package com.auction.app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Document(collection = "bid")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Bid {

	@Id
	private String id;
	private String auctionItemId;
	private Double maxAutoBidAmount = 0.0;
	private String bidderName;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getAuctionItemId() {
		return auctionItemId;
	}
	public void setAuctionItemId(String auctionItemId) {
		this.auctionItemId = auctionItemId;
	}
	
	public Double getMaxAutoBidAmount() {
		return maxAutoBidAmount;
	}
	public void setMaxAutoBidAmount(Double maxAutoBidAmount) {
		this.maxAutoBidAmount = maxAutoBidAmount;
	}
	public String getBidderName() {
		return bidderName;
	}
	public void setBidderName(String bidderName) {
		this.bidderName = bidderName;
	}
	@Override
	public String toString() {
		return "Bid [id=" + id + ", auctionItemId=" + auctionItemId + ", maxAutoBidAmount=" + maxAutoBidAmount
				+ ", bidderName=" + bidderName + "]";
	}
	
}
