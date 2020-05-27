package meeting.test.controller;

import lombok.extern.slf4j.Slf4j;
import meeting.test.entity.Meeting;
import meeting.test.entity.User;
import meeting.test.repository.MeetingRepository;
import meeting.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RestController
public class MeetingController {
    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/addMeeting")
    public ResponseEntity<Long> addMeeting(Long timeBegin, Long timeEnd) {
        Meeting meeting = meetingRepository.save(new Meeting(new Timestamp(timeBegin), new Timestamp(timeEnd), new HashSet<>()));
        return ResponseEntity.ok(Objects.requireNonNull(meeting.getId()));
    }

    @PostMapping("/cancelMeeting")
    public ResponseEntity<String> cancelMeeting(long meetingId) {
        meetingRepository.deleteById(meetingId);
        return ResponseEntity.ok(String.format("cancelMeeting with id %s", meetingId));
    }

    @PostMapping("/addUsers")
    public ResponseEntity<Long> addUsers(String email, long meetingId) {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        User user = optionalUser.orElseGet(() -> new User(email, new HashSet<>()));

        Optional<Meeting> optionalMeeting = meetingRepository.findById(meetingId);

        if (optionalMeeting.isEmpty()) {
            log.error("Error meeting not found");
            return ResponseEntity.badRequest().build();
        }
        Meeting meeting = optionalMeeting.get();
        meeting.getUsers().add(user);
        meetingRepository.save(meeting);

        user.getMeetings().add(meeting);
        return ResponseEntity.ok(Objects.requireNonNull(userRepository.save(user).getId()));
    }

    @PostMapping("/deleteUser")
    public ResponseEntity<?> deleteUser(long userId) {
        userRepository.deleteById(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/showMeetings")
    public List<Meeting> showMeetings() {
        return meetingRepository.findAll();
    }
}
