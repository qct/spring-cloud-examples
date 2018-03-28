package alex.eureka.service;

import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * <p>Created by qct on 2017/2/24.
 */
@Component
public class TestListener implements ApplicationListener<ApplicationEvent> {

    private static final Logger logger = LoggerFactory.getLogger(TestListener.class);

    private AtomicInteger count = new AtomicInteger(0);

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        logger.debug("******************************counter: {}, event: {}", count.incrementAndGet(), event);
    }
}
