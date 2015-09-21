package io.pivotal.boot.clientauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author Vinicius Carvalho
 */
@Component
public class HttpClient implements CommandLineRunner {

	@Autowired
	private RestTemplate template;

	@Override
	public void run(String... args) throws Exception {
		ResponseEntity<String> response = template.getForEntity(args[0], String.class);
		System.out.println(response.getBody());
	}
}
