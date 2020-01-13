package cn.wolfcode.getip;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.wolfcode.getip.mapper")
public class GetipApplication {

	public static void main(String[] args) {
		SpringApplication.run(GetipApplication.class, args);
	}

}
