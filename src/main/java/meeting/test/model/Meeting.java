package meeting.test.model;

import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "Meetings")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class Meeting extends AbstractPersistable<Long> {
    private Timestamp timeBegin;
    private Timestamp timeEnd;
    @ManyToMany(mappedBy = "meetings")
    private Set<User> users = new HashSet<>();
}
