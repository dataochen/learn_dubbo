package org.cdt.myRpc.zk;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dataochen
 * @Description
 * @date: 2020/5/29 18:21
 */
public class NamedThreadFactory implements ThreadFactory {
    private String prefix;
    private final AtomicInteger mThreadNum = new AtomicInteger(1);

    public NamedThreadFactory(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public Thread newThread(Runnable r) {
        String name = prefix + mThreadNum.getAndIncrement();
        Thread thread = new Thread(name);
        return thread;
    }
}
