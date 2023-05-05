package com.nearme;

import java.text.ParseException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class NearMeApplication {

	public static void main(String[] args) throws ParseException {
		SpringApplication.run(NearMeApplication.class, args);
	}

}
