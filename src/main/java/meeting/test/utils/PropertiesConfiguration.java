package meeting.test.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Configuration
@PropertySource("classpath:config.properties")
public class PropertiesConfiguration {

    @Value("${time.meeting.min.minutes:10}")
    private Integer minMeetingTime;
    @Value("${time.meeting.max.hours:4}")
    private Integer maxMeetingTime;
}
