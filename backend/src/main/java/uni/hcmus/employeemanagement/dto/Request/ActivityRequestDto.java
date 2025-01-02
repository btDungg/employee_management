package uni.hcmus.employeemanagement.dto.Request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActivityRequestDto {

    @NotNull(message = "Activity name cannot be null.")
    @Size(min = 3, max = 255, message = "Activity name must be between 3 and 255 characters.")
    private String name;

    @NotNull(message = "Activity type cannot be null.")
    @Pattern(regexp = "^(Thể thao|Hội thảo|Khoá học)$", message = "Activity type must be one of: Thể thao, Hội thảo, Khoá học.")
    private String type;
    @NotNull(message = "Start date cannot be null.")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null.")
    private LocalDate endDate;

    @NotNull(message = "Number of participants cannot be null.")
    private int numberOfParticipants;

    @NotNull(message = "Registration open date cannot be null.")
    private LocalDate registrationOpenDate;

    @NotNull(message = "Registration close date cannot be null.")
    private LocalDate registrationCloseDate;

    @NotNull(message = "Status cannot be null.")
    private String status;


    private String description;

    private Boolean isViewed; // Optional or can be a boolean (true/false)

}