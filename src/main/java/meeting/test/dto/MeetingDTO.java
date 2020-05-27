package meeting.test.dto;

import lombok.Data;
import meeting.test.entity.Meeting;
import meeting.test.entity.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class MeetingDTO {
    private final Set<User> users = new HashSet<>();
    private Long id;
    private Timestamp timeBegin;
    private Timestamp timeEnd;

    public List<MeetingDTO> getMeetingDTOList(List<Meeting> meetingList) {
        List<MeetingDTO> meetingDTOList = new ArrayList<>();
        for (Meeting meeting : meetingList) {

        }
        return meetingDTOList;
    }
}
