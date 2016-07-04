package com.dojo.guava;
import java.util.concurrent.TimeUnit;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.cache.Weigher;

@RestController
public class CacheController {
    
    /*
     * Types de gestionnaire de cache:
     * Map our ConcurrentMap => 
     *      - cache simple (quantité de donnée connue, durée de vie = durée de l'application
     * EhCache (le plus populaire en Java) => 
     *      - gestion TTL
     *      - gestion taille max LRU (Least Recently Used)
     *      - persistence sur disque
     *      - partagé en plusieurs JVM
     * Memcached/Redis => plusieurs applis/JVM avec accés centralisé
     * 
     * Guava Cache se positionne entre les Maps et EhCache
     * 
     * cf: CacheLoader, weigher, removalListener, maximumWeight
     */
    
    
    LoadingCache<String, String> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .maximumWeight(1000)
            .removalListener(new RemovalListener<String, String>() {

                @Override
                public void onRemoval(RemovalNotification<String, String> notification) {
                    System.out.println("Remove this key: " + notification.getKey());
                }
            })
            .weigher(new Weigher<String, String>() {

                @Override
                public int weigh(String key, String value) {
                    return value.length();
                }
            })
            .build(new CacheLoader<String, String>(){

                @Override
                public String load(String key) throws Exception {
                    System.out.println("Key " + key + " isn't in the cache...");
                    return key+"-value";
                }
                
            });

    @RequestMapping("/cache/{key}")
    public String index(@PathVariable String key) {
        String value = cache.getUnchecked(key);
        return "Key= " + key + " => Value= " + value;
    }

}
