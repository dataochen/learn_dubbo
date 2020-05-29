package org.cdt.myRpc.zk;

import org.cdt.myRpc.RpcFrameWork;
import org.cdt.myRpc.cluster.DefaultCluster;
import org.cdt.myRpc.model.Invoker;

import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author dataochen
 * @Description zk数据模型：
 * /MyRpc/apis/xx
 * 接口全限名：ip1,ip2
 * @date: 2020/5/28 17:43
 */
public class DirectoryZkAdapter {
    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 3, 5000,
            TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(), new NamedThreadFactory("MyRpc_directory_pool_"));

    private static final String APIS_PROVIDES_PATH_SUFFIX = "/MyRpc/apis/provides/";
    private static final String APIS_CONSUMES_PATH_SUFFIX = "/MyRpc/apis/consumes/";

    private String zkAddress;

    public DirectoryZkAdapter(String zkAddress) {
        this.zkAddress = zkAddress;
    }

    /**
     * 暴露服务
     * 1.接口路径 写到zk中的已存在的接口api列表中
     * <p>
     * 待完善问题：
     * 1.网络暴露服务失败 zk数据需要删除 ，方案：新增心跳线程维护
     * 2.多应用读取统一接口，并发导致ips丢失数据，如何解决？方案：对路径进行加锁 读写都需要
     */
    public void export(Object server, Class<?> intefaceName) throws UnknownHostException {
        DirectoryZk directoryZk = DirectoryZk.getInstance(zkAddress);
//异步暴露服务 网络层
        threadPoolExecutor.execute(() -> RpcFrameWork.export(1234, server));
//

        String name = intefaceName.getName();
//        全限名 点 转换为 路径
        String intefacePath = APIS_PROVIDES_PATH_SUFFIX + name.replaceAll("\\.", "/");
//先读取intefacePath路径 查看是否存在data 如果存在 用逗号拼接ip
//        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        String hostAddress = "127.0.0.1";
        Object data4Path = directoryZk.getData4Path(intefacePath);
        String ips = hostAddress;
        if (null != data4Path) {
            ips = data4Path + "," + hostAddress;
        }

        directoryZk.addNode(intefacePath, ips);
    }


    /**
     * 引用服务
     * 1.获取servers
     * 2.cluster 负载均衡获取server
     * 3.网络层代理调用
     * <p>
     * 4.录入zk APIS_CONSUMES_PATH_SUFFIX
     * 5.
     * 待完善问题：
     * 1.网络reference服务失败 zk数据需要删除 ，方案：新增心跳线程维护
     * 2.多应用读取统一接口，并发导致ips丢失数据，如何解决？方案：对路径进行加锁 读写都需要
     */
    public <T> T reference(Class<? extends T> interfaces) throws ExecutionException, InterruptedException, UnknownHostException, IllegalAccessException {
        DirectoryZk directoryZk = DirectoryZk.getInstance(zkAddress);

//     * 1.获取servers
        List<Invoker> invokeList = directoryZk.getInvokeList(interfaces);
//             * 2.cluster 负载均衡获取server
        DefaultCluster defaultCluster = new DefaultCluster();
        Invoker select = defaultCluster.select(invokeList);
//        3.网络层代理调用
        Future<? extends T> submit = threadPoolExecutor.submit(() -> RpcFrameWork.refer(interfaces, select.getProviderIp(), 1234));
//     * 4.录入zk APIS_CONSUMES_PATH_SUFFIX
        String name = interfaces.getName();
//        全限名 点 转换为 路径
        String intefacePathConsume = APIS_CONSUMES_PATH_SUFFIX + name.replaceAll("\\.", "/");
//先读取intefacePath路径 查看是否存在data 如果存在 用逗号拼接ip
//        String hostAddress = InetAddress.getLocalHost().getHostAddress();
                 String hostAddress = "127.0.0.1";

        Object data4Path = directoryZk.getData4Path(intefacePathConsume);
        String ips = hostAddress;
        if (null != data4Path) {
            ips = data4Path + "," + hostAddress;
        }
        directoryZk.addNode(intefacePathConsume, ips);

        return submit.get();
    }
}
