package meeting.test.controllers;

import meeting.test.model.Meeting;
import meeting.test.model.MeetingRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeetingController {

    private final MeetingRepository repository;

    public MeetingController(MeetingRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/addMeeting")
    public String addMeeting(@ModelAttribute Meeting meeting, Model model) {
        repository.save(meeting);
        return "addMeeting";
    }
}
