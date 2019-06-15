package codemwnci.bootdemo;

public class FileStorageException extends Exception {
	String message;
	Exception ex;

	public FileStorageException(String message, Exception ex) {
		super();
		this.message = message;
		this.ex = ex;
	}

public FileStorageException(String message) {
	// TODO Auto-generated constructor stub
	this.message=message;
}

}
