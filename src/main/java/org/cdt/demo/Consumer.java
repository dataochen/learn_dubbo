package org.cdt.demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Consumer {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");
        context.start();
        DemoService demoService = (DemoService) context.getBean("demoServiceConsumer"); // 获取远程服务代理
        Thread thread = Thread.currentThread();
        new Thread(()->{
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("开始interrupt");
            thread.interrupt();
        }).start();
        while (true) {
            try {
                System.out.println("开始调用");
                String hello = demoService.sayHello(1); // 执行远程方法
                System.out.println(hello); // 显示调用结果
                break;
            } catch (Exception e) {
                e.printStackTrace();
//                System.out.println("ee");
            }
            Thread.sleep(2000);

        }
    }

}