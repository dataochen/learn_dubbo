package org.cdt.demo;

import com.alibaba.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @Reference(check = false)
    private DemoService demoServiceConsumer;

    @PostConstruct
    public void ee() throws Exception {
        LOGGER.info("====cdt ee start");
        new Thread(() -> {

            while (true) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    exe();
                } catch (Exception e) {
                    LOGGER.error(" afterPropertiesSet e={}", e);
                }
            }
        }).start();

    }

    public void exe() {
        String cdt = demoServiceConsumer.sayHello("cdt");
        System.out.println("===" + cdt);
    }
}
