package com.mars.controller;

import org.junit.Test;

import redis.clients.jedis.Jedis;

import java.math.BigInteger;
import java.util.Random;

/**
 * Created by tachen on 5/23/2017.
 */
public class RedisSessionManagerTests {

    Jedis jedis = new Jedis("172.93.40.193",6379);

//    @Test
//    public void testList(){
//
//        jedis.lpush("queue#tasks", "firstTask");
//        jedis.lpush("queue#tasks", "secondTask");
//
//        String task = jedis.rpop("queue#tasks");
//        System.out.println(task);
//    }

    @Test
    public void testSetValid() throws InterruptedException {
        String token = (new BigInteger(130, new Random())).toString(32);
        jedis.set(token, "live", "NX","EX",(long)20);

        Thread.sleep(5000);
        String value = jedis.get(token);
        System.out.println(value);
    }

    @Test
    public void testSetExpire() throws InterruptedException {
        String token = (new BigInteger(130, new Random())).toString(32);
        jedis.set(token, "live", "NX","EX",(long)20);

        Thread.sleep(25000);
        String value = jedis.get(token);
        System.out.println(value);
    }
}
