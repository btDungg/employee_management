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
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Email extends MetaData {

    @JsonProperty("email")
    private String email;
    @JsonProperty("usageType")
    private String usage_type;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "employeeId", nullable = false)
    private Employee employee;
}
