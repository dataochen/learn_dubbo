package org.cdt.myRpc.zk;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dataochen
 * @Description
 * @date: 2020/4/30 14:51
 */
public class Directory {
    private final ConcurrentHashMap<String, String> localCache = new ConcurrentHashMap<>();
    private ZkClient zkClient;

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
    private Directory() {
    }

    public Directory(String address) {
        init(address);
    }

    public void addNode(String path,Object data) {
         zkClient.createPersistent(path, data);
    }

    public void deleteNode(String path) {
        boolean b = zkClient.deleteRecursive(path);
    }

    public void updateData(String path,Object data) {
        zkClient.writeData(path,data);
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
    class innerPathsLister implements Watcher{
        @Override
        public void process(WatchedEvent watchedEvent) {

        }
    }
}
