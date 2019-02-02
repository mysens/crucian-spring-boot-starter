package cn.mysens.crucian.config.redis;

/**
 * redis模式<br>
 *
 * @author 张成<br>
 * @date 2019年02月02日<br>
 */
public enum RedisModel {

    /**
     * 单机
     */
    SINGLE,

    /**
     * 集群
     */
    CLUSTER,

    /**
     * 主从
     */
    MASTER_SLAVE,

    /**
     * 复制
     */
    REPLICATED,

    /**
     * 哨兵
     */
    SENTINEL;


}
