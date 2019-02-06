package id.co.lesku.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderClass {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("subject")
    @Expose
    private String subject;

    @SerializedName("subjectId")
    @Expose
    private int subjectId;

    @SerializedName("teacherId")
    @Expose
    private int teacherId;

    @SerializedName("schedule")
    @Expose
    private List<String> schedule;

    @SerializedName("selectedSchedule")
    @Expose
    private String selectedSchedule;

    public OrderClass()
    {    }

    public OrderClass(int id, String name, String image, String subject, int subjectId, List<String> schedule, String selectedSchedule)
    {
        this.id = id;
        this.name = name;
        this.image = image;
        this.subject = subject;
        this.subjectId = subjectId;
        this.schedule = schedule;
        this.selectedSchedule = selectedSchedule;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public List<String> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<String> schedule) {
        this.schedule = schedule;
    }

    public String getSelectedSchedule() {
        return selectedSchedule;
    }

    public void setSelectedSchedule(String selectedSchedule) {
        this.selectedSchedule = selectedSchedule;
    }
}
