package meeting.test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "Users")
public class User extends AbstractPersistable<Long> {
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "User_meeting",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "meeting_id")}
    )
    Set<Meeting> meetings = new HashSet<>();
    @Email
    private String email;
}
