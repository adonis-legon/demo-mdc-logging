package com.example.demomdclogging.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Transfer {

    private String transactionId;

    private String sender;

    private Long amount;
}
