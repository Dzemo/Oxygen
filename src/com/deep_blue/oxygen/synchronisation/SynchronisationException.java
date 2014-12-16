package com.deep_blue.oxygen.synchronisation;

public class SynchronisationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param detailMessage
	 * @param throwable
	 */
	public SynchronisationException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	/**
	 * @param detailMessage
	 */
	public SynchronisationException(String detailMessage) {
		super(detailMessage);
	}

	/**
	 * @param throwable
	 */
	public SynchronisationException(Throwable throwable) {
		super(throwable);
	}

}
