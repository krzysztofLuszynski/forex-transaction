package forex.transaction.validation.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class TransactionValidationController {
    @PostMapping("/validate")
    String validate(@RequestBody List<String> transactions) {
        log.debug("Transactions validation for transactions: {}", transactions);

        return "OK";
    }
}
