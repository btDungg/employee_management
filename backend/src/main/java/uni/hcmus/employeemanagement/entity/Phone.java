package uni.hcmus.employeemanagement.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Phone extends MetaData{


    private String phone;
    private String usage;
    @ManyToOne
    @JoinColumn(name="employee_id", nullable = false)
    private Employee employee;
}
