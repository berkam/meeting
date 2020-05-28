package meeting.test.controller;

import lombok.extern.slf4j.Slf4j;
import meeting.test.dto.MeetingDTO;
import meeting.test.entity.Meeting;
import meeting.test.entity.User;
import meeting.test.repository.MeetingRepository;
import meeting.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
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
    public ResponseEntity<?> addUsers(String email, long meetingId) {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        User user = optionalUser.orElseGet(() -> new User(email, new HashSet<>()));

        Optional<Meeting> optionalMeeting = meetingRepository.findById(meetingId);

        if (optionalMeeting.isEmpty()) {
            log.error("Error meeting not found");
            return ResponseEntity.badRequest().build();
        }
        Meeting meeting = optionalMeeting.get();
        meeting.getUsers().add(user);
        user.getMeetings().add(meeting);
        try {
            Long user1 = userRepository.save(user).getId();
            meetingRepository.save(meeting);
            return ResponseEntity.ok(Objects.requireNonNull(user1));
        } catch (TransactionSystemException exception) {
            return ResponseEntity.badRequest().body("Not correct email");
        }
    }

    @PostMapping("/deleteUser")
    public ResponseEntity<?> deleteUser(long userId) {
        userRepository.deleteById(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/showMeetings")
    public ResponseEntity<List<MeetingDTO>> showMeetings() {
        return ResponseEntity.ok(MeetingDTO.of(meetingRepository.findAll()));
    }
}
