package cn.mysens.crucian.config;

import cn.mysens.crucian.config.redis.RedisModel;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * crucian配置文件<br>
 *
 * @author 张成<br>
 * @date 2019年02月02日<br>
 */
@ConfigurationProperties("spring.crucian")
public class CrucianStarterServiceProperties {

    /**
     * 命名空间
     */
    private String namespace;

    /**
     * 超时时间
     */
    private long timeout;

    /**
     * zk配置
     */
    private ZooKeeper zooKeeper;

    /**
     * redis配置
     */
    private Redis redis;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public ZooKeeper getZooKeeper() {
        return zooKeeper;
    }

    public void setZooKeeper(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }

    public Redis getRedis() {
        return redis;
    }

    public void setRedis(Redis redis) {
        this.redis = redis;
    }

    public static class ZooKeeper{
        /**
         * zk地址
         * 逗号分隔
         */
        private String hosts = "127.0.0.1:2181";

        public String getHosts() {
            return hosts;
        }

        public void setHosts(String hosts) {
            this.hosts = hosts;
        }

    }

    public static class Redis{

        /**
         * 单机模式地址
         */
        private String host = "localhost";
        /**
         * 单机模式端口
         */
        private int port = 6379;
        /**
         * 密码
         */
        private String password;
        /**
         * 主从模式主节点地址
         */
        private String masterHost;
        /**
         * 主从模式从节点地址
         */
        private String slaveHosts;
        /**
         * 其他模式节点地址
         */
        private String nodeHosts;
        /**
         * 哨兵模式主节点名字
         */
        private String materName;
        /**
         * redis模式
         */
        private RedisModel model;
        /**
         * 连接池
         */
        private Pool pool;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getMasterHost() {
            return masterHost;
        }

        public void setMasterHost(String masterHost) {
            this.masterHost = masterHost;
        }

        public String getSlaveHosts() {
            return slaveHosts;
        }

        public void setSlaveHosts(String slaveHosts) {
            this.slaveHosts = slaveHosts;
        }

        public String getNodeHosts() {
            return nodeHosts;
        }

        public void setNodeHosts(String nodeHosts) {
            this.nodeHosts = nodeHosts;
        }

        public String getMaterName() {
            return materName;
        }

        public void setMaterName(String materName) {
            this.materName = materName;
        }

        public RedisModel getModel() {
            return model;
        }

        public void setModel(RedisModel model) {
            this.model = model;
        }

        public Pool getPool() {
            return pool;
        }

        public void setPool(Pool pool) {
            this.pool = pool;
        }
    }

    public static class Pool{
        /**
         * 最小活跃连接数
         */
        private int minIdle = 32;
        /**
         * 最大连接数
         */
        private int maxSize = 64;
        /**
         * 从节点最小活跃连接数
         */
        private int nodeMinIdle = 32;
        /**
         * 从节点最大连接数
         */
        private int nodeMaxSize = 64;
        /**
         * 连接超时时间
         */
        private int timeout = 10000;

        public int getMinIdle() {
            return minIdle;
        }

        public void setMinIdle(int minIdle) {
            this.minIdle = minIdle;
        }

        public int getMaxSize() {
            return maxSize;
        }

        public void setMaxSize(int maxSize) {
            this.maxSize = maxSize;
        }

        public int getNodeMinIdle() {
            return nodeMinIdle;
        }

        public void setNodeMinIdle(int nodeMinIdle) {
            this.nodeMinIdle = nodeMinIdle;
        }

        public int getNodeMaxSize() {
            return nodeMaxSize;
        }

        public void setNodeMaxSize(int nodeMaxSize) {
            this.nodeMaxSize = nodeMaxSize;
        }

        public int getTimeout() {
            return timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }
    }





}
