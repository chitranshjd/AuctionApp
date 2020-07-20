package com.auction.app.model;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Document(collection = "auction")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Auction {

	private String auctionItemId = getRandomIntegerBetweenRange(10000, 99999)+"";
	private Double currentBid = 0.0;
	private Double reservePrice = 0.0;
	private Item item;
	
	public Auction() {
		
	}

	public Auction(String auctionItemId) {
		this.auctionItemId = auctionItemId;
		this.currentBid = null;
		this.reservePrice = null;
	}
	
	public String getAuctionItemId() {
		return auctionItemId;
	}
	public Double getCurrentBid() {
		return currentBid;
	}
	public void setCurrentBid(Double currentBid) {
		this.currentBid = currentBid;
	}
	public Double getReservePrice() {
		return reservePrice;
	}
	public void setReservePrice(Double reservePrice) {
		this.reservePrice = reservePrice;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}

	@Override
	public String toString() {
		return "Auction [auctionItemId=" + auctionItemId + ", currentBid=" + currentBid + ", reservePrice="
				+ reservePrice + ", item=" + item;
	}

	public static Integer getRandomIntegerBetweenRange(double min, double max){
		return (int)((int)(Math.random()*((max-min)+1))+min);
	}

}
