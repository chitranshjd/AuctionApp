package com.auction.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.auction.app.model.Auction;
import com.auction.app.service.AuctionService;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/")
public class AuctionController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	AuctionService auctionService;

	@PostMapping(value = "auctionItems")
	@ResponseStatus(value = HttpStatus.CREATED)	// 201 status code
	public String saveAuctionItem(@RequestBody Auction auction) throws Exception {
		LOG.info("Auction Item saved to database");
		Auction auction2 = new Auction(auctionService.save(auction).getAuctionItemId());
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		String response = mapper.writeValueAsString(auction2);
		
		return response;
	}

	@GetMapping(value = "auctionItems")
	public List<Auction> getAllAuction() throws Exception {
		LOG.info("Getting all auction items");
		return auctionService.findAll();
	}

	@GetMapping(value = "auctionItems/{auctionItemId}")
	public @ResponseBody Auction getAuction(@PathVariable String auctionItemId) throws Exception {
		LOG.info("auction for auctionItem", auctionItemId);
		return auctionService.findByAuctionItemId(auctionItemId);
	}

}
