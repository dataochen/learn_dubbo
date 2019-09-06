package org.cdt.demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;
 
public class Provider {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("provider.xml");
        System.out.println(1);
        context.start();
        System.out.println(2);
        System.in.read(); // 按任意键退出
    }
}