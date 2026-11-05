package kh.com.copilot.app.common.constants;

public class Constants {
	
	private Constants() {
	}

	// spring boot 기본정보
	public static final String SPRING_APPLICATION_NAME = "spring.application.name";
	public static final String SPRING_ENCRYPT_KEY = "spring.encrypt.key";

	// 사이트 전체 세션 정보
	public static final String SESSION_INFO = "SESSION_INFO";
	public static final String OPER_GROUP_CD = "KH1";

	// vuejs에서 넘기는 키
	public static final String KBPB_CLIENT_INFO = "Kbpb-Client-Info"; // vuejs common 마스터 키
	public static final String SCR_NO = "screen-No"; // 페이지번호
	public static final String CHNL_DSTCD = "chnl-Dstcd"; // 채널구분

	// DB정보
	public static final String JDBC_JNDI_LOAN = "dbserver.jndi.loan";
	public static final String JDBC_JNDI_GDTI = "dbserver.jndi.gdti";
	public static final String JDBC_JNDI_INFO = "dbserver.jndi.info";
	public static final String JDBC_JNDI_FCUB = "dbserver.jndi.fcub";
	public static final String JDBC_DATABASE_SCHEMA = "dbserver.jdbc.schema";

	// OZ서버정보
	public static final String OZ_SERVER_URL = "oz.serverURL";

	// SonarLint 조치(MCI)
	public static final String USER_AGENT = "User-Agent";
	public static final String DELFINO = "delfino";
	public static final String DEVICE = "Device";
	public static final String MSGBODY = "MsgBody";
	public static final String DEFAULT_TM_PATTERN = "dd/MM/yyyy HH:mm:ss";

	// 기본 에러 메시지 sonarqube 이슈 로 toUpperCase 적용.
	public static final String DEFUALT_ERROR_CODE = "CSMA00001"; //
	public static final String DEFUALT_TREAT_CODE = "CSMT00001"; //
	public static final String SUCCESS_CD = "0000";
	public static final String LANG_CODE_KOR = "KOR"; // 한국어
	public static final String LANG_CODE_ENG = "ENG"; // 영어
	public static final String LANG_CODE_KHR = "KHR"; // 크메르
	public static final String BIZ_INS_DEFAULT_LANG_CODE = LANG_CODE_KOR; // GssBizInsCodeUtils 업무인스턴스 코드 기본값

	// WSO2 관련 변수
	public static final String CARD = "card";
	public static final String COLL = "coll";
	public static final String LOAN = "loan";
	public static final String LOAN_AUTH = "loan_auth";
	public static final String MOD = "mod";
	public static final String INFO = "info";
	public static final String DMB = "dmb";
	public static final String CARD_BATH = "card_batch";
	public static final String CARD_ORDS = "card_ords";
	public static final String COLL_ORDS = "coll_ords";
	public static final String LOAN_ORDS = "loan_ords";
	public static final String MOD_ORDS = "mod_ords";
	public static final String INFO_ORDS = "info_ords";

	public static final String GDT_ORDS = "otc_apikey";

	public static final String INNER = "01";
	public static final String FLEXCUBE = "02";
	public static final String MOBILE = "03";
	public static final String EXTERNAL = "04";
	public static final String DMZ = "05";
	public static final String GDT_TEST = "06";

	public class EXCEL_TYPE {
		public static final String FILE_NAME = "fileName";
		public static final String HEAD = "head";
		public static final String HEAD_TOP_ALL = "head_top_all";
		public static final String HEAD_BOTTOM_ALL = "head_bottom_all";
		public static final String HEAD_TOP = "head_top";
		public static final String HEAD_BOTTOM = "head_bottom";
		public static final String BODY = "body";
		public static final String BODYS = "bodys";
		public static final String NAMES = "names";
		public static final String REPORT_SET = "report_set";
		public static final String ENCRYPT = "encrypt";

		public class EXCEL_EXTENSION {
			public static final String XLS = "xls";
			public static final String XLSX = "xlsx";
		}

	}
}
