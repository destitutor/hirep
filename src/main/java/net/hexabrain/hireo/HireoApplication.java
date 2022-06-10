package net.hexabrain.hireo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HireoApplication {

    public static void main(String[] args) {
        SpringApplication.run(HireoApplication.class, args);
    }

}
