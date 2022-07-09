package com.edddoubled.sudokuninja;

import com.edddoubled.sudokuninja.repository.SolutionMetadataRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = SolutionMetadataRepository.class)
public class SudokuNinjaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SudokuNinjaApplication.class, args);
	}

}
