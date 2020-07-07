package cn.canye365.overbooking;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author CanYe
 * Redis的事务机制 解决 超售现象
 * 运行后 redis的num为0， show_list 按照倒叙，依次列出50条。
 */
public class Application {

    /*public static ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(10, 100, 10, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<Runnable>()
            );*/

    public static void main(String[] args) {

        //Jedis jedis = new Jedis("localhost", 6379);
        //jedis.auth("canye");
        //jedis.select(1);
        //jedis.set("num", "200");
        //jedis.del("show_list"); // 初始化的时候，清楚上一次留下的show_list
        //jedis.close();

        for(int i=1 ; i <= 1000 ; i ++){
            //threadPoolExecutor.execute(new MyTask());
            new Thread(new MyTask(), "t" + i).start();
        }
    }
}
