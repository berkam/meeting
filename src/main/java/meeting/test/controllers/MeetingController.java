package meeting.test.controllers;

import meeting.test.model.Meeting;
import meeting.test.model.MeetingRepository;
import meeting.test.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Set;

@RestController
public class MeetingController {

    private final MeetingRepository repository;

    public MeetingController(MeetingRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/addMeeting")
    public String addMeeting(Timestamp timeBegin, Timestamp timeEnd, Set<User> users) {
        repository.save(new Meeting(timeBegin, timeEnd, users));
        return "addMeeting";
    }

    @GetMapping("/cancelMeeting")
    public String cancelMeeting(long id) {
        repository.deleteById(id);
        return "addMeeting";
    }

    @GetMapping("/addUsers")
    public String addUsers(String timeBegin, String timeEnd) {
        //repository.save(new Meeting(timeBegin, timeEnd));
        return "addMeeting";
    }

    @GetMapping("/deleteUser")
    public String deleteUser(long id) {
        repository.deleteById(id);
        return "addMeeting";
    }

    @GetMapping("/showMeetings")
    public String showMeetings() {
        return repository.findAll().toString();
    }
}
