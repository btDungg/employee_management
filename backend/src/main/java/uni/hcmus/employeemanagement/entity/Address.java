package uni.hcmus.employeemanagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address extends MetaData {

    private String addressFull;

    private String usage_type;

    private Date effectiveDate;

    @ManyToOne
    @JoinColumn(name="employee_id", nullable = false)
    private Employee employee;
}
