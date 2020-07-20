package com.auction.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.auction.app.model.Bid;

@Repository
public interface BidRespository extends MongoRepository<Bid, String> {
	
}
