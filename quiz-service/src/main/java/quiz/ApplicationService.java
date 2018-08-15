package quiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;
import static java.util.Arrays.asList;

@SpringBootApplication
@EnableAutoConfiguration
public class ApplicationService {

    public static void main(String[] args) {
        final Set<Class<?>> basePackageClasses = new HashSet<>(asList(ApplicationService.class));
        SpringApplication.run(basePackageClasses.toArray(new Class[] {}), args);
    }
}