package id.co.lesku.viewmodels;

import id.co.lesku.model.WaitingOrder;
import id.co.lesku.viewmodels.inputs.WaitingOrderViewModelInputs;
import id.co.lesku.viewmodels.outputs.WaitingOrderViewModelOutputs;

public class WaitingOrderViewModel extends BaseViewModel implements WaitingOrderViewModelInputs, WaitingOrderViewModelOutputs {

    private WaitingOrder mWaitingOrder;

    public WaitingOrderViewModel(WaitingOrder waitingOrder)
    {
        mWaitingOrder = waitingOrder;
        notifyChange();
    }

    @Override
    public void setOrder(WaitingOrder waitingOrder) {
        mWaitingOrder = waitingOrder;
        notifyChange();
    }

    @Override
    public String getId() {
        return String.valueOf(mWaitingOrder.getId());
    }

    @Override
    public String getProductId() {
        return String.valueOf(mWaitingOrder.getProductId());
    }

    @Override
    public String getProductName() {
        return String.valueOf(mWaitingOrder.getProductName());
    }

    @Override
    public String getOrderedAssembly() {
        return String.valueOf(mWaitingOrder.getOrderedAssembly())  + " Pertemuan";
    }

    @Override
    public String getOrderedSubject() {
        return String.valueOf(mWaitingOrder.getOrderedSubject()) + " Mata Pelajaran";
    }

    @Override
    public String getCreatedAt() {
        return "Tanggal Pemesanan : " + String.valueOf(mWaitingOrder.getCreatedAt());
    }

    @Override
    public int getStatus() {
        return mWaitingOrder.getStatus();
    }

    @Override
    public String getStatusMessages() {
        String messages = mWaitingOrder.getAccepted() + " dari " + mWaitingOrder.getOrderedAssembly() + " disetejui";
        return messages;
    }
}
