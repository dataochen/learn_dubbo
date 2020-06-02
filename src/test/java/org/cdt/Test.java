package org.cdt;

import org.cdt.myRpc.zk.NamedThreadFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author dataochen
 * @Description
 * @date: 2019/10/24 20:37
 */
public class  Test {

    private String c;
    private  static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 5000,
    TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(), new NamedThreadFactory("MyRpc_directory_pool_"));

    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        int i = System.identityHashCode(new Test());
        int i2 = System.identityHashCode(new Test());
        System.out.println(i);
        System.out.println(i2);
        MyThread myThread = new MyThread();
//        myThread.setDaemon(true);
//        myThread.start();
        threadPoolExecutor.execute(myThread);
        try {
            Thread.sleep(2000);
            System.out.println(myThread.isAlive());
            threadPoolExecutor.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

 static class  MyThread extends Thread{


     @Override
     public void run() {
         System.out.println(1);
     }
 }

}
