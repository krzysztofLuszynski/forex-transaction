package forex.transaction.dto;

import forex.transaction.validation.date.DateFormatConstraint;
import forex.transaction.validation.date.FirstDateBeforeSecondDateConstraint;
import forex.transaction.validation.date.WeekendDayConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper = true)
@FirstDateBeforeSecondDateConstraint.List({
        @FirstDateBeforeSecondDateConstraint(
                firstDateStringProperty = "valueDate",
                secondDateStringProperty = "tradeDate",
                message = "Value date can not be after or equal trade date")
})
public class AbstractSpotForwardTransactionDTO extends TransactionDTO {
    @NotNull(message = "Value date can not be null")
    @DateFormatConstraint(message = "Value date format can be only yyyy-MM-dd")
    @WeekendDayConstraint(message = "Value date can not be on Saturday or Sunday")
    String valueDate;
}
