package meeting.test.model;

import lombok.extern.slf4j.Slf4j;
import meeting.test.dto.MeetingDTO;
import meeting.test.entity.Meeting;
import meeting.test.entity.User;
import meeting.test.repository.MeetingRepository;
import meeting.test.repository.UserRepository;
import meeting.test.utils.TimeChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionSystemException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static meeting.test.model.BusinessError.*;

@Slf4j
@Component
public class MeetingImpl implements MeetingInterface {
    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<?> addMeeting(long timeBegin, long timeEnd, List<String> emails) {
        if (!TimeChecker.checkCorrectTime(timeBegin, timeEnd)) {
            return ResponseEntity.badRequest().body(IncorrectTime.description());
        }

        Meeting meeting = meetingRepository.save(new Meeting(new Timestamp(timeBegin), new Timestamp(timeEnd), new ArrayList<>()));
        log.debug(meeting.toString());

        return addUsersForMeeting(meeting, emails);
    }

    private List<User> findUsersByEmail(List<String> emails) {
        return emails.stream()
                .map(e -> userRepository.findUserByEmail(e).orElseGet(() -> new User(e, new HashSet<>())))
                .collect(Collectors.toList());
    }

    private ResponseEntity<?> addUsersForMeeting(Meeting meeting, List<String> emails) {
        List<User> users = findUsersByEmail(emails);

        for (User user : users) {
            if (!TimeChecker.checkUserTime(user, meeting)) {
                return ResponseEntity.badRequest().body(UserBusy.description());
            }
        }

        meeting.getUsers().addAll(users);
        for (User user : users) {
            user.getMeetings().add(meeting);
        }

        try {
            for (User user : users) {
                userRepository.save(user);
                log.debug(String.format("User added: %s", user.toString()));
            }
            meetingRepository.save(meeting);
            return ResponseEntity.ok(MeetingDTO.of(meeting));
        } catch (TransactionSystemException exception) {
            //log.error(String.format("Not correct email: %s", user.getEmail()));
            return ResponseEntity.badRequest().body("Not correct email");
        }
    }


    @Override
    public ResponseEntity<String> cancelMeeting(long meetingId) {
        Optional<Meeting> optionalMeeting = meetingRepository.findById(meetingId);
        if (optionalMeeting.isEmpty()) {
            log.error(String.format("Error: meeting with id %s not found", meetingId));
            return ResponseEntity.badRequest().body(MeetingNotExist.description());
        }

        Meeting meeting = optionalMeeting.get();
        for (User user : meeting.getUsers()) {
            user.getMeetings().remove(meeting);
            userRepository.save(user);
        }
        meetingRepository.deleteById(meetingId);

        log.debug(String.format("Cancel meeting with id %s", meetingId));
        return ResponseEntity.ok(String.format("Cancel meeting with id %s", meetingId));
    }

    @Override
    public ResponseEntity<?> addUsers(List<String> emails, long meetingId) {
        Optional<Meeting> optionalMeeting = meetingRepository.findById(meetingId);
        if (optionalMeeting.isEmpty()) {
            log.error(String.format("Error: meeting with %s not found", meetingId));
            return ResponseEntity.badRequest().body(MeetingNotExist.description());
        }

        Meeting meeting = optionalMeeting.get();
        return addUsersForMeeting(meeting, emails);
    }

    @Override
    public ResponseEntity<?> deleteUser(long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            log.error(String.format("Error: user with %s not found", userId));
            return ResponseEntity.badRequest().build();
        }
        userRepository.deleteById(userId);
        log.debug(String.format("Delete user with id %s", userId));
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<MeetingDTO>> showMeetings() {
        List<MeetingDTO> meetingDTOS = MeetingDTO.of(meetingRepository.findAll());
        log.debug(meetingDTOS.toString());
        return ResponseEntity.ok(meetingDTOS);
    }
}
