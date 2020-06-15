package meeting.test.utils;

import lombok.extern.slf4j.Slf4j;
import meeting.test.entity.Meeting;
import meeting.test.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class TimeChecker {

    @Autowired
    public PropertiesConfiguration propertiesConfiguration;

    public boolean checkUserTime(User user, Meeting meeting) {
        List<Pair<Timestamp, Timestamp>> busyTime = new ArrayList<>();

        for (Meeting userMeeting : user.getMeetings()) {
            busyTime.add(Pair.of(userMeeting.getTimeBegin(), userMeeting.getTimeEnd()));
        }

        boolean userIsFree = true;
        for (Pair<Timestamp, Timestamp> timesPair : busyTime) {
            if (meeting.getTimeBegin().compareTo(timesPair.getFirst()) >= 0 &&
                    meeting.getTimeBegin().compareTo(timesPair.getSecond()) <= 0 ||
                    meeting.getTimeEnd().compareTo(timesPair.getFirst()) >= 0 &&
                            meeting.getTimeEnd().compareTo(timesPair.getSecond()) <= 0)
                userIsFree = false;
        }
        return userIsFree;
    }

    public boolean checkCorrectTime(long timeBegin, long timeEnd) {
        return checkBeginTimeBeforeAfterTime(timeBegin, timeEnd) &&
                checkTimeIsFromFuture(timeBegin, timeEnd) &&
                checkMeetingMoreThanMin(timeBegin, timeEnd) &&
                checkMeetingLessThanMax(timeBegin, timeEnd);
    }

    private boolean checkBeginTimeBeforeAfterTime(long timeBegin, long timeEnd) {
        log.debug("Method checkBeginTimeBeforeAfterTime, timeBegin {}, timeEnd {}", timeBegin, timeEnd);
        log.debug("result {}", timeBegin < timeEnd);
        return timeBegin < timeEnd;
    }

    private boolean checkTimeIsFromFuture(long timeBegin, long timeEnd) {
        log.debug("Method checkTimeIsFromFuture, timeBegin {}, timeEnd {}, currentTimeMillis {}", timeBegin, timeEnd, System.currentTimeMillis());
        log.debug("result {}", (timeBegin > System.currentTimeMillis()) && (timeEnd > System.currentTimeMillis()));
        return timeBegin > System.currentTimeMillis() && timeEnd > System.currentTimeMillis();
    }

    private boolean checkMeetingMoreThanMin(long timeBegin, long timeEnd) {
        log.debug("Method checkMeetingMoreThanMin, timeBegin {}, timeEnd {}, value {}", timeBegin, timeEnd, propertiesConfiguration.getMinMeetingTime() * 1000 * 60);
        log.debug("result {}", (timeEnd - timeBegin) > (propertiesConfiguration.getMinMeetingTime() * 1000 * 60));
        return timeEnd - timeBegin > propertiesConfiguration.getMinMeetingTime() * 1000 * 60;
    }

    private boolean checkMeetingLessThanMax(long timeBegin, long timeEnd) {
        log.debug("Method checkMeetingLessThanMax, timeBegin {}, timeEnd {}, value {}", timeBegin, timeEnd, propertiesConfiguration.getMinMeetingTime() * 1000 * 60 * 60);
        log.debug("result {}", timeEnd - timeBegin < propertiesConfiguration.getMaxMeetingTime() * 1000 * 60 * 60);
        return timeEnd - timeBegin < propertiesConfiguration.getMaxMeetingTime() * 1000 * 60 * 60;
    }
}
