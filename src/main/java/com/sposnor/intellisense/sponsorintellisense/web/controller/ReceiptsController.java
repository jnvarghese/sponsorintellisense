package com.sposnor.intellisense.sponsorintellisense.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sposnor.intellisense.sponsorintellisense.data.model.Receipts;
import com.sposnor.intellisense.sponsorintellisense.mapper.ReceiptsMapper;

@RestController
@RequestMapping("/api/receipts")
public class ReceiptsController {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReceiptsController.class);
	
	@Autowired
	private ReceiptsMapper receiptsMapper;
	
	@GetMapping("/list")
	public List<Receipts> list() {
		return receiptsMapper.list();
	}
	
	@GetMapping("/listbyparish/{id}")
	public List<Receipts> listByParishId(@PathVariable(value = "id") Long parishId) {
		return receiptsMapper.listByParishId(parishId);	
	}
	
	@GetMapping("/find/{id}")
	public Receipts listById(@PathVariable(value = "id") Long receiptId) {
		return receiptsMapper.findById(receiptId);	
	}
	
	@PostMapping("/add")
	public void createReceipts(@RequestHeader Long userId, @Valid @RequestBody Receipts r) {
		r.setCreatedby(userId);
		receiptsMapper.insert(r);
	}
	
	@PutMapping("/modify/{id}")
	public void updateReceipts(@RequestHeader Long userId, @Valid @RequestBody Receipts r) {		
		r.setUpdatedBy(userId);
		receiptsMapper.update(r);
	}
}
