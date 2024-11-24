package uni.hcmus.employeemanagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Email extends MetaData {
    private String email;
    private String usage_type;
    @ManyToOne
    @JoinColumn(name="employee_id", nullable = false)
    private Employee employee;
}
