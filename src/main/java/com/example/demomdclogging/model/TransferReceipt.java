package com.example.demomdclogging.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransferReceipt {

    private Transfer transfer;
    
    private boolean isSuccess;
    
    private String details;
}
