package com.example.demomdclogging.business;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.example.demomdclogging.exception.TransferException;
import com.example.demomdclogging.model.Transfer;

@Service("RandomTimeTransfer")
public class RandomTimeTransferService extends DemoTransferService{
    @Override
    protected void makeTransfer(Transfer transfer) throws TransferException{
        try {
            int waitSeconds = new Random().nextInt(3) + 1;
            Thread.sleep(waitSeconds * 1000);
        } catch (InterruptedException e) {
            throw new TransferException(e);
        }
    }
}
