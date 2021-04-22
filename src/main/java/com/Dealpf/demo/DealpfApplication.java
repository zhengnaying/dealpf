package com.Dealpf.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@MapperScan("com.Dealpf.demo.Mapper")
@SpringBootApplication
public class DealpfApplication  {

	public static void main(String[] args) {
		SpringApplication.run(DealpfApplication.class, args);
	}

}
