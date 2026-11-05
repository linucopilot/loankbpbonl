package kh.com.copilot.app.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import io.micrometer.common.util.StringUtils;
import jakarta.annotation.PostConstruct;
import kh.com.copilot.app.common.constants.Constants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SystemUtils {
	
	@Autowired
	private Environment env;

	// spring.profiles.active
	private static String springProfilesActive = System.getProperty("spring.profiles.active");

	// ASIS참고(FrameworkMode가 Local이 아닐 경우 -DID=XXXX VM argument, Local일 경우
	// ibf/ibf.properties의 infra.framework.id 값이다.)
	private static String instanceID = System.getProperty("weblogic.Name") != null ? System.getProperty("weblogic.Name")
			: "weblogic";

	// ASIS참고(해당 인스턴스의 호스트명을 반환한다.)
	private static String hostName = "";

	private static String systemCode = "000";
	private static String serviceMode = "";
	private static String serviceUrl = "";
	private static int sequence = 0;
	private static String springApplicationName = "";
	private static String springEncryptKey = "";
	private static String jdbcDatabaSchema = "";
	private static String ldapUrl = "";
	private static String adUserId = "";
	private static String adPassword = "";
	private static String baseDn = "";

	@PostConstruct
	public void init() {
		try {
			

			hostName = env.getProperty(Constants.SPRING_APPLICATION_NAME).indexOf('-') > -1
					? env.getProperty(Constants.SPRING_APPLICATION_NAME).replace("-", "")
					: env.getProperty(Constants.SPRING_APPLICATION_NAME);
			serviceMode = "L";

			serviceUrl = env.getProperty("rest.client.servicePrefix");

			springApplicationName = env.getProperty(Constants.SPRING_APPLICATION_NAME);

			springEncryptKey = env.getProperty(Constants.SPRING_ENCRYPT_KEY);

			jdbcDatabaSchema = env.getProperty(Constants.JDBC_DATABASE_SCHEMA);

			ldapUrl = env.getProperty("adserver.ldap_url");

			adUserId = env.getProperty("adserver.ad_userId");

			adPassword = env.getProperty("adserver.ad_password");

			baseDn = env.getProperty("adserver.baseDn");

			if (isLocal())
				serviceMode = "L";
			if (isDev())
				serviceMode = "D";
			if (isStg())
				serviceMode = "S";
			if (isPrd())
				serviceMode = "P";
			if (isDr())
				serviceMode = "R";

			//log.info("SystemUtils [E] ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		} catch (Exception e) {
			//log.error(e.toString(), e);
		}
	}

	public static String getInstanceID() {
		return instanceID;
	}

	public static String getHostName() {
		return hostName;
	}

	public static String getServiceMode() {
		return serviceMode;
	}

	public static String getServiceUrl() {
		return serviceUrl;
	}

	public static String getSystemCode() {
		return systemCode;
	}

	public static String getJdbcDatabaSchema() {
		return jdbcDatabaSchema;
	}

	public static String getEncryptKey() {
		return springEncryptKey;
	}

	public static String getLdapUrl() {
		return ldapUrl;
	}

	public static String getAdUserId() {
		return adUserId;
	}

	public static String getAdPassword() {
		return adPassword;
	}

	public static String getBaseDn() {
		return baseDn;
	}

	/**
	 * 변수의 SEQUENCE값을 반환
	 * 
	 * @return
	 */
	public synchronized static String getNextSequence() {
		if (sequence >= 1048575) {
			sequence = 0;
		}
		return systemCode + StringUtil.fillZero(Integer.toHexString(sequence++), 5);
	}

	/**
	 * 현재 환경이 로컬인지 여부확인
	 * 
	 * @return
	 */
	public static boolean isLocal() {
		boolean rtn = false;

		/* 로컬인 경우 profile 설정이 되지 않은 경우가 많아서 일단 blank인경우는 local로..) */
		if (StringUtils.isBlank(springProfilesActive)) {
			rtn = true;
		}

		if (StringUtils.isNotBlank(springProfilesActive) && "local".equals(springProfilesActive)) {
			rtn = true;
		}
		return rtn;
	}

	/**
	 * 현재 환경이 개발인지 여부확인
	 * 
	 * @return
	 */
	public static boolean isDev() {
		boolean rtn = false;
		if (StringUtils.isNotBlank(springProfilesActive) && "dev".equals(springProfilesActive)) {
			rtn = true;
		}
		return rtn;
	}

	/**
	 * 현재 환경이 스테이징인지 여부확인
	 * 
	 * @return
	 */
	public static boolean isStg() {
		boolean rtn = false;
		if (StringUtils.isNotBlank(springProfilesActive) && "stg".equals(springProfilesActive)) {
			rtn = true;
		}
		return rtn;
	}

	/**
	 * 현재 환경이 운영인지 여부확인
	 * 
	 * @return
	 */
	public static boolean isPrd() {
		boolean rtn = false;
		if (StringUtils.isNotBlank(springProfilesActive) && "prd".equals(springProfilesActive)) {
			rtn = true;
		}
		return rtn;
	}

	/**
	 * 현재 환경이 운영인지 여부확인
	 * 
	 * @return
	 */
	public static boolean isDr() {
		boolean rtn = false;
		if (StringUtils.isNotBlank(springProfilesActive) && "dr".equals(springProfilesActive)) {
			rtn = true;
		}
		return rtn;
	}

	public static String getSpringApplicationName() {
		return springApplicationName;
	}

	public static void setSpringApplicationName(String springApplicationName) {
		SystemUtils.springApplicationName = springApplicationName;
	}
}
