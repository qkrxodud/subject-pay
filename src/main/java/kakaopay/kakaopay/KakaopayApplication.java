package kakaopay.kakaopay;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.persistence.EntityManager;

@EnableSwagger2
@SpringBootApplication
public class KakaopayApplication {

	public static void main(String[] args) {
		SpringApplication.run(KakaopayApplication.class, args);
	}

	@Bean
	JPAQueryFactory queryFactory (EntityManager em) {
		return new JPAQueryFactory(em);
	}
}
