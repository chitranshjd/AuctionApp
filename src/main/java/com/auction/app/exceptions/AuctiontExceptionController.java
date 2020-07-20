package com.auction.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AuctiontExceptionController{
	
	@ExceptionHandler(value = AuctionItemNotFoundException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	public String exception(AuctionItemNotFoundException exception) {
		
		return exception.getMessage();
	
	}
	
	@ExceptionHandler(value = BidOutException.class)
	@ResponseStatus(value=HttpStatus.ACCEPTED, reason="Bidder has been outbid successfully!")
	public BidOutException  exception(BidOutException exception) {
		
		return exception;
	
	}
	
	@ExceptionHandler(value = IllegalArgumentException.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason = "You have not met the reserve price")
	public String exception(IllegalArgumentException exception) {
		
		return exception.getMessage();
	
	}

	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	public String exception(Exception exception) {
		
		return exception.getMessage();
	
	}
}
