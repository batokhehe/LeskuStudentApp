package id.co.lesku.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeacherSchedule {
    @SerializedName("schedule")
    @Expose
    private List<String> schedule = null;

    public List<String> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<String> schedule) {
        this.schedule = schedule;
    }
}
