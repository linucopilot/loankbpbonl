package kh.com.copilot.app.common.exception;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class BusinessException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	protected String errorCode;
	protected String treatCode;
	protected String customMsg;

	/*
	 * 코드값 세팅(DB에서 에러 메세지, 조치 메시지 조회)
	 */
	public BusinessException(String errorCode, String treatCode) {
		super();
		this.errorCode = errorCode;
		this.treatCode = treatCode;
	}

	/*
	 * 코드값 세팅(DB에서 에러 메세지, 조치 메시지 조회 + 사용자 정의 메시지)
	 */
	public BusinessException(String errorCode, String treatCode, String customMsg) {
		super();
		this.errorCode = errorCode;
		this.treatCode = treatCode;
		this.customMsg = customMsg;
	}

	/*
	 * 코드값과 Throwable(DB에서 에러 메세지 조회, 조치 메시지 조회 + exception trace)
	 */
	public BusinessException(String errorCode, String treatCdoe, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
		this.treatCode = treatCdoe;
	}

	/*
	 * 코드값 세팅(DB에서 에러 메세지, 조치 메시지 조회 + 사용자 정의 메시지, error cause)
	 */
	public BusinessException(String errorCode, String treatCode, String customMsg, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
		this.treatCode = treatCode;
		this.customMsg = customMsg;
	}
}
