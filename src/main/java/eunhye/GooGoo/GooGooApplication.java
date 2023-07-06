package eunhye.GooGoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GooGooApplication {

	public static void main(String[] args) {

		SpringApplication.run(GooGooApplication.class, args);
	}

}
