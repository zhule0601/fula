package com.fula.component;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * @author zl
 * 全局配置文件
 * 读取配置文件， 当指定-Dspring.config.location时，读取指定的
 * 默认读取 resource 下的 application.properties
 * @date 2020/10/11 10:51
 */
@Component
@Order(1)
public class PropertiesUtil {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
    public static final String CONF_DIR = System.getProperty("spring.config.location");
    public static final String DEFAULT_CONF_DIR = "application.properties";
    Properties properties;

    @PostConstruct
    public void init() {
        logger.info(CONF_DIR);
        try {
            if (StringUtils.isNotBlank(CONF_DIR)) {
                properties = new Properties();
                properties.load(new FileInputStream(CONF_DIR));
                logger.error("加载配置文件:{}", CONF_DIR);
            } else {
                Resource resource = new ClassPathResource(DEFAULT_CONF_DIR);
                properties = PropertiesLoaderUtils.loadProperties(resource);
                logger.error("加载配置文件:{}", DEFAULT_CONF_DIR);
            }
        } catch (Exception e) {
            logger.error("加载配置文件失败！系统退出", e);
            System.exit(1);
        }
        logger.info(properties.stringPropertyNames().toString());
    }


}
