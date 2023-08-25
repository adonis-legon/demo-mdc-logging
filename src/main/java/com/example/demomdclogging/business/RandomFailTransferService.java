package com.example.demomdclogging.business;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.example.demomdclogging.exception.TransferException;
import com.example.demomdclogging.model.Transfer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("RandomFailTransfer")
public class RandomFailTransferService extends DemoTransferService{
    @Override
    protected void makeTransfer(Transfer transfer) throws TransferException{
        boolean failTransfer = new Random().nextInt(10) % 2 == 0;
        int waitSeconds = new Random().nextInt(3) + 1;
        try {
            Thread.sleep(waitSeconds * 1000);

            if(failTransfer){
                onTransferError(new TransferException(new Exception("Transaction has failed.")));
            } else{
                log.info("Random Fail transfer is successful for: $" + transfer.getAmount());
            }
        } catch (InterruptedException e) {
            onTransferError(new TransferException(e));
        }
    }
}
