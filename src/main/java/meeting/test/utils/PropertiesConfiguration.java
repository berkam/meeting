package meeting.test.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@lombok.Value
@Configuration
@PropertySource("classpath:config.properties")
public class PropertiesConfiguration {

    @Value("${time.meeting.min.minutes:10}")
    Integer minMeetingTime;
    @Value("${time.meeting.max.hours:4}")
    Integer maxMeetingTime;
}
