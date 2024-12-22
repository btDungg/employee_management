package uni.hcmus.employeemanagement.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Activity extends MetaData  {

    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private int numberOfParticipants;
    private LocalDate createdDate;
    private LocalDate registrationOpenDate;
    private LocalDate registrationCloseDate;
    private String status;
    private String description;
    private String activityType;
    private Boolean isViewed;


    @OneToOne
    @JoinColumn(name="created_by", nullable = false)
    private Employee createdBy;


}
