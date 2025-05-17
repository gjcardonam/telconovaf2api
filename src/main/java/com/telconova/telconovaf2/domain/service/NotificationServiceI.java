package com.telconova.telconovaf2.domain.service;

import com.telconova.telconovaf2.domain.entities.Assignment;

public interface NotificationServiceI {
    void notifyTechnician(Assignment assignment);
}
