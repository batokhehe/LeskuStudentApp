package id.co.lesku.viewmodels;

import id.co.lesku.model.TeacherOrder;
import id.co.lesku.viewmodels.inputs.TeacherOrderViewModelInputs;
import id.co.lesku.viewmodels.outputs.TeacherOrderViewModelOutputs;

public class TeacherOrderViewModel extends BaseViewModel implements TeacherOrderViewModelInputs, TeacherOrderViewModelOutputs {
    private TeacherOrder mTeacherOrder;

    public TeacherOrderViewModel (TeacherOrder teacherOrder)
    {
        mTeacherOrder = teacherOrder;
        notifyChange();
    }

    @Override
    public void setTeacherOrder (TeacherOrder teacherOrder)
    {
        mTeacherOrder = teacherOrder;
        notifyChange();
    }

    @Override
    public int getId ()
    {
        return mTeacherOrder.getId();
    }

    @Override
    public String getName ()
    {
        return String.valueOf(mTeacherOrder.getName());
    }

    @Override
    public String getAge()
    {
        return String.valueOf(mTeacherOrder.getAge());
    }

    @Override
    public String getGraduated ()
    {
        return String.valueOf(mTeacherOrder.getGraduated());
    }

    @Override
    public String getMajor ()
    {
        return String.valueOf(mTeacherOrder.getMajor());
    }

    @Override
    public String getAddress ()
    {
        return String.valueOf(mTeacherOrder.getAddress());
    }

    @Override
    public String getImage ()
    {
        return String.valueOf(mTeacherOrder.getImage());
    }
}
