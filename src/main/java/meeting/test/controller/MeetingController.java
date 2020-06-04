package meeting.test.controller;

import meeting.test.dto.MeetingDTO;
import meeting.test.model.MeetingInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MeetingController {
    @Autowired
    private MeetingInterface meetingInterface;

    @PostMapping("/addMeeting")
    public ResponseEntity<?> addMeeting(long timeBegin, long timeEnd, List<String> email) {
        return meetingInterface.addMeeting(timeBegin, timeEnd, email);
    }

    @PostMapping("/cancelMeeting")
    public ResponseEntity<String> cancelMeeting(long meetingId) {
        return meetingInterface.cancelMeeting(meetingId);
    }

    @PostMapping("/addUser")
    public ResponseEntity<?> addUsers(List<String> email, long meetingId) {
        return meetingInterface.addUsers(email, meetingId);
    }

    @PostMapping("/deleteUser")
    public ResponseEntity<?> deleteUser(long userId) {
        return meetingInterface.deleteUser(userId);
    }

    @PostMapping("/showMeetings")
    public ResponseEntity<List<MeetingDTO>> showMeetings() {
        return meetingInterface.showMeetings();
    }
}
