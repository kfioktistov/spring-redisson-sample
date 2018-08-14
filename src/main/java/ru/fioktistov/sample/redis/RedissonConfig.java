package ru.fioktistov.sample.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.ReplicatedServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
public class RedissonConfig {

    @Value("${redis.server.urls}")
    String[] redisServers;

    @Profile("!dev")
    @Bean
    RedissonClient redissonClusterdClient() {
        Config config = new Config();
        ReplicatedServersConfig replicatedConfig = config.useReplicatedServers().setScanInterval(2000);
        for (String redisServer : redisServers) {
            replicatedConfig.addNodeAddress(redisServer);
        }
        return Redisson.create(config);
    }

    @Profile("dev")
    @Bean
    RedissonClient redissonSingleClient() {
        Config config = new Config();
        SingleServerConfig replicatedConfig = config.useSingleServer();
        for (String redisServer : redisServers) {
            replicatedConfig.setAddress(redisServer);
        }
        return Redisson.create(config);
    }

}
