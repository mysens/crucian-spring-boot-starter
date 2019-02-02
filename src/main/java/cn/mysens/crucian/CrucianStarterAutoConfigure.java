package cn.mysens.crucian;

import cn.mysens.crucian.config.CrucianStarterServiceProperties;
import cn.mysens.crucian.config.RedisConfig;
import cn.mysens.crucian.config.redis.RedisModel;
import cn.mysens.crucian.config.ZkConfig;
import cn.mysens.crucian.service.CrucianStarterService;
import cn.mysens.crucian.zookeeper.ZkClient;
import cn.mysens.crucian.zookeeper.ZkReentrantLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.*;
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
    @ConditionalOnMissingBean({ZkConfig.class, RedisConfig.class})
    public ZkConfig zkConfig(){
        ZkConfig zkConfig = new ZkConfig();
        zkConfig.setNamespace(properties.getNamespace());
        zkConfig.setDefaultTimeout(properties.getTimeout());
        return zkConfig;
    }

    @Bean
    @ConditionalOnMissingBean(ZkReentrantLock.class)
    @ConditionalOnSingleCandidate(ZkConfig.class)
    public ZkReentrantLock zkReentrantLock(ZkConfig zkConfig){
        //zk客户端
        ZkClient zkClient = new ZkClient(zkConfig);
        //锁模板
        return new ZkReentrantLock(zkClient);
    }

    @Bean
    @ConditionalOnProperty(prefix = "spring.crucian", value = "redis.model")
    @ConditionalOnMissingBean({ZkConfig.class, RedisConfig.class})
    public RedisConfig redisConfig(){
        RedisModel redisModel = properties.getRedis().getModel();

    }



}
