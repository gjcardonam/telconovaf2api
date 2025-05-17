package com.telconova.telconovaf2.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    private String title;
    private String message;
    private String date;
    private User user;
}
