package org.cdt.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author dataochen
 * @Description
 * @date: 2019/9/9 11:38
 */
@Component
public class ConsumerSpring {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerSpring.class);
    @Autowired
    private DemoService demoServiceConsumer;

    @PostConstruct
    public void ee() throws Exception {
        LOGGER.info("====cdt ee start");
        while (true) {
            Thread.sleep(3000);
            try {
                exe();
            } catch (Exception e) {
                LOGGER.error(" afterPropertiesSet e={}", e);
            }
        }

    }

    public void exe() {
        String cdt = demoServiceConsumer.sayHello("cdt");
        System.out.println("===" + cdt);
    }
}
