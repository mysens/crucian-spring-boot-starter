package cn.mysens.crucian;

import cn.mysens.crucian.config.CrucianStarterServiceProperties;
import cn.mysens.crucian.config.RedisConfig;
import cn.mysens.crucian.config.ZkConfig;
import cn.mysens.crucian.lock.LockTemplate;
import cn.mysens.crucian.redis.RedisClient;
import cn.mysens.crucian.redis.RedisReentrantLock;
import cn.mysens.crucian.service.CrucianStarterService;
import cn.mysens.crucian.util.RedisConfigUtil;
import cn.mysens.crucian.zookeeper.ZkClient;
import cn.mysens.crucian.zookeeper.ZkReentrantLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <br>
 *
 * @author 张成<br>
 * @date 2019年02月02日<br>
 */
@Configuration
@ConditionalOnClass(CrucianStarterService.class)
@EnableConfigurationProperties(CrucianStarterServiceProperties.class)
public class CrucianStarterAutoConfigure {

    @Autowired
    private CrucianStarterServiceProperties properties;

    @Bean
    @ConditionalOnProperty(prefix = "spring.crucian", value = "zooKeeper.hosts")
    @ConditionalOnMissingBean({ZkConfig.class, RedisConfig.class})
    public ZkConfig zkConfig(){
        ZkConfig zkConfig = new ZkConfig();
        zkConfig.setNamespace(properties.getNamespace());
        zkConfig.setDefaultTimeout(properties.getTimeout());
        zkConfig.setZkHosts(properties.getZooKeeper().getHosts());
        return zkConfig;
    }

    @Bean
    @ConditionalOnProperty(prefix = "spring.crucian", value = "redis.model")
    @ConditionalOnMissingBean({ZkConfig.class, RedisConfig.class})
    public RedisConfig redisConfig() {
        return RedisConfigUtil.initRedisConfig(properties.getRedis());
    }

    @Bean
    @ConditionalOnMissingBean(ZkReentrantLock.class)
    @ConditionalOnSingleCandidate(ZkConfig.class)
    public LockTemplate zkReentrantLock(ZkConfig zkConfig) {
        //zk客户端
        ZkClient zkClient = new ZkClient(zkConfig);
        //锁模板
        return new ZkReentrantLock(zkClient);
    }

    @Bean
    @ConditionalOnMissingBean(ZkReentrantLock.class)
    @ConditionalOnSingleCandidate(RedisConfig.class)
    public LockTemplate redisReentrantLock(RedisConfig redisConfig) {
        //redis客户端
        RedisClient redisClient = new RedisClient(redisConfig);
        return new RedisReentrantLock(redisClient);
    }



}
