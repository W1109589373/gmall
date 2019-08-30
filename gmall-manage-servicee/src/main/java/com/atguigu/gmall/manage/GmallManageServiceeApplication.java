package com.atguigu.gmall.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.atguigu.gmall.manage.mapper")
@ComponentScan("com.atguigu.gmall")
public class GmallManageServiceeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GmallManageServiceeApplication.class, args);
	}

}
