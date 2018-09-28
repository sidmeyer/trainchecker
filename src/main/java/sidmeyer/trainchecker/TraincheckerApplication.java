package sidmeyer.trainchecker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TraincheckerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TraincheckerApplication.class, args);
        System.setProperty("java.awt.headless", "false");
    }
}
