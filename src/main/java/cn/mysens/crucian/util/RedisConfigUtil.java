package cn.mysens.crucian.util;

import cn.mysens.crucian.config.ClusterModeConfig;
import cn.mysens.crucian.config.redis.RedisModel;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <br>
 *
 * @author 张成<br>
 * @date 2019年02月02日<br>
 */
public final class RedisConfigUtil {

    private static final Map<RedisModel, Class<?>> MAPPINGS;

    static{
        Map<RedisModel, Class<?>> mappings = new HashMap<RedisModel, Class<?>>();
        mappings.put(RedisModel.CLUSTER, ClusterModeConfig.class);
        mappings.put(RedisModel.MASTER_SLAVE, ClusterModeConfig.class);
        mappings.put(RedisModel.REPLICATED, ClusterModeConfig.class);
        mappings.put(RedisModel.SENTINEL, ClusterModeConfig.class);
        mappings.put(RedisModel.SINGLE, ClusterModeConfig.class);
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
    }

}
