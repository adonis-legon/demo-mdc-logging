package com.example.demomdclogging.business;

import java.util.List;

import com.example.demomdclogging.exception.TransferException;
import com.example.demomdclogging.model.Transfer;
import com.example.demomdclogging.model.TransferReceipt;

public abstract class TransferService {

    public TransferReceipt transfer(Transfer transfer){
        
        TransferReceipt transferReceipt = new TransferReceipt();
        transferReceipt.setTransfer(transfer);

        try {
            beforeTransfer(transfer);

            makeTransfer(transfer);
            transferReceipt.setSuccess(true);
            transferReceipt.setDetails("Transfer success");
            
            afterTransfer(transfer, transferReceipt);
        } catch (Exception e) {
            transferReceipt.setSuccess(false);
            transferReceipt.setDetails("Transfer error. Message: " + e.getMessage());

            onTransferError(new TransferException(e));
        }

        return transferReceipt;
    }

    public abstract List<Transfer> getTransferList(int pageSize);

    protected abstract void makeTransfer(Transfer transfer) throws TransferException;

    protected abstract void beforeTransfer(Transfer transfer);

    protected abstract void afterTransfer(Transfer transfer, TransferReceipt transferReceipt);

    protected abstract void onTransferError(TransferException transferException);
}
