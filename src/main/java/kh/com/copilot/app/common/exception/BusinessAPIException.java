package kh.com.copilot.app.common.exception;

public class BusinessAPIException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	protected String messageCode;
	protected String errorMessage;
	protected String chnlDstcd;

	public BusinessAPIException(String messageCode, String errorMessage, String chnlDstcd) {
		super();
		this.messageCode = messageCode;
		this.errorMessage = errorMessage;
		this.chnlDstcd = chnlDstcd;
	}

	public BusinessAPIException(String messageCode, String errorMessage, String chnlDstcd, Throwable cause) {
		super(cause);
		this.messageCode = messageCode;
		this.errorMessage = errorMessage;
		this.chnlDstcd = chnlDstcd;
	}

	public String getMessageCode() {
		return this.messageCode;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}

	public String getChnlDstcd() {
		return this.chnlDstcd;
	}
}
