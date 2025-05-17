package com.telconova.telconovaf2.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    private Date timeStamp;
    private List<Order> orders;
}
