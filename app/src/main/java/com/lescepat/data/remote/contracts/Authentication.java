package com.lescepat.data.remote.contracts;

import com.google.gson.JsonObject;

import io.reactivex.Maybe;

public interface Authentication
{
    Maybe<JsonObject> login(String email, String password, String regid);

    Maybe<JsonObject> updateAccount(String name, String email, String address, String phoneNumber, String encodedImage);

    Maybe<JsonObject> register(String fname, String lname, String email, int studylevelid, String parentname, String schoolname, String address, String phone_number, String password, String cpassword);

    void logout();

    Maybe<JsonObject> forgotPassword(String id);
}
