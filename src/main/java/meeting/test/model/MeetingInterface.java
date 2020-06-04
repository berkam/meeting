package meeting.test.model;

import meeting.test.dto.MeetingDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MeetingInterface {

    ResponseEntity<?> addMeeting(long timeBegin, long timeEnd, List<String> email);

    ResponseEntity<String> cancelMeeting(long meetingId);

    ResponseEntity<?> addUsers(List<String> email, long meetingId);

    ResponseEntity<?> deleteUser(long userId);

    ResponseEntity<List<MeetingDTO>> showMeetings();
}
