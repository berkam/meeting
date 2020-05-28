package meeting.test.controller;

import lombok.extern.slf4j.Slf4j;
import meeting.test.dto.MeetingDTO;
import meeting.test.entity.Meeting;
import meeting.test.entity.User;
import meeting.test.repository.MeetingRepository;
import meeting.test.repository.UserRepository;
import meeting.test.utils.EmploymentChecker;
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
    public ResponseEntity<?> addMeeting(long timeBegin, long timeEnd) {
        if (timeBegin < timeEnd) {
            Meeting meeting = meetingRepository.save(new Meeting(new Timestamp(timeBegin), new Timestamp(timeEnd), new HashSet<>()));
            log.debug(meeting.toString());
            return ResponseEntity.ok(Objects.requireNonNull(meeting.getId()));
        } else return ResponseEntity.badRequest().body("Not correct time");
    }

    @PostMapping("/cancelMeeting")
    public ResponseEntity<String> cancelMeeting(long meetingId) {
        meetingRepository.deleteById(meetingId);
        log.debug(String.format("Cancel meeting with id %s", meetingId));
        return ResponseEntity.ok(String.format("Cancel meeting with id %s", meetingId));
    }

    @PostMapping("/addUser")
    public ResponseEntity<?> addUsers(String email, long meetingId) {
        Optional<Meeting> optionalMeeting = meetingRepository.findById(meetingId);
        if (optionalMeeting.isEmpty()) {
            log.error(String.format("Error: meeting with %s not found", meetingId));
            return ResponseEntity.badRequest().build();
        }

        User user = userRepository.findUserByEmail(email).orElseGet(() -> new User(email, new HashSet<>()));
        Meeting meeting = optionalMeeting.get();

        if (!EmploymentChecker.checkUserTime(user, meeting)) {
            return ResponseEntity.badRequest().body("This user is busy for this time");
        }

        meeting.getUsers().add(user);
        user.getMeetings().add(meeting);

        try {
            Long userId = userRepository.save(user).getId();
            meetingRepository.save(meeting);
            log.debug(String.format("User added: %s", user.toString()));
            return ResponseEntity.ok(Objects.requireNonNull(userId));
        } catch (TransactionSystemException exception) {
            log.error(String.format("Not correct email: %s", user.getEmail()));
            return ResponseEntity.badRequest().body("Not correct email");
        }
    }

    @PostMapping("/deleteUser")
    public ResponseEntity<?> deleteUser(long userId) {
        userRepository.deleteById(userId);
        log.debug(String.format("Delete user with id %s", userId));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/showMeetings")
    public ResponseEntity<List<MeetingDTO>> showMeetings() {
        List<MeetingDTO> meetingDTOS = MeetingDTO.of(meetingRepository.findAll());
        log.debug(meetingDTOS.toString());
        return ResponseEntity.ok(meetingDTOS);
    }
}
