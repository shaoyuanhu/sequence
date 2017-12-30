package com.shaoyuanhu.service.launcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Description: 服务启动类
 * @Author: ShaoYuanHu
 * @Date: 2017/12/3
 */
public class Provider {
    private static final Logger logger = LoggerFactory.getLogger(Provider.class);
    private static volatile boolean running = true;
    private static ApplicationContext ctx;

    public static void main(String[] args){
        try{
            ctx = new ClassPathXmlApplicationContext(
                    new String[]{
                            "classpath*:spring-config.xml",
                            "classpath*:spring-config-dao.xml",
//                            "classpath*:spring-config-dubbo.xml",
                            "classpath*:spring-jedis.xml"
                    }
            );
            logger.info("code-sequence-soa server started.");
            System.out.println("sequence服务已启动！！");
        }catch (Exception e){
            running  = false;
            e.printStackTrace();
        }

        synchronized (Provider.class) {
            while (running) {
                try {
                    Provider.class.wait();
                } catch (Throwable e) {
                }
            }
        }
    }
}
