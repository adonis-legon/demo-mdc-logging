package com.example.demomdclogging.business;

import org.springframework.stereotype.Service;

import com.example.demomdclogging.exception.TransferException;
import com.example.demomdclogging.model.Transfer;

@Service("FixedTimeTransfer")
public class FixedTimeTransferService extends DemoTransferService{
    @Override
    protected void makeTransfer(Transfer transfer) throws TransferException{
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new TransferException(e);
        }
    }
}
