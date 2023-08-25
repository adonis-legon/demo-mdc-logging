package com.example.demomdclogging.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demomdclogging.exception.TransferException;
import com.example.demomdclogging.model.Transfer;
import com.example.demomdclogging.model.TransferReceipt;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("DemoTransfer")
public class DemoTransferService extends TransferService{

    @Override
    protected void makeTransfer(Transfer transfer) throws TransferException{
        log.info("Demo transfer of: $" + transfer.getAmount());
    }

    @Override
    protected void beforeTransfer(Transfer transfer) {
        log.info("Preparing to transfer {}$.", transfer.getAmount());
    }

    @Override
    protected void afterTransfer(Transfer transfer, TransferReceipt transferReceipt) {
        log.info("Has transfer of {}$ completed successfully ? {}.", transfer.getAmount(), transferReceipt.isSuccess());
    }

    @Override
    public List<Transfer> getTransferList(int pageSize) {
        List<Transfer> transfers = new ArrayList<>();

        for (int i = 0; i < pageSize; i++) {
            String sampleTransactionId = UUID.randomUUID().toString();
            Long sampleTransactionAmount = (long) (new Random().nextInt(10) + 1) * 1000;

            transfers.add(new Transfer(sampleTransactionId, "demo", sampleTransactionAmount));
        }

        return transfers;
    }

    @Override
    protected void onTransferError(TransferException transferException) {
        log.error("Error in Transfer.", transferException);
    }
    
}
