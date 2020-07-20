package com.auction.app.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.auction.app.model.Auction;
import com.auction.app.model.Bid;
import com.mongodb.client.result.DeleteResult;

@Service
public class MongoTemplateRepository {

	@Autowired
	MongoTemplate mongoTemplate;

	public Double findByCurrentBid() {

		Query query = new Query();
		query.with(new Sort(Sort.Direction.DESC, "currentBid"));
		query.limit(1);
		Auction auction = mongoTemplate.findOne(query, Auction.class);

		return auction.getCurrentBid();
	}

	public Auction upsert(Auction auction, Bid bid) {

		Query query = new Query(new Criteria("auctionItemId").is(auction.getAuctionItemId()));
		Update update = new Update();
		update.set("currentBid", bid.getMaxAutoBidAmount());
		mongoTemplate.updateFirst(query, update, Auction.class);
		return auction;
	}

	public DeleteResult deleteBidderName(String bidderName) {
		Query query = new Query(new Criteria("bidderName").is(bidderName));

		return mongoTemplate.remove(query, Bid.class);

	}

}
