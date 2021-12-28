package fr.clementperrin.notier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "fr.clementperrin.notier")
public class NotierApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotierApplication.class, args);

    }

}
