package meeting.test.utils;

import meeting.test.entity.Meeting;
import meeting.test.entity.User;
import org.springframework.data.util.Pair;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TimeChecker {

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

    public static boolean checkCorrectTime(long timeBegin, long timeEnd) {
        return checkBeginTimeBeforeAfterTime(timeBegin, timeEnd) &&
                checkTimeIsFromFuture(timeBegin, timeEnd) &&
                checkMeetingMoreTenMinutes(timeBegin, timeEnd) &&
                checkMeetingLessFourHours(timeBegin, timeEnd);
    }

    private static boolean checkBeginTimeBeforeAfterTime(long timeBegin, long timeEnd) {
        return timeBegin < timeEnd;
    }

    private static boolean checkTimeIsFromFuture(long timeBegin, long timeEnd) {
        return timeBegin > System.currentTimeMillis() && timeEnd > System.currentTimeMillis();
    }

    private static boolean checkMeetingMoreTenMinutes(long timeBegin, long timeEnd) {
        return timeEnd - timeBegin > 60_000; // 10m
    }

    private static boolean checkMeetingLessFourHours(long timeBegin, long timeEnd) {
        return timeEnd - timeBegin > 14_400_000; // 4h
    }
}
