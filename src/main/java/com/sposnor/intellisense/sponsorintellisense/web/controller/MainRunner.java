package com.sposnor.intellisense.sponsorintellisense.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.sposnor.intellisense.sponsorintellisense.data.model.SponsorReceipts;
import com.sposnor.intellisense.sponsorintellisense.data.model.ViewEnroll;

public class MainRunner {

	public static void main(String[] args) {
		List<ViewEnroll> enrolls = getViewEnrollment();
		List<SponsorReceipts> receipts =  getSponsorReceipts();
		
		List<ViewEnroll> enrolls2 = receipts
			.stream()
			.flatMap(x -> enrolls
							.stream()
							.filter(y -> x.getSponsorId() == y.getSponsorId()))
							.collect(Collectors.toList());
		System.out.println("hai");
		System.out.println(enrolls2);
		//.map( ve -> ve.getSponsorId()).collect(receipts.)
	}
	
	public static List<ViewEnroll> getViewEnrollment() {
		List<ViewEnroll> enrolls = new ArrayList<ViewEnroll>();
		ViewEnroll ve1212 = new ViewEnroll();
		ve1212.setSponsorId(1212L);
		enrolls.add(ve1212);
		ViewEnroll ve1213 = new ViewEnroll();
		ve1213.setSponsorId(1213L);
		enrolls.add(ve1213);
		ViewEnroll ve1214 = new ViewEnroll();
		ve1214.setSponsorId(1214L);
		enrolls.add(ve1214);
		return enrolls;
	}

	public static List<SponsorReceipts> getSponsorReceipts() {
		List<SponsorReceipts> enrolls = new ArrayList<SponsorReceipts>();
		SponsorReceipts ve1212 = new SponsorReceipts();
		ve1212.setSponsorId(1212L);
		enrolls.add(ve1212);
		SponsorReceipts ve1213 = new SponsorReceipts();
		ve1213.setSponsorId(1213L);
		enrolls.add(ve1213);
		SponsorReceipts ve1214 = new SponsorReceipts();
		ve1214.setSponsorId(1214L);
		enrolls.add(ve1214);
		return enrolls;
	}
	
}
