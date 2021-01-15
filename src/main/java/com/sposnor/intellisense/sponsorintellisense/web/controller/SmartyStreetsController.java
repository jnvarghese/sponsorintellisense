package com.sposnor.intellisense.sponsorintellisense.web.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_autocomplete_pro.Lookup;
import com.smartystreets.api.us_autocomplete_pro.Suggestion;
import com.sposnor.intellisense.sponsorintellisense.config.SmartyStreetsConfig;

@RestController
@RequestMapping("/api/street")
public class SmartyStreetsController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SmartyStreetsController.class);
	
	@Autowired
	private SmartyStreetsConfig streetConfig;
	
	@GetMapping("/lookup/{term}")
	public Suggestion[] lookUpStreet(@PathVariable(value = "term") String searchTerm) {
		Lookup lookup = new Lookup(searchTerm);
		try {
			//System.out.println(lookup.getStreet());
			streetConfig.getSmartyStreetClient().send(lookup);
			System.out.println(lookup.getResult());
		} catch (SmartyException e) {
			LOGGER.error("SmartyException while look up street"+ e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			LOGGER.error("IOException while look up street"+ e.getMessage());
			e.printStackTrace();
		} finally {
			//LOGGER.debug(lookup.getResult());
		}
		return lookup.getResult();
	}
}
