package uni.hcmus.employeemanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDetail extends MetaData {

    @ManyToOne
    @JoinColumn(name="activity_id", nullable = false)
    private Activity activity;

    @ManyToOne
    @JoinColumn(name="employee_id", nullable = false)
    private Employee employee;

    private String result;
    private String evidence;
    private int ranking;
    private String status;

}
