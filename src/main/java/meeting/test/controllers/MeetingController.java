package meeting.test.controllers;

import lombok.extern.slf4j.Slf4j;
import meeting.test.entity.Meeting;
import meeting.test.entity.User;
import meeting.test.repository.MeetingRepository;
import meeting.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class MeetingController {
    @Autowired
    private final MeetingRepository meetingRepository;
    @Autowired
    private final UserRepository userRepository;

    public MeetingController(MeetingRepository repository, UserRepository userRepository) {
        this.meetingRepository = repository;
        this.userRepository = userRepository;
    }


    @PostMapping("/addMeeting")
    public String addMeeting(Timestamp timeBegin, Timestamp timeEnd) {
        Meeting meeting = meetingRepository.save(new Meeting(timeBegin, timeEnd, new HashSet<>()));
        return String.format("addMeeting with id %s", meeting.getId());
    }

    @PostMapping("/cancelMeeting")
    public String cancelMeeting(long id) {
        meetingRepository.deleteById(id);
        return String.format("cancelMeeting with id %s", id);
    }

    @PostMapping("/addUsers")
    public String addUsers(String email, long meetingId) {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        User user = optionalUser.orElseGet(() -> new User(email, new HashSet<>()));

        Optional<Meeting> meeting = meetingRepository.findById(meetingId);

        if (meeting.isEmpty()) {
            log.error("Error meeting not found");
            return "Error meeting not found";
        }
        user.getMeetings().add(meeting.get());

        userRepository.save(user);
        return "user add";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(long id) {
        userRepository.deleteById(id);
        return "deleteUser";
    }

    @PostMapping("/showMeetings")
    public List<Meeting> showMeetings() {
        return meetingRepository.findAll();
    }
}
