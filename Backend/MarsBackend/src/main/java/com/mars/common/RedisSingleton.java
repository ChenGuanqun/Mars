package com.mars.common;

import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by tachen on 5/23/2017.
 */
public class RedisSingleton {

    private static Jedis jedis = null;

    private static Object object = new Object();
    private static String redisAddress = null;
    private static int port = 0;

    static {
        Properties properties = new Properties();
        InputStream in = null;
        try {
            in = RedisSingleton.class.getResourceAsStream("/mars.conf");
            properties.load(in);
            redisAddress = properties.getProperty("redis.address");
            port = Integer.valueOf(properties.getProperty("redis.port"));
        } catch (IOException e) {
            e.printStackTrace();
            try {
                in.close();
            } catch (IOException e1) {
            }
        }
    }

    /**
     * Get singleton instance
     *
     * @return
     */
    public static Jedis getInstance() {
        if (jedis == null) {
            synchronized (object) {
                if (jedis == null) {
                    jedis = new Jedis(redisAddress,port);
                }
            }
        }
        return jedis;
    }

}
