package com.ll.chat_ai;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.jdbc.core.JdbcTemplate;

@EnableJpaAuditing // JPA Auditing 활성화 생성일, 수정일 자동 주입
@SpringBootApplication
public class ChatAiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatAiApplication.class, args);
	}


	@Bean
	public CommandLineRunner commandLineRunner(JdbcTemplate jdbcTemplate) {
		return args -> {
			try {
				jdbcTemplate.queryForObject("SELECT 1", Integer.class);
				System.out.println("데이터베이스 연결 성공!");
			} catch (Exception e) {
				System.err.println("데이터베이스 연결 실패: " + e.getMessage());
			}
		};
	}
}
