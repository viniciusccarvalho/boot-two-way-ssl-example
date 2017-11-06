package io.pivotal.boot.clientauth;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.InputStream;

/**
 * @author Vinicius Carvalho
 */
@SpringBootApplication
public class ClientAuthApplication {

	final static String KEYSTORE_PASSWORD = "s3cr3t";

	static
	{
		File jksFile = null;
		try {
			ClassPathResource classPathResource = new ClassPathResource("client.jks");
			InputStream inputStream = classPathResource.getInputStream();
			jksFile = File.createTempFile("client", ".jks");
			try {
				FileUtils.copyInputStreamToFile(inputStream, jksFile);
			} finally {
				IOUtils.closeQuietly(inputStream);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}



		System.setProperty("javax.net.ssl.trustStore", jksFile.getAbsolutePath());
		System.setProperty("javax.net.ssl.trustStorePassword", KEYSTORE_PASSWORD);
		System.setProperty("javax.net.ssl.keyStore", jksFile.getAbsolutePath());
		System.setProperty("javax.net.ssl.keyStorePassword", KEYSTORE_PASSWORD);

		javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
				(hostname, sslSession) -> {
                    if (hostname.equals("localhost")) {
                        return true;
                    }
                    return false;
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
