package com.auction.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auction.app.exceptions.BidOutException;
import com.auction.app.model.Bid;
import com.auction.app.service.BidService;

@RestController
@RequestMapping(value = "/")
public class BidController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	BidService bidService;
		
	@PostMapping(value = "bids")
	public HttpStatus saveBid(@RequestBody Bid bid) throws Exception{
		LOG.info("Saving bid: ", bid);
		bidService.saveBid(bid);
		return HttpStatus.OK;
	}
	
	@DeleteMapping(value = "outbid/{bidderName}")
	public void saveBid(@PathVariable String bidderName) throws Exception{
		
		 Long outbidCount = bidService.outbid(bidderName);
		 throw new BidOutException("The Bidder with name: "+ bidderName+" outbid successfully.", outbidCount);
		
	}

}
