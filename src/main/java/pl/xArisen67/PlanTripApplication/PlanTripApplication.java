package pl.xArisen67.PlanTripApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PlanTripApplication implements AsyncConfigurer {
	public static void main(String[] args) {
		SpringApplication.run(PlanTripApplication.class, args);
	}
}
