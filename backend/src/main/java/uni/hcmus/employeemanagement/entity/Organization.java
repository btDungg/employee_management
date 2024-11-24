package uni.hcmus.employeemanagement.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Organization extends MetaData {

    @OneToOne
    @JoinColumn(name="manager_id", nullable = false)
    private Employee manager_id;
    private String name;

}
