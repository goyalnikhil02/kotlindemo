package codemwnci.bootdemo;

public class MyFileNotFoundException extends Exception {
	String message;
	Exception ex;

	public MyFileNotFoundException(String message, Exception ex) {
		super();
		this.message = message;
		this.ex = ex;
	}

	public MyFileNotFoundException(String message) {
		// TODO Auto-generated constructor stub
		this.message = message;
	}

}
