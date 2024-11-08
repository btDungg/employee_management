package uni.hcmus.employeemanagement.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents an HR entity with basic attributes such as name, point, and manager ID.
 *
 * @author <a href="mailto:21120439@student.hcmus">Bùi Minh Duy</a>
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
//@NoArgsConstructor
public class HR extends Employee{

}
