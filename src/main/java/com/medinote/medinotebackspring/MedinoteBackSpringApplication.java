package com.medinote.medinotebackspring;

import com.medinote.medinotebackspring.config.properties.AppProperties;
import com.medinote.medinotebackspring.openai.OpenAIProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		AppProperties.class,
		OpenAIProperties.class
})
public class MedinoteBackSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedinoteBackSpringApplication.class, args);
	}

}
