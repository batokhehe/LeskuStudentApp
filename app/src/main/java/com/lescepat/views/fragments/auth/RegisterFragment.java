package com.lescepat.views.fragments.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.google.gson.JsonObject;
import com.lescepat.R;
import com.lescepat.data.DataManager;
import com.lescepat.databinding.FragmentRegisterBinding;
import com.lescepat.model.StudyLevel;
import com.lescepat.utils.RetrofitErrorAdapter;
import com.lescepat.utils.constants.S;
import com.lescepat.views.fragments.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import br.com.ilhasoft.support.validation.Validator;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class RegisterFragment extends BaseFragment
{
    private static final String TAG = RegisterFragment.class.getSimpleName();

    private FragmentRegisterBinding mBinding;
    private Validator mValidator;
    List<StudyLevel> mStudyLevel;
    List<String> listStudyClassSpinner;
    List<Integer> listStudyClassSpinnerId;
    private int studylevel = 0;

    public RegisterFragment()
    {
        // Required empty public constructor
        setArguments(new Bundle());
        mStudyLevel = new ArrayList<>();
    }

    @Override
    public void onAttach (Context context)
    {
        super.onAttach(context);
    }

    @Override
    public void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false);
        mValidator = new Validator(mBinding);
        mValidator.enableFormValidationMode();

        mBinding.llRegister.showLoading(true, "Loading..");

        DataManager.can().getStudyLevelList().observeOn(AndroidSchedulers.mainThread())
                .defaultIfEmpty(new ArrayList<StudyLevel>())
                .subscribe(new Consumer<List<StudyLevel>>()
                {
                    @Override
                    public void accept (List<StudyLevel> studyLevels) throws Exception
                    {
                        if (mStudyLevel != null) { mStudyLevel.clear(); }
                        mStudyLevel.addAll(studyLevels);
                        listStudyClassSpinner = new ArrayList<String>();
                        listStudyClassSpinnerId = new ArrayList<Integer>();
                        for (int i = 0; i < mStudyLevel.size(); i++){
                            listStudyClassSpinner.add(mStudyLevel.get(i).getName() + ' ' + mStudyLevel.get(i).getDescription());
                            listStudyClassSpinnerId.add(mStudyLevel.get(i).getId());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_dropdown_item_1line, listStudyClassSpinner);
                        mBinding.spinnerStudyLevel.setAdapter(adapter);
                        mBinding.llRegister.showLoading(false);
                        if (mStudyLevel.size() == 0)
                        {
                            mBinding.llRegister.showEmptyView(true);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept (Throwable throwable) throws Exception
                    {
                        RetrofitErrorAdapter error = new RetrofitErrorAdapter(throwable);
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        mBinding.llRegister.showLoading(false);
                    }
                });

        initUI();
        initEvent();

        return mBinding.getRoot();
    }

    @Override
    public void initUI()
    {
        getActivity().setTitle(S.register);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void initEvent()
    {
        mBinding.btnRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view)
            {
                hideKeyboard();
                submitRegister();
            }
        });

        mBinding.spinnerStudyLevel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d(TAG," selected spinner "+ position);
//                Toast.makeText(getContext(), "" + position, Toast.LENGTH_SHORT).show();
                studylevel = position;
            }
        });

    }

    @Override
    public void onStart ()
    {
        super.onStart();
    }

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStop ()
    {
        super.onStop();
    }

    @Override
    public void onDetach ()
    {
        super.onDetach();
    }

    public void submitRegister ()
    {
        //USERS
        final String fname        = mBinding.etRegFname.getText().toString();
        final String lname        = mBinding.etRegLname.getText().toString();
        final String email       = mBinding.etRegEmail.getText().toString();
        final String password    = mBinding.etRegPassword.getText().toString();
        final String cpassword   = mBinding.etRegCpassword.getText().toString();

        //STUDENT
        final String parent_name = mBinding.etRegParentname.getText().toString();
        final String school_name = mBinding.etRegSchoolname.getText().toString();
        final String address = mBinding.etRegAddress.getText().toString();
        final String phone_number = mBinding.etRegPhonenumber.getText().toString();
        final int studylevelid = listStudyClassSpinnerId.get(studylevel);

        if (!mValidator.validate())
        {
            return;
        }

        //precaution for double click
        mBinding.btnRegister.setEnabled(false);

        mBinding.llRegister.showLoading(true, "Registering..");

        DataManager.can().register(fname, lname, email, studylevelid, parent_name, school_name, address, phone_number, password, cpassword)
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribe(new Consumer<JsonObject>()
                   {
                       @Override
                       public void accept (JsonObject object) throws Exception
                       {
//                           mBinding.llRegister.showLoading(false);
//                           mBinding.btnRegister.setEnabled(true);
//                           Intent intent = new Intent(getContext(), LoginActivity.class);
//                           Toast.makeText(getContext(), "Register Success, Please Check Your E-mail For Verification", Toast.LENGTH_SHORT).show();
//                           startActivity(intent);
//                           getActivity().finish();

                           LoginFragment fragment = new LoginFragment();
                           Bundle args = new Bundle();
                           args.putBoolean("registered", true);
                           args.putString("uname", email);
                           args.putString("pass", password);
                           fragment.setArguments(args);

                           getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, fragment).commit();
                       }
                   }, new Consumer<Throwable>() {
                       @Override
                       public void accept (Throwable throwable) throws Exception
                       {
                           mBinding.llRegister.showLoading(false);
                           RetrofitErrorAdapter error = new RetrofitErrorAdapter(throwable);
                           Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                           mBinding.btnRegister.setEnabled(true);
                       }
                   });
    }
}
