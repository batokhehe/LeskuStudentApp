package com.lescepat.views.activities.others;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.JsonObject;
import com.lescepat.R;
import com.lescepat.data.DataManager;
import com.lescepat.manager.HawkManager;
import com.lescepat.model.User;
import com.lescepat.utils.RetrofitErrorAdapter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import id.co.flipbox.sosoito.LoadingLayout;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class AccountActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private HawkManager hawkManager;
    private String userImg;
    private byte[] decodedString;
    private ImageView ivAccountImg;
    private EditText etAccountName, etAccountEmail, etAccountAddress, etAccountPhone;
    private Button btnUpdateAccount, btnUpdateImage;
    private LoadingLayout llAccount;
    private int GALLERY = 1, CAMERA = 2;
    private String encodedImage = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        toolbar = (Toolbar) findViewById(R.id.toolbar_account);
        setSupportActionBar(toolbar);

        hawkManager = new HawkManager();
        userImg = hawkManager.getAppUserImg();
        decodedString = Base64.decode(userImg, Base64.DEFAULT);

        llAccount = (LoadingLayout) findViewById(R.id.ll_account);
        ivAccountImg = (ImageView) findViewById(R.id.iv_account_image);
        etAccountName = (EditText) findViewById(R.id.et_account_name);
        etAccountEmail = (EditText) findViewById(R.id.et_account_email);
        etAccountAddress = (EditText) findViewById(R.id.et_account_address);
        etAccountPhone = (EditText) findViewById(R.id.et_account_phonenumber);
        btnUpdateImage = (Button) findViewById(R.id.btn_camera_new_account_image);
        btnUpdateAccount = (Button) toolbar.findViewById(R.id.btn_update_account);

        loadAccountImage(decodedString);

        etAccountName.setText(String.valueOf(hawkManager.getAppUserName()));
        etAccountEmail.setText(String.valueOf(hawkManager.getAppUserEmail()));
        etAccountAddress.setText(String.valueOf(hawkManager.getAppUserAddress()));
        etAccountPhone.setText(String.valueOf(hawkManager.getAppUserPhoneNumber()));

        btnUpdateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });

        btnUpdateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = etAccountName.getText().toString();
                String email = etAccountEmail.getText().toString();
                String address = etAccountAddress.getText().toString();
                String phoneNumber = etAccountPhone.getText().toString();

                llAccount.showLoading(true, "Loading..");

                DataManager.can().updateAccount(name, email, address, phoneNumber, encodedImage)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<JsonObject>()
                        {
                            @Override
                            public void accept (JsonObject object) throws Exception
                            {
                                llAccount.showLoading(false);

                                User user = new User();
                                user.setFirstName(name);
                                user.setEmail(email);
                                user.setAddress(address);
                                user.setPhoneNumber(phoneNumber);
                                user.setAppImg(encodedImage);
                                user.setToken(String.valueOf(hawkManager.getAppUserToken()));
                                user.setBalance(String.valueOf(hawkManager.getAppUserBalance()));

                                hawkManager.storeAppUserData(
                                        user.getEmail(),
                                        user.getFirstName(),
                                        user.getBalance(),
                                        user.getAddress(),
                                        user.getPhoneNumber(),
                                        user.getToken(),
                                        user.getAppImg()
                                );
                                Toast.makeText(AccountActivity.this, "Your Account Data Has Updated..", Toast.LENGTH_SHORT).show();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept (Throwable throwable) throws Exception
                            {
                                llAccount.showLoading(false);
                                RetrofitErrorAdapter error = new RetrofitErrorAdapter(throwable);
                                Toast.makeText(AccountActivity.this, "Your Image's Size Too Large..", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

    }

    private void loadAccountImage(byte[] decodedString) {
        Glide.with(this)
                .asBitmap()
                .load(decodedString)
                .apply(new RequestOptions().circleCrop())
                .thumbnail(0.5f)
                .into(ivAccountImg);
    }

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallery();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    convertToBase64(bitmap, 0);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            convertToBase64(bitmap, 1);
        }
    }

    private String convertToBase64(Bitmap bitmap, int type) {
        int quality = type == 0 ? 5 : 70;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        encodedImage = Base64.encodeToString(byteArray, Base64.NO_WRAP);
        decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        loadAccountImage(decodedString);

//        Log.d("Upload File TRF Base 64", "LENGTH : " + encoded.length() + "convertToBase64: " + encoded);
        return encodedImage;
    }

    public void finishActivity(View v){
        finish();
    }
}
