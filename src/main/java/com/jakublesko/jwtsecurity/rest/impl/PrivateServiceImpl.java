package com.jakublesko.jwtsecurity.rest.impl;

import com.jakublesko.jwtsecurity.rest.api.PrivateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrivateServiceImpl implements PrivateService {

    @Autowired
    private PrivateLogic logic;

    @Override
    public String getUserMessage() {
        return logic.getUserMessage();
    }

    @Override
    public String getAdminMessage() {
        return logic.getAdminMessage();
    }
}
