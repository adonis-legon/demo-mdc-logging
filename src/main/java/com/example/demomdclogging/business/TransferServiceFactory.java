package com.example.demomdclogging.business;

public interface TransferServiceFactory {
    TransferService get(String name);
}
