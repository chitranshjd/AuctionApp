package com.auction.app.exceptions;

public class BidOutException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public BidOutException() {
	}
	
	
	public BidOutException(String exceptionMessage) {
		super(exceptionMessage);
	}
	
	public BidOutException(String exceptionMessage, Long deleteResult) {
		super(exceptionMessage);
		this.deleteResult = deleteResult;
	}
	
	
	private Long deleteResult;

	public Long getDeleteResult() {
		return deleteResult;
	}

	public void setDeleteResult(Long deleteResult) {
		this.deleteResult = deleteResult;
	}

}
