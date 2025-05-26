package com.doubleo.didagent.controller;

import com.doubleo.didagent.dto.response.DidCreateResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dids")
public class DidController {

    @PostMapping
    public DidCreateResponse didCreate() {
        return new DidCreateResponse();
    }
}
