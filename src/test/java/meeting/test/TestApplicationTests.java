package meeting.test;

import meeting.test.entity.Meeting;
import meeting.test.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TestApplicationTests {

    @Test
    void someTest() {
        String[] userData = {"berkam15+1@gmail.com", "berkam15+2@gmail.com"};

        long millis = System.currentTimeMillis();
        Timestamp ts = new Timestamp(millis);

        Set<User> users = new HashSet<>();

        Meeting meeting = new Meeting(ts, ts, users);
        assertEquals(0, meeting.getUsers().size());

        for (String userDatum : userData) {
            User user = new User(userDatum, Collections.singleton(meeting));
            meeting.getUsers().add(user);
        }
        assertEquals(2, meeting.getUsers().size());
    }
}
