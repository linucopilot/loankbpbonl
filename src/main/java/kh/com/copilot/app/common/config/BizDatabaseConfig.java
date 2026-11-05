package kh.com.copilot.app.common.config;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Objects;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jndi.JndiObjectFactoryBean;

import kh.com.copilot.app.common.constants.Constants;
import kh.com.copilot.app.common.util.SystemUtils;

@Configuration
@MapperScan(basePackages = "kh.com.copilot.app.common.dao", sqlSessionFactoryRef = "bizSqlSessionFactory")
public class BizDatabaseConfig {

	// mybatis 설정
	@Value("${mybatis.mapper-locations:classpath*:mapper/**/*.xml}")
	private String mapperLocations;
	@Value("${mybatis.type-aliases-package:kh.com.copilot.app.**.dto*}")
	private String typeAliasesPackage;
	@Value("${mybatis.config-location:classpath:mybatis/mybatis-config.xml}")
	private String configLocation;

	@Bean(name = "bizDataSource", destroyMethod = "")
	public DataSource bizJndiDataSource(Environment env) throws IllegalArgumentException, NamingException {
		String jndiName = env.getProperty(Constants.JDBC_JNDI_LOAN);
		if (jndiName == null || jndiName.isBlank()) {
			throw new IllegalStateException("Missing required property: " + Constants.JDBC_JNDI_LOAN);
		}

		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
		bean.setJndiName(jndiName);
		bean.setResourceRef(SystemUtils.isLocal());
		bean.setProxyInterface(DataSource.class);
		bean.setLookupOnStartup(false);
		bean.afterPropertiesSet();
		return Objects.requireNonNull((DataSource) bean.getObject(), "Failed to create JNDI DataSource proxy");
	}

	@Bean(name = "bizSqlSessionFactory")
	public SqlSessionFactory bizSqlSessionFactory(@Qualifier("bizDataSource") DataSource bizDataSource,
			ApplicationContext applicationContext) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(bizDataSource);
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources(mapperLocations));
		sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
		sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource(configLocation));

		sqlSessionFactoryBean.setPlugins(new MybatisInterceptor());

		return Objects.requireNonNull(sqlSessionFactoryBean.getObject(), "Failed to create MyBatis SqlSessionFactory");
	}
}
