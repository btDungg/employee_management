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
public class EmergencyContact extends MetaData {
    @JsonProperty("priority")
    private int priority;
    @JsonProperty("name")
    private String name;
    @JsonProperty("relationship")
    private String relationship;
    @JsonProperty("preferredLanguage")
    private String preferredLanguage;
    @JsonProperty("primaryContactInformation")
    private String primaryContactInformation;
    @JsonProperty("alternativeContactInformation")
    private String alternativeContactInformation;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
}
