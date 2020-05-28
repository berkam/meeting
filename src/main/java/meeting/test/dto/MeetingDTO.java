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
    private Long id;
    private Timestamp timeBegin;
    private Timestamp timeEnd;
    private Set<UserDTO> users = new HashSet<>();

    public static MeetingDTO of(Meeting meeting) {
        MeetingDTO meetingDTO = new MeetingDTO();
        meetingDTO.setId(meeting.getId());
        meetingDTO.setTimeBegin(meeting.getTimeBegin());
        meetingDTO.setTimeEnd(meeting.getTimeEnd());
        for (User user : meeting.getUsers()) {
            meetingDTO.getUsers().add(UserDTO.of(user));
        }
        return meetingDTO;
    }

    public static List<MeetingDTO> of(List<Meeting> meetingList) {
        List<MeetingDTO> meetingDTOS = new ArrayList<>();
        for (Meeting meeting : meetingList) {
            meetingDTOS.add(of(meeting));
        }
        return meetingDTOS;
    }
}
