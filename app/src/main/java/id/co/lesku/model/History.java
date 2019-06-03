package id.co.lesku.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class History {
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
    private String teacherName;
    @SerializedName("teacher_image")
    @Expose
    private String teacherImage;
    @SerializedName("teacher_address")
    @Expose
    private String teacherAddress;
    @SerializedName("student_status")
    @Expose
    private int studentStatus;
    @SerializedName("rating")
    @Expose
    private String rating;

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
        return teacherName;
    }

    public void setTeacherName(String TeacherName) {
        this.teacherName = TeacherName;
    }

    public String getTeacherAddress() {
        return teacherAddress;
    }

    public void setTeacherAddress(String TeacherAddress) {
        this.teacherAddress = TeacherAddress;
    }

    public String getTeacherImage() {
        return teacherImage;
    }

    public void setTeacherImage(String TeacherImage) {
        this.teacherImage = TeacherImage;
    }

    public String getStudyStartAt() {
        return studyStartAt;
    }

    public void setStudyStartAt(String studyStartAt) {
        this.studyStartAt = studyStartAt;
    }

    public int getStudentStatus() {
        return studentStatus;
    }

    public void setStudentStatus(int studentStatus) {
        this.studentStatus = studentStatus;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
