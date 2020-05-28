package meeting.test.dto;

import lombok.Data;
import lombok.ToString;
import meeting.test.entity.Meeting;
import meeting.test.entity.User;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@ToString
public class UserDTO {
    private Long id;
    private String email;
    private Set<Long> meetingsId;

    public static UserDTO of(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setMeetingsId(user.getMeetings().stream().map(Meeting::getId).collect(Collectors.toSet()));
        return userDTO;
    }
}
