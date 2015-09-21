package io.pivotal.boot.clientauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author Vinicius Carvalho
 */
@SpringBootApplication
public class ClientAuthApplication {

	final static String KEYSTORE_PASSWORD = "s3cr3t";

	static
	{
		System.setProperty("javax.net.ssl.trustStore", ClientAuthApplication.class.getClassLoader().getResource("client.jks").getFile());
		System.setProperty("javax.net.ssl.trustStorePassword", KEYSTORE_PASSWORD);
		System.setProperty("javax.net.ssl.keyStore", ClientAuthApplication.class.getClassLoader().getResource("client.jks").getFile());
		System.setProperty("javax.net.ssl.keyStorePassword", KEYSTORE_PASSWORD);

		javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
				new javax.net.ssl.HostnameVerifier() {

					public boolean verify(String hostname,
							javax.net.ssl.SSLSession sslSession) {
						if (hostname.equals("localhost")) {
							return true;
						}
						return false;
					}
				});
	}

	@Bean
	public RestTemplate template() throws Exception{
		RestTemplate template = new RestTemplate();
		return template;
	}

	public static void main(String[] args) {
		SpringApplication.run(ClientAuthApplication.class,args);
	}

}
