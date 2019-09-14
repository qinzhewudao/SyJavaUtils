package cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheStats;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;


/**
 * @author sheyang
 * @description
 * @date 2019/9/14
 * @time 上午10:42
 */
public class LocalCacheUtil {

    /**
     * 缓存过期时间（ms）
     */
    private static final int EXPIRE_TIME = 600_000;

    /**
     * 缓存容量
     */
    private static final int CAPACITY = 5000;

    /**
     * 饿汉式单例对象 jvm 启动时初始化好
     */
    private static LocalCacheUtil instance = new LocalCacheUtil();

    /**
     * LRU缓存
     */
    private Cache<String, Optional<Object>> cache;

    private LocalCacheUtil() {
        cache = CacheBuilder.newBuilder()
                .concurrencyLevel(Runtime.getRuntime().availableProcessors() + 1)
                .expireAfterWrite(EXPIRE_TIME, TimeUnit.MILLISECONDS)
                .initialCapacity(CAPACITY)
                .maximumSize(CAPACITY)
                .recordStats()
                .build();
    }

    /**
     * 获取单例
     *
     * @return 工具类实例
     */
    public static LocalCacheUtil getInstance() {
        return instance;
    }

    /**
     * 重新构建缓存
     *
     * @param expireTime 过期时间（ms）
     * @param capacity   cache容量
     */
    public void rebuild(int expireTime, int capacity) {
        Cache<String, Optional<Object>> newEmptyCache = CacheBuilder.newBuilder()
                .concurrencyLevel(Runtime.getRuntime().availableProcessors() + 1)
                .expireAfterWrite(expireTime, TimeUnit.MILLISECONDS)
                .initialCapacity(capacity)
                .maximumSize(capacity)
                .recordStats()
                .build();
        cache.cleanUp();
        cache = newEmptyCache;

    }

    /**
     * 获取缓存
     *
     * @param key      缓存key
     * @param supplier 不存在时的执行方法
     * @return 缓存对象
     */
    public Object get(final String key, final Supplier supplier) {
        try {
            return cache.get(key, () -> Optional.ofNullable(supplier.get())).orElse(null);
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 只通过key获取缓存
     *
     * @param key 缓存key
     * @return 缓存对象
     */
    public Object getIfPresent(final String key) {
        Optional<Object> value = cache.getIfPresent(key);
        //key不存在于cache
        if (null == value) {
            return null;
        }
        //key对应的值为null
        return value.orElse(null);
    }

    /**
     * 存放缓存
     *
     * @param key   缓存key
     * @param value 缓存对象
     */
    public void put(final String key, final Object value) {
        cache.put(key, Optional.ofNullable(value));
    }

    /**
     * 根据key清除本地缓存
     *
     * @param key 缓存key
     */
    public void delLocalCache(String key) {
        cache.invalidate(key);
    }


    /**
     * 获取本地缓存中key数量
     *
     * @return 本地缓存中key数量
     */
    public int getKeysSize() {
        Set<String> keys = cache.asMap().keySet();
        return keys.size();
    }

    /**
     * 获得所有缓存的命中统计
     *
     * @return 缓存命中统计
     */
    public CacheStats getCacheStats() {
//        requestCount返回Cache查找方法返回缓存或未缓存值的次数。这被定义为hitCount + missCount。
//        hitCount返回Cache查找方法返回缓存值的次数。
//        hitRate()返回已命中的缓存请求的比率。这被定义为hitCount / requestCount
//        missCount返回Cache查找方法返回未缓存（新加载）值的次数
//        missRate返回未命中的缓存请求的比率。这被定义为missCount / requestCount
//        loadCount返回Cache查找方法尝试加载新值的总次数。这包括成功的加载操作以及抛出异常的操作
//        loadSuccessCount返回Cache查找方法成功加载新值的次数。
//        loadExceptionCount返回Cache查找方法在加载新值时抛出异常的次数
//                totalLoadTime返回缓存加载新值所花费的纳秒总数
//        averageLoadPenalty返回加载新值所花费的平均时间
//        evictionCount返回条目被驱逐的次数。
        return cache.stats();
    }

}
