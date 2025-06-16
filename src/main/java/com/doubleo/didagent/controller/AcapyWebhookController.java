package com.doubleo.didagent.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhooks")
@Slf4j
public class AcapyWebhookController {

    @PostMapping("/**")
    @ResponseStatus(HttpStatus.OK)
    public void receiveAnyWebhook(
            HttpServletRequest req,
            @RequestBody Map<String, Object> payload,
            @RequestHeader Map<String, String> headers) {
        String path = req.getRequestURI();
        log.info("=== Webhook Path: {} ===", path);
        log.info("--- Headers ---");
        headers.forEach((k, v) -> log.info("{}: {}", k, v));
        log.info("--- Payload ---");
        log.info("{}", payload);
    }
}
