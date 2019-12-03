/**
 * Created on 2018/1/22.
 */
package com.alicp.jetcache.anno.support;

import com.alicp.jetcache.anno.CacheConsts;
import com.alicp.jetcache.anno.method.CacheInvokeConfig;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * @author <a href="mailto:areyouok@gmail.com">huangli</a>
 */
public class ConfigMap {
    private ConcurrentHashMap<String, CacheInvokeConfig> methodInfoMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, CacheInvokeConfig> cacheNameMap = new ConcurrentHashMap<>();

    public void putByMethodInfo(String key, CacheInvokeConfig config, Supplier<String> autogenerateNameFunc) {
        methodInfoMap.put(key, config);
        CachedAnnoConfig cac = config.getCachedAnnoConfig();
        if (cac != null) {
            if (CacheConsts.isUndefined(cac.getName())) {
                String newName = autogenerateNameFunc.get();
                cac.setName(newName);
                cacheNameMap.put(cac.getArea() + "_" + newName, config);
            }
        }
    }

    public void putByMethodInfo(String key, CacheInvokeConfig config) {
        methodInfoMap.put(key, config);
        CachedAnnoConfig cac = config.getCachedAnnoConfig();
        if (cac != null && !CacheConsts.isUndefined(cac.getName())) {
            cacheNameMap.put(cac.getArea() + "_" + cac.getName(), config);
        }
    }

    public CacheInvokeConfig getByMethodInfo(String key) {
        return methodInfoMap.get(key);
    }

    public CacheInvokeConfig getByCacheName(String area, String cacheName) {
        return cacheNameMap.get(area + "_" + cacheName);
    }
}
