package org.cdt;

/**
 * @author dataochen
 * @Description
 * @date: 2020/11/5 11:21
 */
public class ParkTest {
    public static void main(String[] args) {
        Obj obj = new ParkTest().new Obj();
        Thread thread = Thread.currentThread();
        new Thread(()->{
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            thread.interrupt();

        }).start();
        obj.a();
        obj.a();
        obj.a();
    }
    class Obj{

        public synchronized void  a(){
            System.out.println("1");
            System.out.println(Thread.currentThread().isInterrupted());
//            LockSupport.park(this);
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().isInterrupted());
            System.out.println("2");

        }
    }
}
