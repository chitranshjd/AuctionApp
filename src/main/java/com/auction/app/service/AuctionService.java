package com.auction.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auction.app.model.Auction;
import com.auction.app.repository.AuctionRepository;

@Service
public class AuctionService {
	
	@Autowired
	AuctionRepository auctionRepository;
	
	public List<Auction> findAll() throws Exception{
		return auctionRepository.findAll();
	}
	
	public Auction save (Auction auction) throws Exception {
		return auctionRepository.save(auction);
	}
	
	public Auction findByAuctionItemId(String auctionItemId) throws Exception {
		return auctionRepository.findByAuctionItemId(auctionItemId);
	}
	
	public void saveBid(Auction auction) throws Exception {
		auctionRepository.save(auction);
	}

}
