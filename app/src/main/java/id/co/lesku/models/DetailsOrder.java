package id.co.lesku.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailsOrder {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("study_class_id")
    @Expose
    private int studyClassId;
    @SerializedName("subject_id")
    @Expose
    private int subjectId;
    @SerializedName("subject_name")
    @Expose
    private String subjectName;
    @SerializedName("teacher_id")
    @Expose
    private int teacherId;
    @SerializedName("teacher_name")
    @Expose
    private String teacherName;
    @SerializedName("teacher_image")
    @Expose
    private String teacherImage;
    @SerializedName("study_start_at")
    @Expose
    private String studyStartAt;

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

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherImage() {
        return teacherImage;
    }

    public void setTeacherImage(String teacherImage) {
        this.teacherImage = teacherImage;
    }

    public String getStudyStartAt() {
        return studyStartAt;
    }

    public void setStudyStartAt(String studyStartAt) {
        this.studyStartAt = studyStartAt;
    }
}
