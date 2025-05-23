package com.springbatch.migracaodados;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MultithreadingStepApplication {

	public static void main(String[] args) {
//		SpringApplication.run(MultithreadingStepApplication.class, args); 
//		por causa da multithread tem de forçar para fechar a aplicação com thread
		ConfigurableApplicationContext context = SpringApplication.run(MultithreadingStepApplication.class, args);
		context.close();
	}

}
