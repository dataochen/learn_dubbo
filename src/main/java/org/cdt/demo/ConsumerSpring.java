package org.cdt.demo;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author dataochen
 * @Description
 * @date: 2019/9/9 11:38
 */
@Component
public class ConsumerSpring implements InitializingBean {
    @Resource(name = "demoServiceConsumer")
    private DemoService demoServiceConsumer;
    @Override
    public void afterPropertiesSet() throws Exception {
        while (true) {
            Thread.sleep(3000);
            try {
                exe();
            } catch (Exception e) {
                System.out.println("eeee");
            }
        }

    }

    public void exe() {
        String cdt = demoServiceConsumer.sayHello("cdt");
        System.out.println("==="+cdt);
    }
}
