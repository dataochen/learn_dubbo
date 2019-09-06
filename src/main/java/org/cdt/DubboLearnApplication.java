package org.cdt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(value = "classpath:main.xml")
public class DubboLearnApplication {

	public static void main(String[] args) {
		SpringApplication.run(DubboLearnApplication.class, args);
	}

}
