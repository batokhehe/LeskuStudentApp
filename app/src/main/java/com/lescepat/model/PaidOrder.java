package com.lescepat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaidOrder {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("product_id")
    @Expose
    private int productId;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("ordered_assembly")
    @Expose
    private String orderedAssembly;
    @SerializedName("ordered_subject")
    @Expose
    private String orderedSubject;
    @SerializedName("trf_file")
    @Expose
    private String trfFile;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("price")
    @Expose
    private String price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOrderedAssembly() {
        return orderedAssembly;
    }

    public void setOrderedAssembly(String orderedAssembly) {
        this.orderedAssembly = orderedAssembly;
    }

    public String getOrderedSubject() {
        return orderedSubject;
    }

    public void setOrderedSubject(String orderedSubject) {
        this.orderedSubject = orderedSubject;
    }

    public String getTrfFile() {
        return trfFile;
    }

    public void setTrfFile(String trfFile) {
        this.trfFile = trfFile;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
