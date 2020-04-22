package com.jakublesko.jwtsecurity.rest.impl;

import com.jakublesko.jwtsecurity.rest.api.PublicService;
import org.springframework.stereotype.Service;

@Service
public class PublicServiceImpl implements PublicService {

    @Override
    public String getMessage() {
        return "Hello from public API controller";
    }
}
