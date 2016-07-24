package hr.aportolan.kps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:hr/aportolan/kps/datasource-config.xml")
public class KpsApplication {

	public static void main(String[] args) {
		SpringApplication.run(KpsApplication.class, args);
	}
}
