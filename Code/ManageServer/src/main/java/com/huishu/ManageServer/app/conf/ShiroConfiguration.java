package com.huishu.ManageServer.app.conf;


import com.huishu.ManageServer.security.*;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro的配置
 * 
 * @author yindq
 * @date 2017年9月28日
 */
@Configuration
public class ShiroConfiguration {
	private static Logger LOGGER = LoggerFactory.getLogger(ShiroConfiguration.class);
	// 拦截器，必须保证有序
	private final static Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
	private final static Map<String, Filter> filters = new LinkedHashMap<String, Filter>();

	/**
	 * ShiroFilter
	 * 
	 * @return
	 */
	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean getShiroFilterFactoryBean() {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

		/*
		 * 配置访问权限 anon：表示全部放权的资源路径，authc：表示需要认证才可以访问
		 */

		filterChainDefinitionMap.put("/**/*.json", "authc");
		filterChainDefinitionMap.put("/**/*.html", "authc");

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

		filters.put("ajaxSessionFilter", getMyUserFilter());
		filters.put("authc", getMyFormAuthenticationFilter());
		shiroFilterFactoryBean.setSecurityManager(getDefaultWebSecurityManager());
		shiroFilterFactoryBean.setLoginUrl("/apis/login.do");
		shiroFilterFactoryBean.setSuccessUrl("/apis/user/userPark.html");
		shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");
		shiroFilterFactoryBean.setFilters(filters);
		return shiroFilterFactoryBean;
	}

	/**
	 * 配置核心安全事务管理器
	 * 
	 * @return
	 */
	@Bean(name = "securityManager")
	public DefaultWebSecurityManager getDefaultWebSecurityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setSessionManager(getDefaultWebSessionManager());
		securityManager.setRealm(getShiroDbRealm());
		LOGGER.info("===============shiro已经加载================");
		// 用户授权/认证信息Cache
		securityManager.setCacheManager(getEhCacheManager());
		return securityManager;
	}

	/**
	 * 配置自定义的权限登录器 (这个需要自己写，账号密码校验；权限等)
	 * 
	 * @return
	 */
	@Bean(name = "shiroDbRealm")
	public ShiroDbRealm getShiroDbRealm() {
		ShiroDbRealm shiroRealm = new ShiroDbRealm();
		// 配置自定义的密码比较器
		shiroRealm.setCredentialsMatcher(getCustomCredentialsMatcher());
		// 启用身份验证缓存，即缓存AuthenticationInfo信息，默认false
		shiroRealm.setAuthenticationCachingEnabled(true);
		shiroRealm.setCacheManager(getEhCacheManager());
		shiroRealm.setCredentialsMatcher(getCustomCredentialsMatcher());
		return shiroRealm;
	}

	/**
	 * 配置EhCache 缓存
	 * 
	 * @return
	 */
	@Bean
	public EhCacheManager getEhCacheManager() {
		EhCacheManager em = new EhCacheManager();
		em.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
		return em;
	}

	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	/**
	 * 处理session超时
	 * 
	 * @return
	 */
	@Bean(name = "myUserFilter")
	public MyUserFilter getMyUserFilter() {
		return new MyUserFilter();
	}

	/**
	 * 开启shiro aop注解支持.
	 * 
	 * @return
	 */
	@Bean(name = "authorizationAttributeSourceAdvisor")
	public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor() {
		AuthorizationAttributeSourceAdvisor auth = new AuthorizationAttributeSourceAdvisor();
		auth.setSecurityManager(getDefaultWebSecurityManager());
		return auth;
	}

	/**
	 * FormAuthenticationFilter
	 * 
	 * @return
	 */
	@Bean(name = "loginFormAuthenticationFilter")
	public MyFormAuthenticationFilter getMyFormAuthenticationFilter() {
		return new MyFormAuthenticationFilter();
	}

	/**
	 * DefaultWebSessionManager
	 * 
	 * @return
	 */
	@Bean(name = "defaultWebSessionManager")
	public DefaultWebSessionManager getDefaultWebSessionManager() {
		MySessionManager manager = new MySessionManager();
		// 会话超时时间，单位：毫秒
		manager.setGlobalSessionTimeout(7200000);
		// 定时清理失效会话, 清理用户直接关闭浏览器造成的孤立会话
		manager.setSessionValidationInterval(600000);
		manager.setSessionValidationSchedulerEnabled(true);
		manager.setSessionDAO(getEnterpriseCacheSessionDAO());
		return manager;
	}

	/**
	 * 自定义校验密码
	 * 
	 * @return
	 */
	@Bean(name = "customCredentialsMatcher")
	public CustomCredentialsMatcher getCustomCredentialsMatcher() {
		CustomCredentialsMatcher matcher = new CustomCredentialsMatcher(getEhCacheManager());
		return matcher;
	}

	/**
	 * SessionDAO
	 * 
	 * @return
	 */
	@Bean(name = "enterpriseCacheSessionDAO")
	public EnterpriseCacheSessionDAO getEnterpriseCacheSessionDAO() {
		EnterpriseCacheSessionDAO dao = new EnterpriseCacheSessionDAO();
		dao.setCacheManager(getEhCacheManager());
		return dao;
	}
}