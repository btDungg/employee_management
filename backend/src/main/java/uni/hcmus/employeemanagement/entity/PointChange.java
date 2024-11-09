package uni.hcmus.employeemanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    /** The amount of points changed. */
    private Long amount;

    /** The date when the change occurred. */
    @Column(name = "change_date")
    private Long changeDate;

    /** The reason for the point change. */
    private String reason;


    @Column(name="received_id")
    private Long receivedId;

    /** The employee associated with the point change. */
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
}