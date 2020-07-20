package com.auction.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auction.app.exceptions.AuctionItemNotFoundException;
import com.auction.app.model.Auction;
import com.auction.app.model.Bid;
import com.auction.app.repository.AuctionRepository;
import com.auction.app.repository.BidRespository;
import com.auction.app.repository.MongoTemplateRepository;

@Service
public class BidService {

	@Autowired
	BidRespository bidrepository;

	@Autowired
	AuctionRepository auctionRepository;
	
	@Autowired
	MongoTemplateRepository auctionMongoTemplateRepository;

	public void saveBid(Bid bid) throws Exception {

		Auction auction = auctionRepository.findByAuctionItemId(bid.getAuctionItemId()); 
		
		int a = bid.getMaxAutoBidAmount().intValue(); 
		
		Double temp = (bid.getMaxAutoBidAmount() - Double.parseDouble(String.valueOf(a))); 
		if(temp != 0 )
			bid.setMaxAutoBidAmount(bid.getMaxAutoBidAmount()+(1-temp)); 
		
		if(auction == null || auction.getAuctionItemId().isEmpty()) {
			throw new AuctionItemNotFoundException("There is no Auction with AutionItemId: "+bid.getAuctionItemId());
		}
		
		Double maxOfCurrentBid = auctionMongoTemplateRepository.findByCurrentBid();

		if(maxOfCurrentBid > bid.getMaxAutoBidAmount()){
			bid.setMaxAutoBidAmount(maxOfCurrentBid);
			bidrepository.save(bid);
			throw new IllegalArgumentException("You have not met the reserve price");
		}
		
		if(maxOfCurrentBid < bid.getMaxAutoBidAmount()){
			auctionMongoTemplateRepository.upsert(auction, bid);
		}
		
		bidrepository.save(bid);
		
	} 
	
	public Long outbid(String bidderName) {
		
		return auctionMongoTemplateRepository.deleteBidderName(bidderName).getDeletedCount();
		
	}
}
