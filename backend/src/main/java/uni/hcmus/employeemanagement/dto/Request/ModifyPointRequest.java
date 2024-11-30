package uni.hcmus.employeemanagement.dto.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModifyPointRequest {
	private long receive_id;
    private int amount;
    private String modifyType;
    private String reason;
}
