package org.cdt.myRpc.zk;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

/**
 * @author dataochen
 * @Description
 * @date: 2020/6/2 20:18
 */
public class ZkLock {
    private static volatile ZkLock instance;
    private  ZkClient zkClient;
    private final String LOCKPATH = "/LOCK";

    private void init(String address) {
        if (zkClient == null) {
            synchronized (this) {
                if (zkClient == null) {
                    zkClient = new ZkClient(address, 10000, 10000, new SerializableSerializer());
                }
            }
        }
        return;
    }

    private ZkLock() {
    }

    private ZkLock(String address) {

    }

    public static ZkLock getInstance(String address) {
        if (instance == null) {
            synchronized (ZkLock.class) {
                if (instance == null) {
                    instance = new ZkLock();
                    instance.init(address);
                }
            }
        }
        return instance;
    }

    public  boolean lock() {
        try {
            zkClient.createEphemeral(LOCKPATH);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    /**
     * 自旋获取锁
     * 可以指定超时时间 单位毫秒
     * 每10毫秒 自旋一次
     *
     * @param timeOut
     */
    public boolean tryLock(Long timeOut) {
        long time=timeOut;
        while (time>=0) {
            time = time - 10;
            boolean lock = lock();
            if (lock) {
                return true;
            }
        }
        return false;
    }

    public boolean unLock() {
        boolean delete = zkClient.delete(LOCKPATH);
        return delete;
    }

    //    =================有序锁

    /**
     * 原理：抢最小节点
     * 1.在指定path下创建临时有序节点
     * 2.判断当前client的临时有序节点是否是path下最小的，如果是即可认为获取了锁
     * 3.如果不是，没有抢到锁，此时需要监听最小的节点，用于当最小节点被删除（锁释放）激活当前client去抢锁（继续比较当前client的临时有序节点是否是path下最小的）
     * 注意：1.需要判断当前client是否已经创建过临时有序节点，如果是就不需要在创建临时有序节点了
     * 2.可通过临时有序节点改为临时无序节点 达到非公平锁的效果
     */
    class SequentialLock {

    }
}
