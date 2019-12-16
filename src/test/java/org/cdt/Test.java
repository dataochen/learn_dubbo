package org.cdt;

import org.cdt.demo.DemoService;
import org.cdt.demo.DemoService2Impl;
import org.cdt.demo.JdkProxy;

/**
 * @author dataochen
 * @Description
 * @date: 2019/10/24 20:37
 */
public class Test {
    public static void main(String[] args) {
        DemoService2Impl demoService2 = new DemoService2Impl();
        JdkProxy<DemoService> demoServiceJdkProxy = new JdkProxy<>(demoService2);
        DemoService proxy = demoServiceJdkProxy.getProxy();
        String cdt = proxy.sayHello("cdt");
        System.out.println(cdt);
    }
}
