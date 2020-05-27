package meeting.test.dto;

import lombok.Data;
import meeting.test.entity.User;

import java.util.List;
import java.util.Set;

@Data
public class UserDTO {
    private Long id;
    private String email;
    private Set<Long> meetingsId;

    public List<UserDTO> getUserDTOList(List<User> meetingList) {
        return null;
    }
}
