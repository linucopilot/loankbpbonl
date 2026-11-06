package kh.com.copilot.app.common.dto;

public class ResultPayload {
	private String resultCd = "0000";
	private Object resultData;

	public String getResultCd() {
		return resultCd;
	}

	public void setResultCd(String resultCd) {
		this.resultCd = resultCd;
	}

	public Object getResultData() {
		return resultData;
	}

	public void setResultData(Object resultData) {
		this.resultData = resultData;
	}
}
