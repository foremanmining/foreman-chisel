package mn.foreman.chisel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** The chisel application. */
@SpringBootApplication
public class Application {

    /**
     * Application entry point.
     *
     * @param args The command line arguments.
     */
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
