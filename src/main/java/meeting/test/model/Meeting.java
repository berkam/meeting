package meeting.test.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Meeting extends AbstractPersistable<Long> {
    private String timeBegin;
    private String timeEnd;
}
