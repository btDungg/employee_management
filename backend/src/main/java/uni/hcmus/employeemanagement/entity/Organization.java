package uni.hcmus.employeemanagement.entity;


import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Organization extends MetaData {

    @OneToOne
    @JoinColumn(name="manager_id", nullable = false)
    private Employee manager_id;
    private String name;

}
