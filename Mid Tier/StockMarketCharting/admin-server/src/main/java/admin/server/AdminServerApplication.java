package admin.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AdminServerApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(AdminServerApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
	    return builder.sources(AdminServerApplication.class);
	}

}
