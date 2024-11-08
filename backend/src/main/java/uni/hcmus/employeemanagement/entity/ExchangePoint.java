package uni.hcmus.employeemanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents an exchange point entity with basic attributes such as reserved money.
 *
 * @author <a href="mailto:21120439@student.hcmus">Bùi Minh Duy</a>
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExchangePoint extends PointChange {

    /** The amount of money reserved for exchange. */
    @Column(name = "reserved_money")
    private double reservedMoney;
}
