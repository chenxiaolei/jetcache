/**
 * Created on  13-09-19 20:40
 */
package com.alicp.jetcache.anno.aop;

import com.alicp.jetcache.anno.support.CacheNameGenerator;
import com.alicp.jetcache.anno.support.ConfigMap;
import com.alicp.jetcache.anno.support.DefaultCacheNameGenerator;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;

/**
 * @author <a href="mailto:areyouok@gmail.com">huangli</a>
 */
public class CacheAdvisor extends AbstractBeanFactoryPointcutAdvisor {

    public static final String CACHE_ADVISOR_BEAN_NAME = "jetcache2.internalCacheAdvisor";

    @Autowired
    private ConfigMap cacheConfigMap;

    @Autowired(required = false)
    @Lazy(false)
    private CacheNameGenerator cacheNameGenerator;

    @Value(value = "${jetcache.hiddenPackages:null}")
    private String[] hiddenPackage;

    private String[] basePackages;

    @Override
    public Pointcut getPointcut() {
        CachePointcut pointcut = new CachePointcut(basePackages);
        pointcut.setCacheConfigMap(cacheConfigMap);
        if (cacheNameGenerator == null) {
            pointcut.setCacheNameGenerator(new DefaultCacheNameGenerator(hiddenPackage));
        }
        pointcut.setCacheNameGenerator(cacheNameGenerator);
        return pointcut;
    }

    public void setCacheConfigMap(ConfigMap cacheConfigMap) {
        this.cacheConfigMap = cacheConfigMap;
    }

    public void setBasePackages(String[] basePackages) {
        this.basePackages = basePackages;
    }
}
