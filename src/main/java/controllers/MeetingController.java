package controllers;

import model.MeetingRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeetingController {

    private final MeetingRepository repository;

    MeetingController(MeetingRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/addMeeting")
    public String loginSubmit(String time) {
        //repository.save(new model.Meeting());
        return "login";
    }
}
