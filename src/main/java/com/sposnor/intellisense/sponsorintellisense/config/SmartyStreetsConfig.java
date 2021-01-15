package com.sposnor.intellisense.sponsorintellisense.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.smartystreets.api.ClientBuilder;
import com.smartystreets.api.SharedCredentials;
import com.smartystreets.api.us_autocomplete_pro.Client;

@Configuration
public class SmartyStreetsConfig {

	private String key = System.getProperty("SMARTY_AUTH_WEB");
	
	private String hostname = System.getProperty("SMARTY_AUTH_REFERER");
	
	
	@Bean
	public Client getSmartyStreetClient() {
        SharedCredentials credentials = new SharedCredentials(key, hostname);
        Client client = new ClientBuilder(credentials).buildUsAutocompleteProApiClient();
		return client;
	}
	
}
