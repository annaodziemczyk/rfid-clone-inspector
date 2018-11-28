package com.cit.notifier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.cit.notifier"})
@SpringBootApplication
public class NotifierApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotifierApplication.class, args);
	}
}
