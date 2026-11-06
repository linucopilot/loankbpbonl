package kh.com.copilot.app.common.dto;

import kh.com.copilot.app.common.constants.Constants;
import lombok.Builder;

@Builder
public class ResponseErrorMessageDTO {

	private String errorMsg;
	private String exceptionTrace;
	private String errChnlDstcd;
	private String treatMsg;
	private String treatCd;
	private String customMsg;
	private String uuid;

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getExceptionTrace() {
		return exceptionTrace;
	}

	public void setExceptionTrace(String exceptionTrace) {
		this.exceptionTrace = exceptionTrace;
	}

	public String getErrChnlDstcd() {
		return errChnlDstcd;
	}

	public void setErrChnlDstcd(String errChnlDstcd) {
		this.errChnlDstcd = errChnlDstcd;
	}

	public String getTreatMsg() {
		return treatMsg;
	}

	public void setTreatMsg(String treatMsg) {
		this.treatMsg = treatMsg;
	}

	public String getTreatCd() {
		return treatCd;
	}

	public void setTreatCd(String treatCd) {
		this.treatCd = treatCd;
	}

	public String getCustomMsg() {
		return customMsg;
	}

	public void setCustomMsg(String customMsg) {
		this.customMsg = customMsg;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public ResponseErrorMessageDTO(String errorMsg, String exceptionTrace, String errChnlDstcd, String treatMsg,
			String treatCd, String customMsg, String uuid) {
		this.errorMsg = errorMsg == null ? "" : errorMsg;
		this.exceptionTrace = exceptionTrace == null ? "" : exceptionTrace;
		this.errChnlDstcd = errChnlDstcd == null ? "" : errChnlDstcd + " : " + this.getChnlDstcd(errChnlDstcd);
		this.treatMsg = treatMsg == null ? "" : treatMsg;
		this.treatCd = treatCd == null ? "" : treatCd;
		this.customMsg = customMsg == null ? "" : customMsg;
		this.uuid = uuid == null ? "" : uuid;
	}

	private String getChnlDstcd(String chnlDstcd) {
		String chnlDstNm = "";
		switch (chnlDstcd) {
		case Constants.INNER:
			chnlDstNm = "INNER";
			break;
		case Constants.FLEXCUBE:
			chnlDstNm = "FLEXCUBE";
			break;
		case Constants.MOBILE:
			chnlDstNm = "MOBILE";
			break;
		case Constants.EXTERNAL:
			chnlDstNm = "EXTERNAL";
			break;
		default:
			chnlDstNm = "";
			break;
		}
		return chnlDstNm;
	}
}
