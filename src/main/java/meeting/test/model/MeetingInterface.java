package meeting.test.model;

import meeting.test.dto.MeetingDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MeetingInterface {

    ResponseEntity<?> addMeeting(long timeBegin, long timeEnd);

    ResponseEntity<String> cancelMeeting(long meetingId);

    ResponseEntity<?> addUsers(String email, long meetingId);

    ResponseEntity<?> deleteUser(long userId);

    ResponseEntity<List<MeetingDTO>> showMeetings();
}
