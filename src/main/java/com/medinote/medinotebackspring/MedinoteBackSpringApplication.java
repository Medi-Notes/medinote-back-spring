package com.medinote.medinotebackspring;

import com.medinote.medinotebackspring.config.properties.AppProperties;
import com.medinote.medinotebackspring.openai.OpenAIProperties;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.TimeZone;

@SpringBootApplication
@EnableConfigurationProperties({
		AppProperties.class,
		OpenAIProperties.class
})
public class MedinoteBackSpringApplication {

	@PostConstruct
	public void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}

	public static void main(String[] args) {
		SpringApplication.run(MedinoteBackSpringApplication.class, args);
	}

}
