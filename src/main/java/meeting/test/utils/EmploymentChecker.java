package meeting.test.utils;

import meeting.test.entity.Meeting;
import meeting.test.entity.User;
import org.springframework.data.util.Pair;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class EmploymentChecker {

    public static boolean checkUserTime(User user, Meeting meeting) {
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
}
