package com.qb.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
	
//	@Value("${app.redis.host}")
    private String redisHost = "localhost";
	
//    @Value("${app.redis.port}")
    private int redisPort = 6379;

    @Value("${app.redis.password}")
    private String redisPassword;
    
//    @Value("${app.redis.db.index}")
    int redisDbIndex = 0;

//    @Value("${app.redis.sentinels.hosts}")
//    String redisSentinelHosts;
//    @Value("${app.redis.sentinel.master.name}")
//    String redisMasterName;
//    @Value("${app.redis.use-sentinels}")
//    boolean useSentinels;
//
//    @Value("${app.redis.sentinels.password}")
//    String redisSentinelPassword;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(
                redisHost, redisPort);
        redisStandaloneConfiguration.setDatabase(redisDbIndex);
        redisStandaloneConfiguration.setPassword(redisPassword);
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    RedisTemplate< String, Object > redisTemplate() {
        final RedisTemplate< String, Object > template =  new RedisTemplate< String, Object >();
        template.setConnectionFactory( jedisConnectionFactory() );
        template.setKeySerializer( new StringRedisSerializer() );
        template.setHashValueSerializer( new GenericToStringSerializer< Object >( Object.class ) );
        template.setValueSerializer( new GenericToStringSerializer< Object >( Object.class ) );
        return template;
    }

}
