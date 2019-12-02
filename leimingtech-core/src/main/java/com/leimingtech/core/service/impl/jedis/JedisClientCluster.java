package com.leimingtech.core.service.impl.jedis;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import com.leimingtech.core.service.jedis.JedisClient;
import com.leimingtech.core.util.SerializationUtil;

/**
 * 集群版的jedis客户端操作
 *
 * @author liuzhen
 *
 */
public class JedisClientCluster implements JedisClient {

    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public String set(String key, String value) {
        if(!JedisConfig.jedis_status){
            return null;
        }
        return jedisCluster.set(key, value);
    }

    @Override
    public String set(String key, byte[] serialize) {
        if(!JedisConfig.jedis_status){
            return null;
        }
        return jedisCluster.set(key.getBytes(), serialize);
    }

    @Override
    public String get(String key) {
        if(!JedisConfig.jedis_status){
            return null;
        }
        return jedisCluster.get(key);
    }

    @Override
    public <T> T get(String key, Class<T> classs) {
        if(!JedisConfig.jedis_status){
            return null;
        }

        byte[] result=jedisCluster.get(key.getBytes());

        if(result!=null){
            return (T) SerializationUtil.deserialize(result);
        }

        return null;
    }

    @Override
    public Long del(String key) {
        if(!JedisConfig.jedis_status){
            return null;
        }

        Long result=jedisCluster.del(key);
        return result;
    }

    @Override
    public List<T> getList(String key, Class<T> classs) {
        if(!JedisConfig.jedis_status){
            return null;
        }

        byte[] result=jedisCluster.get(key.getBytes());

        if(result!=null){
            return (List<T>) SerializationUtil.deserialize(result);
        }

        return null;
    }

    @Override
    public Long hset(String key, String item, String value) {
        if(!JedisConfig.jedis_status){
            return null;
        }
        return jedisCluster.hset(key, item, value);
    }

    @Override
    public String hget(String key, String item) {
        if(!JedisConfig.jedis_status){
            return null;
        }
        return jedisCluster.hget(key, item);
    }

    @Override
    public Long hdel(String key, String item) {
        if(!JedisConfig.jedis_status){
            return null;
        }
        return jedisCluster.hdel(key, item);
    }

    @Override
    public Long incr(String key) {
        if(!JedisConfig.jedis_status){
            return null;
        }
        return jedisCluster.incr(key);
    }

    @Override
    public Long decr(String key) {
        if(!JedisConfig.jedis_status){
            return null;
        }
        return jedisCluster.decr(key);
    }

    @Override
    public Long expire(String key, int second) {
        if(!JedisConfig.jedis_status){
            return null;
        }
        return jedisCluster.expire(key, second);
    }

    @Override
    public Long ttl(String key) {
        if(!JedisConfig.jedis_status){
            return null;
        }
        return jedisCluster.ttl(key);
    }

}
