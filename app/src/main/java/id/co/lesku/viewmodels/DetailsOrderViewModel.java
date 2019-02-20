package id.co.lesku.viewmodels;

import id.co.lesku.model.DetailsOrder;
import id.co.lesku.viewmodels.inputs.DetailsOrderViewModelInputs;
import id.co.lesku.viewmodels.outputs.DetailsOrderViewModelOutputs;

public class DetailsOrderViewModel extends BaseViewModel implements DetailsOrderViewModelInputs, DetailsOrderViewModelOutputs {

    private DetailsOrder mDetailsOrder;

    public DetailsOrderViewModel (DetailsOrder detailsOrder)
    {
        mDetailsOrder = detailsOrder;
        notifyChange();
    }

    @Override
    public void setDetailsOrder(DetailsOrder detailsOrder) {
        mDetailsOrder = detailsOrder;
        notifyChange();
    }

    @Override
    public int getId() {
        return mDetailsOrder.getId();
    }

    @Override
    public int getStudyClassId() {
        return mDetailsOrder.getStudyClassId();
    }

    @Override
    public String getSubjectName() {
        return String.valueOf(mDetailsOrder.getSubjectName());
    }

    @Override
    public String getTeacherName() {
        return String.valueOf(mDetailsOrder.getTeacherName());
    }

    @Override
    public String getTeacherAge() {
        return String.valueOf(mDetailsOrder.getTeacherAge());
    }

    @Override
    public String getTeacherImage() {
        return String.valueOf(mDetailsOrder.getTeacherImage());
    }

    @Override
    public String getStudyStartAt() {
        return String.valueOf(mDetailsOrder.getStudyStartAt());
    }

    @Override
    public int getStatus() {
        return mDetailsOrder.getStatus();
    }

    @Override
    public String getStatusMessage() {
        String message = "";
        String status = String.valueOf(mDetailsOrder.getStatus());
        if(status.equals("0")){
            message = "Belum Disetujui";
        } else if (status.equals("1")){
            message = "Sudah Disetujui";
        } else if (status.equals("3")){
            message = "Belum Dimulai";
        } else {
            message = "Sudah Selesai";
        }
        return message;
    }
}
