package id.co.lesku.views.fragments.auth;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.JsonObject;

import br.com.ilhasoft.support.validation.Validator;
import id.co.lesku.LeskuApplication;
import id.co.lesku.R;
import id.co.lesku.data.DataManager;
import id.co.lesku.databinding.FragmentLoginBinding;
import id.co.lesku.manager.HawkManager;
import id.co.lesku.model.User;
import id.co.lesku.utils.RetrofitErrorAdapter;
import id.co.lesku.utils.constants.S;
import id.co.lesku.views.activities.MainActivity;
import id.co.lesku.views.fragments.BaseFragment;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class LoginFragment extends BaseFragment
{
    private static final String TAG = LoginFragment.class.getSimpleName();

    private FragmentLoginBinding               mBinding;
    private Validator                          mValidator;
    private OnLoginFragmentInteractionListener mListener;
    private HawkManager hawkManager;
    LeskuApplication mApp;
    Configuration configuration = null;

    public LoginFragment()
    {
        // Required empty public constructor
        setArguments(new Bundle());
        mApp = new LeskuApplication();
    }

    @Override
    public void onAttach (Context context)
    {
        super.onAttach(context);
        if (context instanceof OnLoginFragmentInteractionListener)
        {
            mListener = (OnLoginFragmentInteractionListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        mValidator = new Validator(mBinding);
        mValidator.enableFormValidationMode();

        initUI();
        initEvent();

        return mBinding.getRoot();
    }

    @Override
    public void initUI()
    {
        getActivity().setTitle(S.sign_in);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void initEvent()
    {
        mBinding.btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view)
            {
                hideKeyboard();
                submitLogin();
            }
        });

        mBinding.tvForgotPassword.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                if (mListener != null)
                {
                    mListener.onForgotPasswordClick();
                }
            }
        });

        mBinding.tvRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                if (mListener != null)
                {
                    mListener.onRegisterClick();
                }
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
        mListener = null;
    }

    public void submitLogin ()
    {

        final String id       = mBinding.tilUserIdWrapper.getEditText().getText().toString();
        final String password = mBinding.etPassword.getText().toString();

        hawkManager = new HawkManager();
        String regid = hawkManager.getFirebaseId();

        if (!mValidator.validate())
        {
            return;
        }

        //precaution for double click
        mBinding.btnLogin.setEnabled(false);

        mBinding.loginLoading.showLoading(true, "Signing in...");

        DataManager.can().login(id, password, regid)
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribe(new Consumer<JsonObject>()
                   {
                       @Override
                       public void accept (JsonObject object) throws Exception
                       {
                           mBinding.loginLoading.showLoading(false);
                           mBinding.btnLogin.setEnabled(true);

                           User user = new User();
                           JsonObject obj = object.getAsJsonObject("response");
                           String tempFName = obj.get("first_name").getAsString();
                           String tempLName = obj.get("last_name").getAsString();
                           user.setFirstName(tempFName + ' ' + tempLName);
                           user.setEmail(obj.get("email").getAsString());
                           user.setAddress(obj.get("address").getAsString());
                           user.setPhoneNumber(obj.get("phone_number").getAsString());
                           user.setToken(obj.get("token").getAsString());
                           user.setAppImg(obj.get("app_img").getAsString());

                           hawkManager.storeAppUserData(
                                   user.getEmail(),
                                   user.getFirstName(),
                                   user.getAddress(),
                                   user.getPhoneNumber(),
                                   user.getToken(),
                                   user.getAppImg()
                           );

                           Log.d(TAG, "Name : " + user.getLastName());
                           Log.d(TAG, "Email : " + user.getEmail());
                           Log.d(TAG, "Token : " + hawkManager.getAppUserToken());
                           Log.d(TAG, "App Img : " + user.getAppImg());


                           if(hawkManager.getAppUserToken() != null){
//                               mApp.updateService();
//                               Toast.makeText(getContext(), "Token : " + prefManager.getAppUserToken(), Toast.LENGTH_SHORT).show();
                               mApp.onLoggedIn(getContext());
                               Intent intent = new Intent(getContext(), MainActivity.class);
                               startActivity(intent);
                               getActivity().finish();
                           }

                       }
                   }, new Consumer<Throwable>() {
                       @Override
                       public void accept (Throwable throwable) throws Exception
                       {
                           mBinding.loginLoading.showLoading(false);
                           RetrofitErrorAdapter error = new RetrofitErrorAdapter(throwable);
                           Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                           mBinding.btnLogin.setEnabled(true);
                       }
                   });
    }

    public interface OnLoginFragmentInteractionListener
    {
        void onForgotPasswordClick();
        void onRegisterClick();
    }
}
