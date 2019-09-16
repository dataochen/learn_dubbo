package org.cdt.demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Consumer {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");
        context.start();
        DemoService demoService = (DemoService) context.getBean("demoServiceConsumer"); // 获取远程服务代理
        while (true) {
            try {
                String hello = demoService.sayHello("world cdt"); // 执行远程方法
                System.out.println(hello); // 显示调用结果
            } catch (Exception e) {
                e.printStackTrace();
//                System.out.println("ee");
            }
            Thread.sleep(2000);

        }
    }
}