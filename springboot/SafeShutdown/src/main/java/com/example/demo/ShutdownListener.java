package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 *
 * org.springframework.boot.web.embedded.tomcat.GracefulShutdown
 * 설정을 통해 위에 클래스 파일을 사용하면 좋지만 그렇지 못할 경우 대체하기 위하여 사용
 *
 * https://blog.marcosbarbero.com/graceful-shutdown-spring-boot-apps/
 */
//@Component
@Slf4j
public class ShutdownListener implements TomcatConnectorCustomizer, ApplicationListener<ContextClosedEvent> {

    private static final long TIMEOUT = 20;
    private Connector connector;


    @Override
    public void customize(Connector connector) {
        this.connector = connector;
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {

        this.connector.pause();
        Executor executor = this.connector.getProtocolHandler().getExecutor();

        if (!(executor instanceof ThreadPoolExecutor))  return;

        try {
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
            threadPoolExecutor.shutdown();
            if (!threadPoolExecutor.awaitTermination(TIMEOUT, TimeUnit.SECONDS)) {
                log.warn("Tomcat thread pool did not shut down gracefully within "
                        + TIMEOUT + " seconds. Proceeding with forceful shutdown");

                threadPoolExecutor.shutdownNow();

                if (!threadPoolExecutor.awaitTermination(TIMEOUT, TimeUnit.SECONDS)) {
                    log.error("Tomcat thread pool did not terminate");
                }
            }else {
                log.info("success close application");
            }
        } catch (InterruptedException ex) {

            log.error("shutdown error", ex);

            Thread.currentThread().interrupt();
        }
    }
}
