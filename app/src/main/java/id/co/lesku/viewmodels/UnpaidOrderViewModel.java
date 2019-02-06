package id.co.lesku.viewmodels;

import id.co.lesku.model.UnpaidOrder;
import id.co.lesku.viewmodels.inputs.UnpaidOrderViewModelInputs;
import id.co.lesku.viewmodels.outputs.UnpaidOrderViewModelOutputs;

public class UnpaidOrderViewModel extends BaseViewModel implements UnpaidOrderViewModelInputs, UnpaidOrderViewModelOutputs {

    private UnpaidOrder mUnpaidOrder;

    public UnpaidOrderViewModel(UnpaidOrder unpaidOrder)
    {
        mUnpaidOrder = unpaidOrder;
        notifyChange();
    }

    @Override
    public void setOrder(UnpaidOrder unpaidOrder) {
        mUnpaidOrder = unpaidOrder;
        notifyChange();
    }

    @Override
    public String getId() {
        return String.valueOf(mUnpaidOrder.getId());
    }

    @Override
    public String getProductId() {
        return String.valueOf(mUnpaidOrder.getProductId());
    }

    @Override
    public String getProductName() {
        return String.valueOf(mUnpaidOrder.getProductName());
    }

    @Override
    public String getOrderedAssembly() {
        return String.valueOf(mUnpaidOrder.getOrderedAssembly())  + " Pertemuan";
    }

    @Override
    public String getOrderedSubject() {
        return String.valueOf(mUnpaidOrder.getOrderedSubject()) + " Mata Pelajaran";
    }

    @Override
    public String getCreatedAt() {
        return "Tanggal Pemesanan : " + String.valueOf(mUnpaidOrder.getCreatedAt());
    }

    @Override
    public int getStatus() {
        return mUnpaidOrder.getStatus();
    }

    @Override
    public String getStatusMessages() {
        String messages = "";
        if(mUnpaidOrder.getStatus() == 0) {
            messages = "Bukti Transfer Belum di Upload.";
        } else if(mUnpaidOrder.getStatus() == 1) {
            messages = "Bukti Transfer Sudah di Upload, Menunggu Verifikasi";
        }
        return messages;
    }
}
