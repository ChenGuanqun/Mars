package com.mars.controller;

import com.mars.common.RedisSingleton;

import org.junit.Test;

import redis.clients.jedis.Jedis;

import java.math.BigInteger;
import java.util.Random;

/**
 * Created by tachen on 5/23/2017.
 */
public class RedisSingletonTest {

    @Test
    public void test() throws InterruptedException {
        Jedis jedis = RedisSingleton.getInstance();
        System.out.println(jedis);
        String token = (new BigInteger(130, new Random())).toString(32);
        jedis.set(token, "live", "NX","EX",(long)20);

        Thread.sleep(5000);
        String value = jedis.get(token);
        System.out.println(value);


        jedis = RedisSingleton.getInstance();
        System.out.println(jedis);
    }
}
