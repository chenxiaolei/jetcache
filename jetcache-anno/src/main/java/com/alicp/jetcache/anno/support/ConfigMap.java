/**
 * Created on 2018/1/22.
 */
package com.alicp.jetcache.anno.support;

import com.alicp.jetcache.anno.CacheConsts;
import com.alicp.jetcache.anno.method.CacheInvokeConfig;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * @author huangli
 */
public class ConfigMap {
    private ConcurrentHashMap<String, CacheInvokeConfig> methodInfoMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, CachedAnnoConfig> cacheNameMap = new ConcurrentHashMap<>();

    // todo: add features
    public void putByMethodInfo(String key, CacheInvokeConfig config) {
        putByMethodInfo(key, config, null);
    }

    // todo: add features
    public void putByMethodInfo(String key, CacheInvokeConfig config, Supplier<String> autogenerateNameFunc) {
        methodInfoMap.put(key, config);
        CachedAnnoConfig cac = config.getCachedAnnoConfig();
        if (cac != null) {
            if (CacheConsts.isUndefined(cac.getName()) && autogenerateNameFunc != null) {
                cac.setName(autogenerateNameFunc.get());
            }
            if (!CacheConsts.isUndefined(cac.getName())) {
                cacheNameMap.put(cac.getArea() + "_" + cac.getName(), cac);
            }
        }
    }

    public CacheInvokeConfig getByMethodInfo(String key) {
        return methodInfoMap.get(key);
    }

    public CachedAnnoConfig getByCacheName(String area, String cacheName) {
        return cacheNameMap.get(area + "_" + cacheName);
    }
}
