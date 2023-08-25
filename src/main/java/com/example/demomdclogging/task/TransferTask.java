package com.example.demomdclogging.task;

import org.slf4j.MDC;

import com.example.demomdclogging.business.TransferService;
import com.example.demomdclogging.model.Transfer;

public class TransferTask implements Runnable {

    private Transfer transfer;

    private TransferService transferService;

    public TransferTask(Transfer transfer, TransferService transferService) {
        this.transfer = transfer;
        this.transferService = transferService;
    }

    @Override
    public void run() {
        MDC.put("transaction.id", transfer.getTransactionId());
        MDC.put("transaction.owner", transfer.getSender());

        transferService.transfer(transfer);

        MDC.clear();
    }
    
}
