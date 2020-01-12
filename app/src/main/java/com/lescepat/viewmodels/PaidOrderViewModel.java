package com.lescepat.viewmodels;

import com.lescepat.model.PaidOrder;
import com.lescepat.viewmodels.inputs.PaidOrderViewModelInputs;
import com.lescepat.viewmodels.outputs.PaidOrderViewModelOutputs;

public class PaidOrderViewModel extends BaseViewModel implements PaidOrderViewModelInputs, PaidOrderViewModelOutputs {

    private PaidOrder mPaidOrder;

    public PaidOrderViewModel(PaidOrder paidOrder)
    {
        mPaidOrder = paidOrder;
        notifyChange();
    }

    @Override
    public void setOrder(PaidOrder PaidOrder) {
        mPaidOrder = PaidOrder;
        notifyChange();
    }

    @Override
    public String getId() {
        return String.valueOf(mPaidOrder.getId());
    }

    @Override
    public String getProductId() {
        return String.valueOf(mPaidOrder.getProductId());
    }

    @Override
    public String getProductName() {
        return String.valueOf(mPaidOrder.getProductName());
    }

    @Override
    public String getOrderedAssembly() {
        return String.valueOf(mPaidOrder.getOrderedAssembly())  + " Pertemuan";
    }

    @Override
    public String getOrderedSubject() {
        return String.valueOf(mPaidOrder.getOrderedSubject()) + " Mata Pelajaran";
    }

    @Override
    public String getCreatedAt() {
        return "Pesanan : " + String.valueOf(mPaidOrder.getCreatedAt());
    }

    @Override
    public int getStatus() {
        return mPaidOrder.getStatus();
    }

    @Override
    public String getStatusMessages() {
        String messages = "";
        return messages;
    }
}
