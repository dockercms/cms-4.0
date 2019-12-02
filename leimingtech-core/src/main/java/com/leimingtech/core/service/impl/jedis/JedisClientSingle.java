package com.leimingtech.core.service.impl.jedis;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.leimingtech.core.service.jedis.JedisClient;
import com.leimingtech.core.util.SerializationUtil;

/**
 * 单机版的jedis客户端操作
 *
 * @author liuzhen
 *
 */
public class JedisClientSingle implements JedisClient {

    @Autowired
    private JedisPool jedisPool;

    @Override
    public String set(String key, String value) {
        if(!JedisConfig.jedis_status){
            return null;
        }
        Jedis jedis = jedisPool.getResource();
        String result = jedis.set(key, value);
        jedis.close();
        return result;
    }

    @Override
    public String set(String key, byte[] serialize) {
        if(!JedisConfig.jedis_status){
            return null;
        }
        Jedis jedis = jedisPool.getResource();
        String result = jedis.set(key.getBytes(), serialize);
        jedis.close();
        return result;
    }

    @Override
    public String get(String key) {
        if(!JedisConfig.jedis_status){
            return null;
        }
        Jedis jedis = jedisPool.getResource();
        String result = jedis.get(key);
        jedis.close();
        return result;
    }

    @Override
    public Long del(String key) {
        if(!JedisConfig.jedis_status){
            return null;
        }

        Jedis jedis = jedisPool.getResource();
        Long result=jedis.del(key);
        return result;
    }

    @Override
    public <T> T get(String key, Class<T> classs) {

        if (!JedisConfig.jedis_status) {
            return null;
        }
        Jedis jedis = jedisPool.getResource();
        byte[] result = jedis.get(key.getBytes());

        if (result != null) {
            return (T) SerializationUtil.deserialize(result);
        }
        jedis.close();
        return null;
    }

    @Override
    public List<T> getList(String key, Class<T> classs) {
        if (!JedisConfig.jedis_status) {
            return null;
        }
        Jedis jedis = jedisPool.getResource();
        byte[] result = jedis.get(key.getBytes());

        if (result != null) {
            return (List<T>) SerializationUtil.deserialize(result);
        }
        jedis.close();
        return null;
    }

    @Override
    public Long hset(String key, String item, String value) {
        if(!JedisConfig.jedis_status){
            return null;
        }
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.hset(key, item, value);
        jedis.close();
        return result;
    }

    @Override
    public String hget(String key, String item) {
        if(!JedisConfig.jedis_status){
            return null;
        }
        Jedis jedis = jedisPool.getResource();
        String result = jedis.hget(key, item);
        jedis.close();
        return result;
    }

    @Override
    public Long hdel(String key, String item) {
        if(!JedisConfig.jedis_status){
            return null;
        }
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.hdel(key, item);
        jedis.close();
        return result;
    }

    @Override
    public Long incr(String key) {
        if(!JedisConfig.jedis_status){
            return null;
        }
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.incr(key);
        jedis.close();
        return result;
    }

    @Override
    public Long decr(String key) {
        if(!JedisConfig.jedis_status){
            return null;
        }
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.decr(key);
        jedis.close();
        return result;
    }

    @Override
    public Long expire(String key, int second) {
        if(!JedisConfig.jedis_status){
            return null;
        }
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.expire(key, second);
        jedis.close();
        return result;
    }

    @Override
    public Long ttl(String key) {
        if(!JedisConfig.jedis_status){
            return null;
        }
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.ttl(key);
        jedis.close();
        return result;
    }

}
