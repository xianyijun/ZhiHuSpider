package me.kuye.spider.exception;

public class NetworkStateException extends RuntimeException {
	private static final long serialVersionUID = -6868931386861260219L;

	public NetworkStateException() {
		super();
	}

	public NetworkStateException(Throwable cause) {
		super(cause);
	}

	public NetworkStateException(String message) {
		super(message);
	}

	public NetworkStateException(String message, Throwable cause) {
		super(message, cause);
	}
}
