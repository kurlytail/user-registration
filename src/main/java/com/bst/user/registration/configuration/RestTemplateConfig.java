package com.bst.user.registration.configuration;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

	@Bean
	public HttpClient httpClient() {
		return HttpClientBuilder.create().build();
	}

	@Bean
	public ClientHttpRequestFactory httpRequestFactory(final HttpClient httpClient) {
		return new HttpComponentsClientHttpRequestFactory(httpClient);
	}

	@Bean
	public RestTemplate restTemplate(final ClientHttpRequestFactory httpRequestFactory) {
		final RestTemplate template = new RestTemplate(httpRequestFactory);
		template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		return template;
	}

}
