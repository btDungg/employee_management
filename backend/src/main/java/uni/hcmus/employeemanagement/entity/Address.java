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

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address extends MetaData {

    @JsonProperty("address")
    private String addressFull;
    @JsonProperty("usageType")
    private String usage_type;
    @JsonProperty("effectiveDate")
    private Date effectiveDate;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "employeeId", nullable = false)
    private Employee employee;
}
