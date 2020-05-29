package org.cdt.myRpc.zk;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.Watcher;
import org.cdt.myRpc.model.Invoker;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dataochen
 * @Description 本地缓存层
 * 作用：同步zk数据
 * @date: 2020/4/30 14:51
 */
public class DirectoryZk {
    private volatile ZkClient zkClient;
    private volatile static DirectoryZk directoryZk;
    private static final String APIS_PROVIDES_PATH_SUFFIX = "/MyRpc/apis/provides/";
    private static final String APIS_CONSUMES_PATH_SUFFIX = "/MyRpc/apis/consumes/";
    /**
     * 本地缓存的invoker
     * Key:接口全限名
     * value:对应的生产者集合
     */
    private ConcurrentHashMap<String, List<Invoker>> localInvokerMap = new ConcurrentHashMap<>();

    private void init(String address) {
        if (zkClient == null) {
            synchronized (this) {
                if (zkClient == null) {
                    zkClient = new ZkClient(address, 10000, 10000, new SerializableSerializer());
                }
            }
        }
    }

    public void destroy() {
        zkClient.close();
    }

    private DirectoryZk() {
    }

    private DirectoryZk(String address) {
        init(address);
    }

    public static DirectoryZk getInstance(String address) {
        if (directoryZk == null) {
            synchronized (DirectoryZk.class) {
                if (directoryZk == null) {
                    directoryZk = new DirectoryZk(address);
                }
            }
        }
        return directoryZk;
    }

    public void addNode(String path, Object data) {
        zkClient.createPersistent(path, true);
//        createEphemeralParent(path);
        zkClient.writeData(path, data);
        zkClient.subscribeChildChanges(path, new InnerPathsLister());

    }

    private void createEphemeralParent(String path) {
        try {
            zkClient.createEphemeral(path);
        } catch (ZkNodeExistsException e) {
        } catch (ZkNoNodeException e) {
            String parentDir = path.substring(0, path.lastIndexOf('/'));
            createEphemeralParent(parentDir);
            createEphemeralParent(path);

        }
    }

    public void deleteNode(String path) {
        boolean b = zkClient.deleteRecursive(path);
        zkClient.unsubscribeChildChanges(path, new InnerPathsLister());

    }

    public void updateData(String path, Object data) {
        zkClient.writeData(path, data);
        zkClient.subscribeDataChanges(path, new InnerPathsLister());
    }

    public boolean exist(String path) {
        return zkClient.exists(path);
    }

    public Object getData4Path(String path) {
        Object o = zkClient.readData(path, true);
        return o;
    }

    public List<Object> getData4Children(String parentPath) {
        LinkedList<Object> objects = new LinkedList<>();
        List<String> children = zkClient.getChildren(parentPath);
        if (Objects.isNull(children)) {
            return null;
        }
        if ("/".equals(parentPath.substring(parentPath.length() - 1))) {
            parentPath = parentPath.substring(0, parentPath.length() - 1);
        }
        for (String child : children) {
            Object o = zkClient.readData(parentPath + child);
            objects.add(o);
        }
        return objects;

    }

    public List<String> getChildren(String path) {
        List<String> children = zkClient.getChildren(path);
        return children;
    }

    class InnerPathsLister implements IZkChildListener, IZkDataListener, IZkStateListener {

        @Override
        public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {

        }

        @Override
        public void handleDataChange(String dataPath, Object data) throws Exception {

        }

        @Override
        public void handleDataDeleted(String dataPath) throws Exception {

        }

        @Override
        public void handleStateChanged(Watcher.Event.KeeperState state) throws Exception {

        }

        @Override
        public void handleNewSession() throws Exception {

        }

        @Override
        public void handleSessionEstablishmentError(Throwable error) throws Exception {

        }
    }

    //    --------------------Invoker 操作方法-------------
    public List<Invoker> getInvokeList(Class interfaces) throws IllegalAccessException {
        String name = interfaces.getName();
//        全限名 点 转换为 路径
        String intefacePath = APIS_PROVIDES_PATH_SUFFIX + name.replaceAll("\\.", "/");
        String intefacePathConsume = APIS_CONSUMES_PATH_SUFFIX + name.replaceAll("\\.", "/");
        //     * 1.获取servers
        Object data4Path1 = getData4Path(intefacePath);
        if (null == data4Path1) {
            throw new IllegalAccessException("no server exist by path:" + intefacePath);
        }
        String[] ipsProvide = Optional.ofNullable(data4Path1).map(x -> x.toString()).map(x -> x.split(",")).orElse(new String[0]);
        if (ipsProvide.length <= 0) {
            throw new IllegalAccessException("no server exist by path:" + intefacePath);
        }
        LinkedList<Invoker> invokers = new LinkedList<>();
        for (String ip : ipsProvide) {
            Invoker invoker = new Invoker(ip);
            invokers.add(invoker);
        }
        return invokers;
    }
}
