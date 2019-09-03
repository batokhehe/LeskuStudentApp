package com.lescepat.viewmodels;

import com.lescepat.model.History;
import com.lescepat.viewmodels.inputs.HistoryViewModelInputs;
import com.lescepat.viewmodels.outputs.HistoryViewModelOutputs;

public class HistoryViewModel extends BaseViewModel implements HistoryViewModelInputs, HistoryViewModelOutputs {

    private History mHistory;

    public HistoryViewModel(History History)
    {
        mHistory = History;
        notifyChange();
    }

    @Override
    public void setHistory(History mHistory) {
        mHistory = mHistory;
        notifyChange();
    }

    @Override
    public String getId() {
        return String.valueOf(mHistory.getId());
    }

    @Override
    public String getStudyClassId() {
        return String.valueOf(mHistory.getStudyClassId());
    }

    @Override
    public String getSubjectId() {
        return String.valueOf(mHistory.getSubjectId());
    }

    @Override
    public String getSubjectName() {
        return String.valueOf(mHistory.getSubjectName());
    }

    @Override
    public String getTeacherName() {
        return String.valueOf(mHistory.getTeacherName());
    }

    @Override
    public String getTeacherAddress() {
        return String.valueOf(mHistory.getTeacherAddress());
    }

    @Override
    public String getTeacherImage() {
        return String.valueOf(mHistory.getTeacherImage());
    }

    @Override
    public String getStudyStartAt() {
        return "Tanggal Pertemuan : " + String.valueOf(mHistory.getStudyStartAt());
    }

    @Override
    public int getStudentStatus() {
        return mHistory.getStudentStatus();
    }

    @Override
    public String getStatusText() {
        int status = mHistory.getStudentStatus();
        String message = "";
        if(status == 4){
            message = "Pertemuan telah dikonformasi";
        } else if (status == 5){
            message = "Menunggu Persetujan Reschedule";
        } else if (status == 6){
            message = "Reschedule";
        }
        return message;
    }
}
