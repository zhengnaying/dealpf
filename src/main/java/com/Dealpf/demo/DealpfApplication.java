package com.Dealpf.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.Dealpf.demo.Mapper")
@SpringBootApplication
@EnableScheduling
public class DealpfApplication  {

	public static void main(String[] args) {
		SpringApplication.run(DealpfApplication.class, args);
	}

}

