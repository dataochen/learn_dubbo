package org.cdt.myRpc.zk;

import org.cdt.myRpc.RpcFrameWork;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author dataochen
 * @Description zk数据模型：
 * /MyRpc/apis/xx
 * 接口全限名：ip1,ip2
 * @date: 2020/5/28 17:43
 */
public class DirectoryZkAdapter {
    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 3, 5000,
            TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());

    private static final String APIS_PATH_SUFFIX = "/MyRpc/apis/";

    private String zkAddress;

    /**
     * 暴露服务
     * 1.接口路径 写到zk中的已存在的接口api列表中
     *
     * 待完善问题：
     * 1.网络暴露服务失败 zk数据需要删除 ，方案：新增心跳线程维护
     * 2.多应用读取统一接口，并发导致ips丢失数据，如何解决？方案：对路径进行加锁
     */
    public void export(Object server, Class<?> intefaceName) throws UnknownHostException {
//异步暴露服务 网络层
        threadPoolExecutor.execute(() -> RpcFrameWork.export(1234, server));
//
        Directory directory = new Directory(zkAddress);
        String name = intefaceName.getName();
//        全限名 点 转换为 路径
        String intefacePath = APIS_PATH_SUFFIX + name.replaceAll(".", "/");
//先读取intefacePath路径 查看是否存在data 如果存在 用逗号拼接ip
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        Object data4Path = directory.getData4Path(intefacePath);
        String ips = hostAddress;
        if (null != data4Path) {
            ips = data4Path + "," + hostAddress;
        }

        directory.addNode(APIS_PATH_SUFFIX + intefacePath, ips);
    }
}
