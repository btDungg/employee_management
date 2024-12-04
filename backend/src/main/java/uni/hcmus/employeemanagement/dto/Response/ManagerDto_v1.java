package uni.hcmus.employeemanagement.dto.Response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ManagerDto_v1 {
    private Long id;
    private String name;

    public ManagerDto_v1(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
