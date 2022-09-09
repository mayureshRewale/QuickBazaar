package com.qb.Commons.Exception;

public class IdleTimeoutException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public IdleTimeoutException(String message)
    {
        super(message);

    }

}
