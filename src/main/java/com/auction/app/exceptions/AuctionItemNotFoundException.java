package com.auction.app.exceptions;

public class AuctionItemNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public AuctionItemNotFoundException(String exceptionMessage) {
		super(exceptionMessage);
	}
	

}
