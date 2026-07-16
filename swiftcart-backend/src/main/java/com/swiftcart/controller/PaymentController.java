package com.swiftcart.controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.swiftcart.dto.ApiResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @PostConstruct
    public void init() {
        Stripe.apiKey = System.getenv("STRIPE_SECRET_KEY");
    }

    @PostMapping("/create-intent")
    public ResponseEntity<ApiResponse<Map<String, String>>> createPaymentIntent(@RequestBody Map<String, Object> data) {
        try {
            Long amount = Long.valueOf(data.get("amount").toString());
            String currency = data.getOrDefault("currency", "usd").toString();

            PaymentIntentCreateParams params =
                    PaymentIntentCreateParams.builder()
                            .setAmount(amount)
                            .setCurrency(currency)
                            .build();

            PaymentIntent intent = PaymentIntent.create(params);

            Map<String, String> responseData = new HashMap<>();
            responseData.put("clientSecret", intent.getClientSecret());

            return ResponseEntity.ok(new ApiResponse<>(true, "Payment Intent created", responseData));
        } catch (StripeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Invalid request body"));
        }
    }
}
