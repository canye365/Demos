package cn.canye365.overbooking;

import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * @author CanYe
 * Redis的事务机制 解决 超售现象
 * 运行后 redis的num为0， show_list 按照倒叙，依次列出50条。
 */
public class JMeterApplication extends AbstractJavaSamplerClient {

    @Override
    public SampleResult runTest(JavaSamplerContext javaSamplerContext) {
        //System.out.println(Thread.currentThread().getName());
        SampleResult sampleResult = new SampleResult();
        sampleResult.sampleStart();
        Jedis jedis = new Jedis("localhost", 6379);
        //jedis.auth("canye");
        jedis.select(1);
        jedis.watch("num", "show_list");
        int num = Integer.parseInt(jedis.get("num"));
        if(num > 0){
            Transaction transaction = jedis.multi();
            transaction.decr("num");
            transaction.rpush("show_list", num + " is show...");
            transaction.exec();
        }
        jedis.close();
        sampleResult.setSuccessful(true);
        sampleResult.sampleEnd();
        return sampleResult;
    }
}
