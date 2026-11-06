package kh.com.copilot.app.common.util;

import java.util.Enumeration;
import java.util.Locale;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kh.com.copilot.app.common.constants.Constants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpUtils {
	private HttpUtils() {
	}

	public static HttpServletRequest getCurrentRequest() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return attributes != null ? attributes.getRequest() : null;
	}

	public static HttpServletResponse getCurrentResponse() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return attributes != null ? attributes.getResponse() : null;
	}

	public static boolean isMultipartFormData(HttpServletRequest request) {
		if (request == null) {
			return false;
		}

		String contentType = request.getContentType();
		return contentType != null && contentType.toLowerCase(Locale.ROOT).contains("multipart");
	}

	public static boolean isMultipartFormData() {
		HttpServletRequest request = getCurrentRequest();
		return isMultipartFormData(request);
	}

	private static String getFirstIp(String ip) {
		if (StringUtil.isEmpty(ip)) {
			return "";
		}

		String normalizedIp = ip.trim();
		if (normalizedIp.isEmpty()) {
			return "";
		}

		String[] split = normalizedIp.split(",");
		return split[0].trim();
	}

	public static String getRemoteAddr(HttpServletRequest request) {
		if (request == null) {
			return "";
		}

		/*
		 * http://lesstif.com/pages/viewpage.action?pageId=20775886
		 *
		 * WAS 는 보통 2차 방화벽 안에 있고 Web Server 를 통해 client 에서 호출되거나 cluster로 구성되어 load
		 * balancer 에서 호출되는데 이럴 경우에서 getRemoteAddr() 을 호출하면 웹서버나 load balancer의 IP 가 나옴
		 * WebLogic 의 web server 연계 모듈인 weblogic connector 는 위 헤더를 사용하지 않고
		 * Proxy-Client-IP 나 WL-Proxy-Client-IP 를 사용하므로 weblogic 에서 도는 application 작성시
		 * 수정이 필요함
		 */
		String[] headerNames = { "X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP",
				"HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR" };
		for (String headerName : headerNames) {
			String clientIp = request.getHeader(headerName);
			if (!StringUtil.isEmpty(clientIp)) {
				return getFirstIp(clientIp);
			}
		}

		String remoteAddr = request.getRemoteAddr();
		return StringUtil.isEmpty(remoteAddr) ? "" : remoteAddr;
	}

	/**
	 * vuejs에서 넘기는 공통 해더.
	 *
	 * @param request
	 * @return
	 */
	public static JsonNode getVuejsCommonHeader(HttpServletRequest request) {
		if (request == null) {
			return null;
		}

		try {
			Enumeration<String> headers = request.getHeaderNames();
			while (headers != null && headers.hasMoreElements()) {
				String headerName = headers.nextElement();
				if (!Constants.KBPB_CLIENT_INFO.equalsIgnoreCase(headerName)) {
					continue;
				}

				String headerValue = request.getHeader(headerName);
				if (StringUtil.isEmpty(headerValue)) {
					return null;
				}

				return JsonUtils.readJson(headerValue);
			}
		} catch (RuntimeException e) {
			//log.debug("getVuejsCommonHeader parsing error");
		}

		return null;
	}

	/**
	 * vuejs넘기는 화면번호
	 *
	 * @param request
	 * @return
	 */
	public static String getHeaderValue(HttpServletRequest request, String headerKey) {
		if (request == null || StringUtil.isEmpty(headerKey)) {
			return "";
		}

		try {
			JsonNode vueHeader = getVuejsCommonHeader(request);
			if (vueHeader == null) {
				return "";
			}

			JsonNode headerValue = vueHeader.get(headerKey);
			return headerValue == null ? "" : headerValue.asText();
		} catch (RuntimeException e) {
			//log.debug("getHeaderValue parsing error", e);
			return "";
		}
	}

	/**
	 * vuejs넘기는 화면번호
	 *
	 * @param
	 * @return String
	 */
	public static String getScrNo() {
		HttpServletRequest request = getCurrentRequest();
		return getHeaderValue(request, Constants.SCR_NO);
	}

	/**
	 * vuejs넘기는 채널구분
	 *
	 * @param
	 * @return String
	 */
	public static String getChnlDstcd() {
		HttpServletRequest request = getCurrentRequest();
		return getHeaderValue(request, Constants.CHNL_DSTCD);
	}
}
