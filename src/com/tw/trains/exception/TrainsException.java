package com.tw.trains.exception;

public class TrainsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1171077903094510489L;

	public TrainsException ()
	{
		super();
	}
	public TrainsException(String message) {
		super(message);
	}
	
	public TrainsException(Throwable cause) {
		super(cause);
	}
	

	public TrainsException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TrainsException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
