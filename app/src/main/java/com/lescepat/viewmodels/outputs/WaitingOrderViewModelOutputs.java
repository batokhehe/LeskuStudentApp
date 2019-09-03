package com.lescepat.viewmodels.outputs;

public interface WaitingOrderViewModelOutputs {
    public String getId();

    public String getProductId();

    public String getProductName();

    public String getOrderedAssembly();

    public String getOrderedSubject();

    public String getCreatedAt();

    public int getStatus();

    public String getStatusMessages();
}
