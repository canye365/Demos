package cn.canye365.overbooking;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import javax.xml.crypto.Data;
import java.util.Date;

/**
 * @author CanYe
 */
public class MyTask implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
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
    }
}
