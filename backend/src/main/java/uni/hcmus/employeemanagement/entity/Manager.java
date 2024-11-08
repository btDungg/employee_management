package uni.hcmus.employeemanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a manager entity with basic attributes such as bonus point.
 *
 * @author <a href="mailto:21120439@student.hcmus">Bùi Minh Duy</a>
 */
//@MappedSuperclass
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Manager extends Employee{
    /** The bonus point for the manager. */
    @Column(name = "bonus_employee_point")
    private int bonusEmployeePoint;
}
