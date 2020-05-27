package meeting.test.controllers;

import meeting.test.entity.Meeting;
import meeting.test.entity.User;
import meeting.test.repository.MeetingRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@RestController
public class MeetingController {

    private final MeetingRepository repository;

    public MeetingController(MeetingRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/addMeeting")
    public String addMeeting(Timestamp timeBegin, Timestamp timeEnd, Set<User> users) {
        repository.save(new Meeting(timeBegin, timeEnd, users));
        return "addMeeting";
    }

    @PostMapping("/cancelMeeting")
    public String cancelMeeting(long id) {
        repository.deleteById(id);
        return "addMeeting";
    }

    @PostMapping("/addUsers")
    public String addUsers(String timeBegin, String timeEnd) {
        //repository.save(new Meeting(timeBegin, timeEnd));
        return "addMeeting";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(long id) {
        repository.deleteById(id);
        return "addMeeting";
    }

    @PostMapping("/showMeetings")
    public List<Meeting> showMeetings() {
        return repository.findAll();
    }
}
