package com.alves.youtransfer;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class YoutransferApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure()
				.directory(".")
				.ignoreIfMalformed()
				.ignoreIfMissing()
				.load();

		Map<String, Object> envVars = new HashMap<>();
		dotenv.entries().forEach(entry -> envVars.put(entry.getKey(), entry.getValue()));

		new SpringApplicationBuilder(YoutransferApplication.class)
				.properties(envVars)
				.run(args);
	}
}
