package cn.mysens.crucian.util;

import cn.mysens.crucian.config.*;
import cn.mysens.crucian.config.redis.RedisModel;

/**
 * <br>
 *
 * @author 张成<br>
 * @date 2019年02月02日<br>
 */
public final class RedisConfigUtil {

    /*private static final Map<RedisModel, Class<?>> MAPPINGS;

    static{
        Map<RedisModel, Class<?>> mappings = new HashMap<RedisModel, Class<?>>();
        mappings.put(RedisModel.CLUSTER, ClusterModeConfig.class);
        mappings.put(RedisModel.MASTER_SLAVE, MasterSlaveModeConfig.class);
        mappings.put(RedisModel.REPLICATED, ReplicatedModeConfig.class);
        mappings.put(RedisModel.SENTINEL, SentinelModeConfig.class);
        mappings.put(RedisModel.SINGLE, SingleInstanceModeConfig.class);
        MAPPINGS = Collections.unmodifiableMap(mappings);
    }

    private RedisConfigUtil() {
    }

    public static String getConfigClass(RedisModel redisModel) {
        Class<?> configClass = MAPPINGS.get(redisModel);
        Assert.state(configClass != null,
                "Crucian get unknown redis model " + redisModel);
        return configClass.getName();
    }

    public static RedisModel getModel(String configClassName) {
        for (Map.Entry<RedisModel, Class<?>> entry : MAPPINGS.entrySet()) {
            if (entry.getValue().getName().equals(configClassName)) {
                return entry.getKey();
            }
        }
        throw new IllegalStateException(
                "Crucian get unknown config class " + configClassName);
    }*/

    public static RedisConfig initRedisConfig(CrucianStarterServiceProperties.Redis redis) {
        RedisConfig redisConfig = new RedisConfig();
        redisConfig.setPassword(redis.getPassword());
        if (redis.getModel().equals(RedisModel.CLUSTER)) {
            ClusterModeConfig clusterModeConfig = new ClusterModeConfig();
            clusterModeConfig.setIdleConnectionTimeout(redis.getPool().getTimeout());
            clusterModeConfig.setMasterConnectionMinimumIdleSize(redis.getPool().getMinIdle());
            clusterModeConfig.setMasterConnectionPoolSize(redis.getPool().getMaxSize());
            clusterModeConfig.setNodeAddress(redis.getNodeHosts().split(","));
            clusterModeConfig.setSlaveConnectionMinimumIdleSize(redis.getPool().getMinIdle());
            clusterModeConfig.setSlaveConnectionPoolSize(redis.getPool().getMaxSize());
            redisConfig.setClusterModeConfig(clusterModeConfig);
        } else if (redis.getModel().equals(RedisModel.MASTER_SLAVE)) {
            MasterSlaveModeConfig masterSlaveModeConfig = new MasterSlaveModeConfig();
            masterSlaveModeConfig.setIdleConnectionTimeout(redis.getPool().getTimeout());
            masterSlaveModeConfig.setMasterAddress(redis.getMasterHost());
            masterSlaveModeConfig.setMasterConnectionMinimumIdleSize(redis.getPool().getMinIdle());
            masterSlaveModeConfig.setMasterConnectionPoolSize(redis.getPool().getMaxSize());
            masterSlaveModeConfig.setSlaveAddress(redis.getSlaveHosts().split(","));
            masterSlaveModeConfig.setSlaveConnectionMinimumIdleSize(redis.getPool().getMinIdle());
            masterSlaveModeConfig.setSlaveConnectionPoolSize(redis.getPool().getMaxSize());
            redisConfig.setMasterSlaveModeConfig(masterSlaveModeConfig);
        } else if (redis.getModel().equals(RedisModel.REPLICATED)) {
            ReplicatedModeConfig replicatedModeConfig = new ReplicatedModeConfig();
            replicatedModeConfig.setIdleConnectionTimeout(redis.getPool().getTimeout());
            replicatedModeConfig.setMasterConnectionMinimumIdleSize(redis.getPool().getMinIdle());
            replicatedModeConfig.setMasterConnectionPoolSize(redis.getPool().getMaxSize());
            replicatedModeConfig.setNodeAddress(redis.getNodeHosts().split(","));
            replicatedModeConfig.setSlaveConnectionPoolSize(redis.getPool().getMaxSize());
            replicatedModeConfig.setSlaveConnectionMinimumIdleSize(redis.getPool().getMinIdle());
            redisConfig.setReplicatedModeConfig(replicatedModeConfig);
        } else if (redis.getModel().equals(RedisModel.SENTINEL)) {
            SentinelModeConfig sentinelModeConfig = new SentinelModeConfig();
            sentinelModeConfig.setIdleConnectionTimeout(redis.getPool().getTimeout());
            sentinelModeConfig.setMasterConnectionMinimumIdleSize(redis.getPool().getMaxSize());
            sentinelModeConfig.setMasterConnectionPoolSize(redis.getPool().getMinIdle());
            sentinelModeConfig.setSlaveConnectionMinimumIdleSize(redis.getPool().getMaxSize());
            sentinelModeConfig.setSlaveConnectionPoolSize(redis.getPool().getMinIdle());
            sentinelModeConfig.setMasterName(redis.getMaterName());
            sentinelModeConfig.setSentinelAddress(redis.getNodeHosts().split(","));
            redisConfig.setSentinelModeConfig(sentinelModeConfig);
        } else {
            SingleInstanceModeConfig singleInstanceModeConfig = new SingleInstanceModeConfig();
            singleInstanceModeConfig.setAddress(redis.getHost());
            singleInstanceModeConfig.setConnectionMinimumIdleSize(redis.getPool().getMinIdle());
            singleInstanceModeConfig.setConnectionPoolSize(redis.getPool().getMaxSize());
            singleInstanceModeConfig.setIdleConnectionTimeout(redis.getPool().getTimeout());
            redisConfig.setSingleInstanceModeConfig(singleInstanceModeConfig);
        }
        return redisConfig;
    }

}
