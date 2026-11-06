package kh.com.copilot.app.common.exception;

import java.io.StringWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import jakarta.servlet.http.HttpServletRequest;
import kh.com.copilot.app.common.constants.Constants;
import kh.com.copilot.app.common.dto.ErrorMessageDTO;
import kh.com.copilot.app.common.dto.ResponseErrorMessageDTO;
import kh.com.copilot.app.common.dto.ResultPayload;
import kh.com.copilot.app.csm.dao.MsgMgtDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler implements ResponseBodyAdvice<Object> {
	private static final String UNKNOWN_ERROR_MESSAGE = "An error occurred during the transaction. Please check the transaction status and try again.";
	private final MsgMgtDao msgMgtDao;
	
	public GlobalExceptionHandler(MsgMgtDao dMgtDao) {
		this.msgMgtDao = dMgtDao;
	}

	@ResponseBody
	@ExceptionHandler(BusinessException.class)
	public ResultPayload handleBusinessException(HttpServletRequest request, BusinessException e) {
//		log.error("BusinessException", e);		
		return this.getErrorMessage(request, e);
	}
	
	@ResponseBody
	@ExceptionHandler(BusinessAPIException.class)
	public ResultPayload handleBusinessApiException(HttpServletRequest request, BusinessAPIException e) {
//		log.error("BusinessAPIException", e);
		return this.getErrorMessage(request, e);
	}

	/** system exception처리 */
	@ResponseBody
	@ExceptionHandler(Exception.class)
	public ResultPayload handleUnknownException(HttpServletRequest request, Exception e) {
//		log.error("handleUnknownException", e);
		return this.getErrorMessage(request, e);
	}

	private ResultPayload getErrorMessage(HttpServletRequest request, Throwable e) {
		ResultPayload rPayload = new ResultPayload();

		if (e instanceof BusinessException) {
			rPayload = getBusinessException(request, (BusinessException) e);
		} else if (e instanceof BusinessAPIException) {
			rPayload = getBusinessAPIException(request, (BusinessAPIException) e);
		} else if (e instanceof MaxUploadSizeExceededException) {
			rPayload.setResultCd(Constants.DEFUALT_ERROR_CODE);
			rPayload.setResultData(ResponseErrorMessageDTO.builder()
					.errorMsg("Maximum upload size exceeded. Please upload a file smaller than 20 MB.")
					.exceptionTrace(getPrintStackTrace(e)).build());
		} else {
			rPayload.setResultCd(Constants.DEFUALT_ERROR_CODE);
				rPayload.setResultData(ResponseErrorMessageDTO.builder().errorMsg(UNKNOWN_ERROR_MESSAGE)
					.exceptionTrace(getPrintStackTrace(e)).build());
		}
		return rPayload;
	}

	private ResultPayload getBusinessAPIException(HttpServletRequest request, BusinessAPIException e) {
		ResultPayload rPayload = new ResultPayload();
		rPayload.setResultCd(e.getMessageCode());
		rPayload.setResultData(ResponseErrorMessageDTO.builder().errorMsg(e.getErrorMessage()).errChnlDstcd(e.getChnlDstcd())
				.exceptionTrace(getPrintStackTrace(e)).build());

		return rPayload;
	}

	private ResultPayload getBusinessException(HttpServletRequest request, BusinessException e) {
//		log.debug("getBusinessException {}", request);
		ResultPayload rPayload = new ResultPayload();
		Map<String, Object> param = new HashMap<>();
		param.put("langDstcd", Objects.toString(request == null ? null : request.getAttribute("langDstcd"), ""));
		param.put("list", Arrays.asList(e.errorCode, e.treatCode));

		List<ErrorMessageDTO> result = e.errorCode == null ? null : msgMgtDao.selectErrorCodeList(param);
		rPayload.setResultCd(e.errorCode);
		if (result == null) {
			rPayload.setResultCd(Constants.DEFUALT_ERROR_CODE);
			rPayload.setResultData(ResponseErrorMessageDTO.builder().errorMsg("The Error Code is not defined")
					.exceptionTrace(getPrintStackTrace(e)).errChnlDstcd(Constants.INNER)
					.treatCd(Constants.DEFUALT_TREAT_CODE).customMsg(e.customMsg == null ? "" : e.customMsg)
					.treatMsg("Please register the error code on the this page.").build());

		} else {
			ErrorMessageDTO treatment = result.stream().filter(p -> "T".equals(p.getMsgKndCdNo())).findFirst().orElse(null);
			ErrorMessageDTO error = result.stream().filter(p -> !"T".equals(p.getMsgKndCdNo())).findFirst().orElse(null);

			rPayload.setResultData(ResponseErrorMessageDTO.builder()
					.errorMsg(error != null ? error.getOutptCtnt1() : "The Error Code is not defined")
					.errChnlDstcd(error != null ? error.getChnlDstcd() : Constants.INNER)
					.exceptionTrace(getPrintStackTrace(e)).treatCd(e.treatCode)
					.treatMsg(treatment != null ? treatment.getOutptCtnt1() : "")
					.customMsg(e.customMsg == null ? "" : e.customMsg).build());

		}

		return rPayload;
	}

	private String getPrintStackTrace(Throwable e) {
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		return errors.toString();
	}

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		// TODO Auto-generated method stub
		return ResultPayload.class.isAssignableFrom(returnType.getParameterType());
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		if (body instanceof ResultPayload) {
			ResultPayload rPayload = (ResultPayload) body;
			if (rPayload.getResultData() instanceof Map) {
				Map<String, Object> rMap = (Map<String, Object>) rPayload.getResultData();
				rMap.put("uuid", requestId(request));
				rPayload.setResultData(rMap);
			} else if (rPayload.getResultData() instanceof ResponseErrorMessageDTO) {
				ResponseErrorMessageDTO rDto = (ResponseErrorMessageDTO) rPayload.getResultData();
				rDto.setUuid(requestId(request));
				rPayload.setResultData(rDto);
			}

			body = rPayload;
		}
		return body;
	}

	private String requestId(ServerHttpRequest request) {
		if (request instanceof org.springframework.http.server.ServletServerHttpRequest servletRequest) {
			return Objects.toString(servletRequest.getServletRequest().getAttribute("UUID"), "");
		}
		return "";
	}

}
