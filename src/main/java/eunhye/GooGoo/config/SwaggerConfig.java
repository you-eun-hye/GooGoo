package eunhye.GooGoo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI springOpenApi(){
        return new OpenAPI().info(new Info()
                .title("GooGoo API 문서")
                .version("1.1")
                .description("잘못된 부분이나 오류 발생 시 바로 말씀해주세요.")
                .contact(new Contact()
                        .name("유은혜")
                        .email("dkfvktorcp@naver.com")));
    }
}
