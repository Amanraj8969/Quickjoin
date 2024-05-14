package org.jhipster.quickjoin.web.rest;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This controller handles creating payment intents using Stripe.
 */
@RestController
@RequestMapping("/api")
public class PaymentController {

    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);

    /**
     * Creates a payment intent for a given item ID.
     *
     * @param itemId The ID of the item for which the payment intent is created.
     * @return A ResponseEntity containing the payment intent client secret or an error message.
     */
    @GetMapping("/create-payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestParam Long itemId) {
        log.info("Received request to create payment intent for itemId: {}", itemId);

        // Replace with a secure way of storing your Stripe secret key (e.g., environment variable)
        String stripeSecretKey =
            "sk_test_51MPm5BSJseISqjDO87xbdKZ4n00lPdqtTDGIYgif2x9VtQXm4Eq3si8zQO1Fgc7fEuvoq2en1PEZKhmFtXQNTfhb00BzIv8oGH";
        Stripe.apiKey = stripeSecretKey;

        // Replace with your logic to retrieve item price
        Long amount = getItemPrice(itemId);
        String currency = "usd";

        Map<String, Object> paymentIntentParams = new HashMap<>();
        paymentIntentParams.put("amount", amount);
        paymentIntentParams.put("currency", currency);

        try {
            PaymentIntent intent = PaymentIntent.create(paymentIntentParams);
            return ResponseEntity.ok(intent.getClientSecret());
        } catch (StripeException e) {
            log.error("Error creating payment intent", e);
            return ResponseEntity.badRequest().body("Failed to create payment intent");
        }
    }

    /**
     * This method needs to be implemented to retrieve the price of the item based on the itemId.
     * Replace this with your logic for fetching item details.
     *
     * @param itemId The ID of the item.
     * @return The price of the item in cents (you can adjust the unit as needed).
     */
    private Long getItemPrice(Long itemId) {
        // Implement your logic to retrieve item price based on itemId
        // This is a placeholder, replace with your actual implementation
        return 100L; // Placeholder price of $1.00 in cents
    }
}
