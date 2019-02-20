package id.co.lesku.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpcomingSchedule {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("study_class_id")
    @Expose
    private int studyClassId;
    @SerializedName("subject_id")
    @Expose
    private int subjectId;
    @SerializedName("study_start_at")
    @Expose
    private String studyStartAt;
    @SerializedName("subject_name")
    @Expose
    private String subjectName;
    @SerializedName("teacher_name")
    @Expose
    private String TeacherName;
    @SerializedName("teacher_image")
    @Expose
    private String TeacherImage;
    @SerializedName("teacher_address")
    @Expose
    private String TeacherAddress;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudyClassId() {
        return studyClassId;
    }

    public void setStudyClassId(int studyClassId) {
        this.studyClassId = studyClassId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTeacherName() {
        return TeacherName;
    }

    public void setTeacherName(String TeacherName) {
        this.TeacherName = TeacherName;
    }

    public String getTeacherAddress() {
        return TeacherAddress;
    }

    public void setTeacherAddress(String TeacherAddress) {
        this.TeacherAddress = TeacherAddress;
    }

    public String getTeacherImage() {
        return TeacherImage;
    }

    public void setTeacherImage(String TeacherImage) {
        this.TeacherImage = TeacherImage;
    }

    public String getStudyStartAt() {
        return studyStartAt;
    }

    public void setStudyStartAt(String studyStartAt) {
        this.studyStartAt = studyStartAt;
    }
}
