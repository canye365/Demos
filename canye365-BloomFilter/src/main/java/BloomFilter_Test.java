import com.google.common.hash.Funnels;
import com.google.common.hash.Hashing;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 布隆过滤器是一个使用错误率来换取空间和时间的算法，错误率主要体现在：
 * 他说数据存在，那么不一定存在，他说不存在，一定不存在
 * 它是使用bit数组来记录mysql数据中数据是否存在
 *
 * 布隆过滤器步骤：标识 + 验证
 *
 * 参数选择：
 * 根据预估数据量n以及预判率fpp，bit数据大小m的计算方式：m = - n·lnfpp / (ln2)^2
 * 由预估数据量n以及bit数组长度m，得到hash函数的个数k：k = m / n · ln2
 *
 * @author CanYe
 */
public class BloomFilter_Test {
    private JedisPool jedisPool = null;
    private Jedis jedis = null;
    //要存储的数据量··
    private static long n = 10000;
    //所能容忍错误率
    private static double fpp = 0.01F;

    //bit数组长度
    private static long numBits = optimalNumOfBits(n, fpp);
    //hash函数个数
    private int numHashFunctions = optimalNumOfHashFunctions(n, numBits);


    public static void main(String[] args) {
        System.out.println("bit数组长度：" + numBits);
//        long[] indexs = new BloomFilter_Test().getIndexs("hello");
        BloomFilter_Test filterTest = new BloomFilter_Test();
        filterTest.init();

        int ex_count = 0;
        int ne_count = 0;
        /**
         * 存在： 不一定存在
         * 不存在：一定不存在
         */
        for (int i = 0; i < 20000; i++) {
            // 标识 1w个数据
            //filterTest.put("bf",100 + i + "");
            // 验证 2w个数据，
            boolean exist = filterTest.isExist("bf", 100 + i + "");
            if(exist){
                ex_count++;
            }else{
                ne_count++;
            }
        }
        //ex_count:6729	ne_count 3271
        System.out.println("ex_count:" + ex_count + "\t" + "ne_count " + ne_count);
        /**
         * bit数组长度：95850
         * ex_count:10095	ne_count 9905
         * 误差率：95 / 1w = 0.95% < fpp = 1.00%
         */
    }

    public void init(){
        //测试连接redis
        jedisPool = new JedisPool("127.0.0.1", 6379);
        jedis = jedisPool.getResource();
    }

    private long getCount() {
        Pipeline pipeline = jedis.pipelined();
        Response<Long> bf = pipeline.bitcount("bf");
        pipeline.sync();
        Long count = bf.get();
        try {
            pipeline.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 判断keys是否存在于集合where中
     */
    public boolean isExist(String where, String key) {
        long[] indexs = getIndexs(key);
        boolean result;
        //这里使用了Redis管道来降低过滤器运行当中访问Redis次数 降低Redis并发量
        Pipeline pipeline = jedis.pipelined();
        try {
            for (long index : indexs) {
                pipeline.getbit(where, index);
            }
            result = !pipeline.syncAndReturnAll().contains(false);
        } finally {
            try {
                pipeline.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
//        if (!result) {
//            put(where, key);
//        }
        return result;
    }


    /**
     * 将key存入redis bitmap
     */
    private void put(String where, String key) {
        long[] indexs = getIndexs(key);
        //这里使用了Redis管道来降低过滤器运行当中访问Redis次数 降低Redis并发量
        Pipeline pipeline = jedis.pipelined();
        try {
            for (long index : indexs) {
                pipeline.setbit(where, index, true);
            }
            pipeline.sync();
            /**
             * 把数据存储到mysql中
             */
        } finally {
            try {
                pipeline.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




    /**
     *  根据key获取bitmap下标方法来自guava
     */
    public long[] getIndexs(String key) {
        long hash1 = hash(key);
        long hash2 = hash1 >>> 16;
        long[] result = new long[numHashFunctions];
        for (int i = 0; i < numHashFunctions; i++) {
            long combinedHash = hash1 + i * hash2;
            if (combinedHash < 0) {
                combinedHash = ~combinedHash;
            }
            result[i] = combinedHash % numBits;
        }
        return result;
    }

    /**
     * 获取一个hash值 方法来自guava
     */
    private long hash(String key) {
        Charset charset = Charset.forName("UTF-8");
        return Hashing.murmur3_128().hashObject(key, Funnels.stringFunnel(charset)).asLong();
    }


    private static int optimalNumOfHashFunctions(long n, long m) {
        return Math.max(1, (int) Math.round((double) m / n * Math.log(2)));
    }


    private static long optimalNumOfBits(long n, double p) {
        if (p == 0) {
            p = Double.MIN_VALUE;
        }
        return (long) (-n * Math.log(p) / (Math.log(2) * Math.log(2)));
    }
}
