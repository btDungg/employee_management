package uni.hcmus.employeemanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

/**
 * Represents a change in points for an entity, including the change amount, date, and reason.
 *
 * @author <a href="mailto:21120439@student.hcmus">Bùi Minh Duy</a>
 */
//@MappedSuperclass

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class PointChange extends MetaData {

    @Column(nullable = false)
    private int amount;

    @Column(name = "change_date", nullable = false)
    private LocalDate changeDate;

    private String reason;

    @ManyToOne
    @JoinColumn(name = "received_id", nullable = false)
    private Employee received;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
}