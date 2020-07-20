package com.auction.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.auction.app.model.Auction;

@Repository
public interface AuctionRepository extends MongoRepository<Auction, String> {
	
	Auction findByAuctionItemId(String auctionItemId);

}
