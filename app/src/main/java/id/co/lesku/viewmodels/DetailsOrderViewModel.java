package id.co.lesku.viewmodels;

import id.co.lesku.models.DetailsOrder;
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
    public String getTeacherImage() {
        return String.valueOf(mDetailsOrder.getTeacherImage());
    }

    @Override
    public String getStudyStartAt() {
        return String.valueOf(mDetailsOrder.getStudyStartAt());
    }
}
