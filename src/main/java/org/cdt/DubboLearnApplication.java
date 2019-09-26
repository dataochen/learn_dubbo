package org.cdt;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chendatao
 */
@SpringBootApplication
@EnableDubbo
public class DubboLearnApplication {

	public static void main(String[] args) {
		SpringApplication.run(DubboLearnApplication.class, args);
	}

}
