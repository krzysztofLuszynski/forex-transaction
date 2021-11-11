package forex.transaction.domain.vanillaoption;

import forex.transaction.domain.Transaction;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper = true)
public class VanillaOptionTransaction extends Transaction {
    VanillaOptionStyle style;
    VanillaOptionStrategy strategy;
    LocalDate deliveryDate;
    LocalDate expiryDate;
    LocalDate excerciseStartDate;
    String payCcy;
    BigDecimal premium;
    String premiumCcy;
    String premiumType;
    LocalDate premiumDate;
}
