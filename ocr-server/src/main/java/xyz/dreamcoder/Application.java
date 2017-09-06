package xyz.dreamcoder;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import xyz.dreamcoder.service.ActivationService;

@SpringBootApplication
@EnableAsync
public class Application implements InitializingBean {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	private final ActivationService activationService;

	@Autowired
	public Application(ActivationService activationService) {
		this.activationService = activationService;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
//		activationService.verify();
	}
}