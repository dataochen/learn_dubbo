package org.cdt.myRpc.test;

import org.cdt.myRpc.zk.ZkLock;

/**
 * @author dataochen
 * @Description
 * @date: 2020/6/2 20:36
 */
public class ZkLockTest {
        static int  j=0;
    public static void main(String[] args) {
      cc();
    }

    public static void cc() {
        ZkLock lock = ZkLock.getInstance("127.0.0.1");
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                boolean b = lock.tryLock(100L);
                if (!b) {
                    System.out.println("====");
                    return;
                }
//            System.out.println("ThreadOne"+i);
                int k=j;
                j=k+1;
                System.out.println(j);
//                lock.unLock();
            }
        });
        thread.start();
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                boolean b = lock.tryLock(100L);
                if (!b) {
                    System.out.println("====");
                }
                int k=j;
                j=k+1;
                System.out.println(j);
//                System.out.println("ThreadTwo"+i);
                lock.unLock();

            }
        });
//        thread2.start();
    }
}
