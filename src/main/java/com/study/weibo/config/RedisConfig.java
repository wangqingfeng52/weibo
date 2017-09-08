package com.study.weibo.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisPoolConfig;


@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    @Value("${spring.redis.host}")
    private String REDIS_HOST;
    @Value("${spring.redis.port}")
    private Integer REDIS_PORT;
    @Value("${spring.redis.pool.max-wait}")
    private Long REDIS_MAX_WAIT;
    @Value("${spring.redis.pool.max-idle}")
    private Integer REDIS_MAX_IDLE;
    @Value("${spring.redis.pool.min-idle}")
    private Integer REDIS_MIN_IDLE;


    @Configuration
    @EnableCaching
    public class RedisCacheConfig {
        @Bean
        public CacheManager cacheManager(
                @SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
            return new RedisCacheManager(redisTemplate);
        }

        @Bean
        public RedisTemplate redisTemplate(){
            RedisTemplate redisTemplate = new RedisTemplate();
            redisTemplate.setConnectionFactory(jedisConnectionFactory());
            return redisTemplate;
        }
    }

    @Bean
    public RedisConnectionFactory jedisConnectionFactory(){
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(REDIS_MAX_IDLE);
        poolConfig.setMaxIdle(REDIS_MIN_IDLE);
        poolConfig.setMaxWaitMillis(REDIS_MAX_WAIT);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnCreate(true);
        poolConfig.setTestWhileIdle(true);
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(poolConfig);
        jedisConnectionFactory.setHostName(REDIS_HOST);
        jedisConnectionFactory.setPort(REDIS_PORT);

        //其他配置，可再次扩展
        return jedisConnectionFactory;
    }

}