package ebing.top.dog.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@EnableDiscoveryClient
//@EnableFeignClients

@MapperScan("ebing.top.dog.service.mapper")
@EnableAsync
@EnableTransactionManagement
@SpringBootApplication
public class DogServiceApplication {

	public static void main(String[] args) {
		System.out.println("====  dog_service 准备启动  ====");
		SpringApplication.run(DogServiceApplication.class, args);
		System.out.println("====  dog_service 启动成功  ====");
	}
}
