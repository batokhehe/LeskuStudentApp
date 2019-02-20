package id.co.lesku.viewmodels;

import id.co.lesku.model.UpcomingSchedule;
import id.co.lesku.viewmodels.inputs.UpcomingScheduleViewModelInputs;
import id.co.lesku.viewmodels.outputs.UpcomingScheduleViewModelOutputs;

public class UpcomingScheduleViewModel extends BaseViewModel implements UpcomingScheduleViewModelInputs, UpcomingScheduleViewModelOutputs {

    private UpcomingSchedule mUpcomingSchedule;

    public UpcomingScheduleViewModel(UpcomingSchedule upcomingSchedule)
    {
        mUpcomingSchedule = upcomingSchedule;
        notifyChange();
    }

    @Override
    public void setSchedule(UpcomingSchedule mUpcomingSchedule) {
        mUpcomingSchedule = mUpcomingSchedule;
        notifyChange();
    }

    @Override
    public String getId() {
        return String.valueOf(mUpcomingSchedule.getId());
    }

    @Override
    public String getStudyClassId() {
        return String.valueOf(mUpcomingSchedule.getStudyClassId());
    }

    @Override
    public String getSubjectId() {
        return String.valueOf(mUpcomingSchedule.getSubjectId());
    }

    @Override
    public String getSubjectName() {
        return String.valueOf(mUpcomingSchedule.getSubjectName());
    }

    @Override
    public String getTeacherName() {
        return String.valueOf(mUpcomingSchedule.getTeacherName());
    }

    @Override
    public String getTeacherAddress() {
        return String.valueOf(mUpcomingSchedule.getTeacherAddress());
    }

    @Override
    public String getTeacherImage() {
        return String.valueOf(mUpcomingSchedule.getTeacherImage());
    }

    @Override
    public String getStudyStartAt() {
        return "Tanggal Pertemuan : " + String.valueOf(mUpcomingSchedule.getStudyStartAt());
    }
}
