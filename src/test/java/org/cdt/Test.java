package org.cdt;

import java.lang.reflect.InvocationTargetException;

/**
 * @author dataochen
 * @Description
 * @date: 2019/10/24 20:37
 */
public class  Test {

    private String c;

    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        int i = System.identityHashCode(new Test());
        int i2 = System.identityHashCode(new Test());
        System.out.println(i);
        System.out.println(i2);
        MyThread myThread = new MyThread();
        myThread.setDaemon(true);
        myThread.run();
        try {
            Thread.sleep(2000);
            System.out.println(myThread.isAlive());
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
