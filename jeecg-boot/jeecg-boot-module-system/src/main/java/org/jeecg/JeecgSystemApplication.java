package org.jeecg;

import com.bstek.ureport.console.UReportServlet;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Context;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.jeecg.common.util.oConvertUtils;
import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 单体启动类（采用此类启动为单体模式）
 */
@Slf4j
/**
 * 2021/1/15 hujw
 * 我们需要的是使用Shiro做权限管理而不是SpringSecurity，
 * 但又需要Activiti来做流程业务,但是Activiti默认需要支持SpringSecurity，为了避免冲突必须剔除SpringSecurity
 * 否则会导致SpringSecurity越过Shiro验证
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
// 监听加载UReport2提供的spring配置文件ureport-console-context.xml，由于要扩展 所以引入context.xml
@ImportResource("classpath:context.xml")
public class JeecgSystemApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(JeecgSystemApplication.class);
    }

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(JeecgSystemApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = oConvertUtils.getString(env.getProperty("server.servlet.context-path"));
        log.info("\n----------------------------------------------------------\n\t" +
                "Application Jeecg-Boot is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + path + "/\n\t" +
                "External: \thttp://" + ip + ":" + port + path + "/\n\t" +
                "Swagger文档: \thttp://" + ip + ":" + port + path + "/doc.html\n\t" +
                "UReport2设计：\thttp://" + ip + ":" + port + path + "/ureport/designer\n" +
                "----------------------------------------------------------");
    }

    /**
     * ureport2 servlet, 报表工具UReport2
     */
    @Bean
    public ServletRegistrationBean buildUReportServlet(){
        return new ServletRegistrationBean(new UReportServlet(),"/ureport/*");
    }
}
