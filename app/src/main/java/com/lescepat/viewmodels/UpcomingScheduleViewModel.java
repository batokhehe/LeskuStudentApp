package com.lescepat.viewmodels;

import com.lescepat.model.UpcomingSchedule;
import com.lescepat.viewmodels.inputs.UpcomingScheduleViewModelInputs;
import com.lescepat.viewmodels.outputs.UpcomingScheduleViewModelOutputs;

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

    @Override
    public int getStudentStatus() {
        return mUpcomingSchedule.getStudentStatus();
    }

    @Override
    public String getStatusText() {
        int status = mUpcomingSchedule.getStudentStatus();
        String uniqueCode = mUpcomingSchedule.getUniqueCode();
        String message = "";
        if(status == 4){
            message = "Pertemuan telah dikonfirmasi";
        } else if (status == 5){
            message = "Menunggu Persetujan Reschedule";
        } else if (status == 6){
            message = "Reschedule";
        }
        return uniqueCode + "\n" + message;
    }
}
