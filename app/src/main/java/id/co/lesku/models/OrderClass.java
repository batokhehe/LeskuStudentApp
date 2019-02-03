package id.co.lesku.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("teacherId")
    @Expose
    private int teacherId;

    public OrderClass()
    {    }

    public OrderClass(int id, String name, String image, String subject, int subjectId, String date)
    {
        this.id = id;
        this.name = name;
        this.image = image;
        this.subject = subject;
        this.subjectId = subjectId;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
