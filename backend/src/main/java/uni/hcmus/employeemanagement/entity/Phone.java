package uni.hcmus.employeemanagement.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Phone extends MetaData {
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("usageType")
    private String usage_type;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "employeeId", nullable = false)
    private Employee employee;
}
