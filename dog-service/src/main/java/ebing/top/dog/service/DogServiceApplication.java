package ebing.top.dog.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableDiscoveryClient
@EnableFeignClients
@EnableTransactionManagement
@SpringBootApplication
public class DogServiceApplication {

	public static void main(String[] args) {
		System.out.println("====  dog-service 准备启动  ====");
		SpringApplication.run(DogServiceApplication.class, args);
		System.out.println("====  dog-service 启动成功  ====");
	}
}
