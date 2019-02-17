package id.co.lesku.views.fragments.order;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import id.co.lesku.R;
import id.co.lesku.data.DataManager;
import id.co.lesku.databinding.FragmentDetailsOrderBinding;
import id.co.lesku.model.DetailsOrder;
import id.co.lesku.utils.RetrofitErrorAdapter;
import id.co.lesku.viewmodels.DetailsOrderListViewModel;
import id.co.lesku.views.adapters.order.DetailsOrderAdapter;
import id.co.lesku.views.fragments.BaseFragment;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

import static android.app.Activity.RESULT_CANCELED;

public class DetailsOrderFragment extends BaseFragment {

    FragmentDetailsOrderBinding mBinding;
    List<DetailsOrder> mDetailsOrder;
    private OnFragmentInteractionListener mListener;
    DetailsOrderAdapter adapter;
    private int position;
    private String id, status;
    private Button btnUploadTrfFile;
    private TextView tvUploadTrfFile;
    private int GALLERY = 1, CAMERA = 2;

    public DetailsOrderFragment() {
        // Required empty public constructor
        setArguments(new Bundle());
        mDetailsOrder = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initUI() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details_order, container, false);
        mBinding.setTeacherOrders(new DetailsOrderListViewModel());

        adapter = new DetailsOrderAdapter(mDetailsOrder, getContext());

        mBinding.rvDetailsOrder.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.rvDetailsOrder.setAdapter(adapter);

        btnUploadTrfFile = mBinding.btnUploadTrfFile;
        tvUploadTrfFile = mBinding.tvUploadTrfFile;

        id = getArguments().getString("id");
        status = getArguments().getString("status");
        position = getArguments().getInt("position");

//        Toast.makeText(getContext(), "" + status, Toast.LENGTH_SHORT).show();

        if(status.equals("1")){
            btnUploadTrfFile.setVisibility(View.GONE);
            tvUploadTrfFile.setVisibility(View.VISIBLE);
        } else {
            btnUploadTrfFile.setVisibility(View.VISIBLE);
            tvUploadTrfFile.setVisibility(View.GONE);
        }

        requestMultiplePermissions();
        btnUploadTrfFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });

        mBinding.llDetailsOrder.showLoading(true, "Loading..");

        DataManager.can().getDetailsOrderList(id).observeOn(AndroidSchedulers.mainThread())
                .defaultIfEmpty(new ArrayList<DetailsOrder>())
                .subscribe(new Consumer<List<DetailsOrder>>()
                {
                    @Override
                    public void accept (List<DetailsOrder> details) throws Exception
                    {
                        if (mDetailsOrder != null) { mDetailsOrder.clear(); }
                        mDetailsOrder.addAll(details);
                        mBinding.rvDetailsOrder.getAdapter().notifyDataSetChanged();
                        mBinding.llDetailsOrder.showLoading(false);
                        if (mDetailsOrder.size() == 0)
                        {
                            mBinding.llDetailsOrder.showEmptyView(true);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept (Throwable throwable) throws Exception
                    {
                        RetrofitErrorAdapter error = new RetrofitErrorAdapter(throwable);
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        mBinding.llDetailsOrder.showLoading(false);
                    }
                });

        return mBinding.getRoot();
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getContext());
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
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), contentURI);
                    convertToBase64(bitmap);
//                    Toast.makeText(getContext(), "Image Saved!", Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            convertToBase64(bitmap);
//            Toast.makeText(getContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    private String convertToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.NO_WRAP);

        Log.d("Upload File TRF Base 64", "LENGTH : " + encoded.length()+ "convertToBase64: " + encoded);

        DataManager.can().uploadTrfFile(id, encoded)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JsonObject>()
                {
                    @Override
                    public void accept (JsonObject object) throws Exception
                    {
                        Toast.makeText(getContext(), "File Uploaded", Toast.LENGTH_SHORT).show();
                        btnUploadTrfFile.setVisibility(View.GONE);
                        tvUploadTrfFile.setVisibility(View.VISIBLE);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept (Throwable throwable) throws Exception
                    {
                        RetrofitErrorAdapter error = new RetrofitErrorAdapter(throwable);
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        return encoded;
    }

    private void requestMultiplePermissions() {
        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }
}
