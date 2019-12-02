package com.leimingtech.core.service.jedis;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;

/**
 * redis客户端
 */
public interface JedisClient {

    public String set(String key, String value);

    /**
     * 存储序列化的数据
     * @param key
     * @param serialize
     * @return
     */
    public String set(String key, byte[] serialize);

    public String get(String key);

    public Long del(String key);

    /**
     * 根据key获取序列化数据
     * @param key
     * @param class1
     * @return
     */
    public <T> T get(String key, Class<T> classs);

    public List<T> getList(String key, Class<T> classs);

    public Long hset(String key, String item, String value);

    public String hget(String key, String item);

    public Long hdel(String key, String item);

    public Long incr(String key);

    public Long decr(String key);

    /**
     *
     * @Description: 设置存存活时间
     * @param key
     * @param second
     * @return
     *
     * @author leechenxiang
     * @date 2016年4月27日 下午4:34:35
     */
    public Long expire(String key, int second);

    /**
     *
     * @Description: 判断key多久过期
     * @param key
     * @return 秒 >= 0 剩余秒数 = -1 永久存活 = -2 已经消除
     *
     * @author leechenxiang
     * @date 2016年4月27日 下午4:34:22
     */
    public Long ttl(String key);

}
