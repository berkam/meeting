package meeting.test.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "Meetings")
@NoArgsConstructor
@ToString
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Timestamp timeBegin;
    private Timestamp timeEnd;
    @ManyToMany(mappedBy = "meetings")
    private Set<User> users = new HashSet<>();

    public Meeting(Timestamp timeBegin, Timestamp timeEnd, Set<User> users) {
        this.timeBegin = timeBegin;
        this.timeEnd = timeEnd;
        this.users = users;
    }
}
