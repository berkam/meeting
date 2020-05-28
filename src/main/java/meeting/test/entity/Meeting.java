package meeting.test.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
    private List<User> users = new ArrayList<>();

    public Meeting(Timestamp timeBegin, Timestamp timeEnd, List<User> users) {
        this.timeBegin = timeBegin;
        this.timeEnd = timeEnd;
        this.users = users;
    }
}
