package deeper.into.you.todo_app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.Duration;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Value("${scheduler.check-interval:PT1M}")
    private Duration checkInterval;
}
