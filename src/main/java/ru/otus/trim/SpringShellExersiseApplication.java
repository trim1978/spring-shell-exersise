package ru.otus.trim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@SpringBootApplication
@ComponentScan(basePackages = "ru.otus.trim")
public class SpringShellExersiseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringShellExersiseApplication.class, args);
	}

//	public ApplicationEventMulticaster applicationEventMulticaster (){
//		SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
//		eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
//		return eventMulticaster;
//	}

}
