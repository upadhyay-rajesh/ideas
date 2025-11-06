package database;

/*TYPES OF CACHING STRATEGIES
1 Cache Aside (Most Common)
	Application → checks cache → if not found → loads from DB → stores in cache.
 	Simple
 	Works with all systems
2 Read Through
	Cache provider loads data from DB automatically.
	Application always reads from cache.
 Better consistency
 No cache miss handling in app

3 Write Through
	Writes go to the cache → cache writes to DB.
 	Always consistent
	Little slower (because 2 writes)

4 Write Behind (Write Back)
	Write only to cache → async writer updates DB later.
 	Very fast
	DB consistency risk if system crashes

5 Refresh Ahead
	Cache refreshes before data expires.
	Prevents cache misses

EXAMPLES
Ehcache (JVM In-Memory Cache)
	Best for:
		Single JVM applications
		Local caching
	Very fast access

*/
//Ehcache Example 
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

public class EhcacheExample {
    public static void main(String[] args) {

        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);

        Cache<Long, String> cache = cacheManager.createCache("studentCache",
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                        Long.class, String.class, 
                        ResourcePoolsBuilder.heap(1000)));

        // put
        cache.put(1L, "Rajesh");
        cache.put(2L, "Dhruv");

        // get
        System.out.println(cache.get(1L));
        System.out.println(cache.get(2L));

        cacheManager.close();
    }
}


/*Very fast (nanoseconds)
DRAWBACK Only works in same JVM / same application
*/
/*Caffeine Cache (Most Modern, High-Performance)

Best for:
	High throughput
	Near-zero latency
	Local in-memory LRU/LFU caching
Large enterprise apps, microservices

*/

//Caffeine Example (Program)
/*
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

public class CaffeineExample {
    public static void main(String[] args) {

        Cache<Long, String> cache = Caffeine.newBuilder()
                .maximumSize(10000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build();

        // put
        cache.put(1L, "Amit");
        cache.put(2L, "Sumit");

        // get
        System.out.println(cache.getIfPresent(1L));
        System.out.println(cache.getIfPresent(2L));
    }
}
*/
/*
Fastest Java cache
Ideal for microservices
DRAWBACK JVM local only
*/
/*
Redis Cache (Distributed In-Memory Cache)
Best for:
	Microservices
	Multiple server instances
	Real-time systems
	Large scale apps (Netflix, Uber, etc.)
Redis Java Client (Jedis)

*/

//Redis Example 
/*
import redis.clients.jedis.Jedis;

public class RedisExample {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost", 6379);

        // Write
        jedis.set("student:101", "Rajesh Upadhyay");

        // Read
        String name = jedis.get("student:101");
        System.out.println(name);

        jedis.close();
    }
}
*/
/*
Shared cache among multiple servers
uper fast (microseconds)
Supports TTL, Pub/Sub, Queue, Streams
drawback Requires Redis Server
*/