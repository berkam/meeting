package meeting.test.dto;

import lombok.Data;
import meeting.test.entity.User;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class UserDTO {
    private Long id;
    private String email;
    private Set<Long> meetingsId;

    public static UserDTO of(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setMeetingsId(user.getMeetings().stream().map(AbstractPersistable::getId).collect(Collectors.toSet()));
        return userDTO;
    }

    public static List<UserDTO> of(List<User> userList) {
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : userList) {
            userDTOS.add(of(user));
        }
        return userDTOS;
    }
}
