package meeting.test.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Meetings")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Meeting extends AbstractPersistable<Long> {
    private Timestamp timeBegin;
    private Timestamp timeEnd;
    @ManyToMany(mappedBy = "meetings")
    private Set<User> users = new HashSet<>();
}
