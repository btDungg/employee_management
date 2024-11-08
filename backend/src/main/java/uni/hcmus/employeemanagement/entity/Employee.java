package uni.hcmus.employeemanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Represents an employee entity with basic attributes such as name, point, and manager ID.
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
public class Employee extends MetaData {

    /** The name of the employee. */
    @Column(name = "employee_name")
    private String name;

    /** The point value associated with the employee. */
    @Column(name = "point")
    private int point;

    /** The ID of the manager for the employee. */
    @Column(name = "manager_id")
    private long managerId;

    /** The type of employee. */
    @Column(name = "type")
    private String type;
}