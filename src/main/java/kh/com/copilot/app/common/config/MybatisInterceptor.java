package kh.com.copilot.app.common.config;

import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import kh.com.copilot.app.common.constants.Constants;
import kh.com.copilot.app.common.util.HttpUtils;
import kh.com.copilot.app.common.util.StringUtil;
import kh.com.copilot.app.common.util.SystemUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Intercepts({
		@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
				RowBounds.class, ResultHandler.class }),
		@Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class MybatisInterceptor implements Interceptor {
	@Override
	public Object intercept(Invocation invocation) throws Throwable {

		processIntercept(invocation);
		return invocation.proceed();
	}

	private void processIntercept(Invocation invocation) {
		if (invocation == null || invocation.getArgs() == null || invocation.getArgs().length < 2) {
			throw new IllegalArgumentException("MyBatis invocation must contain a mapped statement and parameters");
		}
		String method = invocation.getMethod().getName();
		Object[] args = invocation.getArgs();
		Object param = args[1];
		if (!(args[0] instanceof MappedStatement mappedStatement)) {
			throw new IllegalArgumentException("MyBatis invocation must start with a mapped statement");
		}
		String queryId = mappedStatement.getId();

		//log.debug("Intercepting MyBatis {} statement {}", method, queryId);

		if (param instanceof Map<?, ?> parameters) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) parameters;

			if (isMissing(map.get("operGroupCd"))) {
				map.put("operGroupCd", Constants.OPER_GROUP_CD);
			}
			var request = HttpUtils.getCurrentRequest();
			if (request != null) {
				if (isMissing(map.get("sysLastUno"))) {
					map.put("sysLastUno", request.getAttribute("sysLastUno"));
				}
				if (isMissing(map.get("langDstcd"))) {
					map.put("langDstcd", request.getAttribute("langDstcd"));
				}
				if (isMissing(map.get("UUID"))) {
					map.put("UUID", request.getAttribute("UUID"));
				}
			}
			if (isMissing(map.get("schema"))) {
				map.put("schema", SystemUtils.getJdbcDatabaSchema());
			}
		}
	}

	private boolean isMissing(Object value) {
		return value == null || value instanceof String text && StringUtil.isEmpty(text);
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		// Do nothing
	}
}
