package meeting.test.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Users")
public class User extends AbstractPersistable<Long> {
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "User_meeting",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "meeting_id")}
    )
    Set<Meeting> meetings = new HashSet<>();
    private String email;
}
