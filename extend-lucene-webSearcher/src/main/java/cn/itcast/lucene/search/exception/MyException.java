package cn.itcast.lucene.search.exception;

public class MyException extends Exception {

	private static final long serialVersionUID = 1L;
	private String expMessage;
	
	public MyException() {
		
	}
	public MyException(String msg) {
		this.expMessage = msg;
	}

	public String getExpMessage() {
		return expMessage;
	}

	public void setExpMessage(String expMessage) {
		this.expMessage = expMessage;
	}
	
}
