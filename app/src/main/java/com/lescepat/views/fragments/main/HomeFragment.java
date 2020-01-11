package com.lescepat.views.fragments.main;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lescepat.R;
import com.lescepat.data.DataManager;
import com.lescepat.databinding.FragmentHomeBinding;
import com.lescepat.manager.HawkManager;
import com.lescepat.model.Product;
import com.lescepat.model.StudyLevel;
import com.lescepat.model.Subject;
import com.lescepat.utils.RetrofitErrorAdapter;
import com.lescepat.utils.constants.K;
import com.lescepat.viewmodels.ProductListViewModel;
import com.lescepat.views.activities.auth.LoginActivity;
import com.lescepat.views.adapters.product.ProductAdapter;
import com.lescepat.views.fragments.BaseFragment;
import com.lescepat.views.fragments.transaction.OrderClassFragment;
import com.orhanobut.hawk.Hawk;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class HomeFragment extends BaseFragment {
    public FragmentHomeBinding mBinding;
    List<Product>       mProduct;
    List<Subject>       mSubject;
    private OnFragmentInteractionListener mListener;
    ProductAdapter adapter;
    private HawkManager hawkManager;
    private List<StudyLevel> mStudyLevel;
    int studyLevelId = 0;
    int studyOrder = 0;

    public HomeFragment() {
        // Required empty public constructor
        setArguments(new Bundle());
        mProduct = new ArrayList<>();
        mSubject = new ArrayList<>();
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

//        Toast.makeText(getContext(), "Subject : " + Hawk.get(K.SUBJECT_LIST), Toast.LENGTH_SHORT).show();

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        mBinding.setProducts(new ProductListViewModel());

        adapter = new ProductAdapter(mProduct, getContext());
        hawkManager = new HawkManager();

//        int mNoOfColumns = RecyclerViewUtils.calculateNoOfColumns(getContext(), 300);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);

        mBinding.homeRecycleView.setNestedScrollingEnabled(false);
        mBinding.homeRecycleView.setLayoutManager(layoutManager);
//        mBinding.homeRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.homeRecycleView.setAdapter(adapter);

        mBinding.llHomeList.showLoading(true, "Loading..");

        DataManager.can().getProductList().observeOn(AndroidSchedulers.mainThread())
                .defaultIfEmpty(new ArrayList<Product>())
                .subscribe(new Consumer<List<Product>>()
                {
                    @Override
                    public void accept (List<Product> products) throws Exception
                    {
                        if (mProduct != null) { mProduct.clear(); }
                        mProduct.addAll(products);
                        mBinding.homeRecycleView.getAdapter().notifyDataSetChanged();
                        mBinding.llHomeList.showLoading(false);
                        if (mProduct.size() == 0)
                        {
                            mBinding.llHomeList.showEmptyView(true);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept (Throwable throwable) throws Exception
                    {
                        RetrofitErrorAdapter error = new RetrofitErrorAdapter(throwable);
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        mBinding.llHomeList.showLoading(false);
                    }
                });

//        if(Hawk.get(K.SUBJECT_LIST) == null){
//            DataManager.can().getSubject().observeOn(AndroidSchedulers.mainThread())
//                    .defaultIfEmpty(new ArrayList<Subject>())
//                    .subscribe(new Consumer<List<Subject>>()
//                    {
//                        @Override
//                        public void accept (List<Subject> subjects) throws Exception
//                        {
//                            if (mSubject != null) { mSubject.clear(); }
//                            mSubject.addAll(subjects);
//                        }
//                    }, new Consumer<Throwable>() {
//                        @Override
//                        public void accept (Throwable throwable) throws Exception
//                        {
//                            RetrofitErrorAdapter error = new RetrofitErrorAdapter(throwable);
//                            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
//                        }
//                    });
//        }

        adapter.setOnClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                if(hawkManager.getAppUserToken() != null) {
                    final Dialog dialog = new Dialog(getContext());
                    dialog.setContentView(R.layout.dialog_study_level);
                    dialog.setTitle(R.string.study_level_hint);

                    MaterialBetterSpinner spinnerStudyLevel = (MaterialBetterSpinner) dialog.findViewById(R.id.spinner_study_level);
                    mStudyLevel =  Hawk.get(K.STUDY_LEVEL_LIST);
                    List<String> listStudyClassSpinner = new ArrayList<String>();
                    List<Integer> listStudyClassSpinnerId = new ArrayList<Integer>();

                    for (int i = 0; i < mStudyLevel.size(); i++){
                        listStudyClassSpinner.add(mStudyLevel.get(i).getName() + ' ' + mStudyLevel.get(i).getDescription());
                        listStudyClassSpinnerId.add(mStudyLevel.get(i).getId());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_dropdown_item_1line, listStudyClassSpinner);
                    spinnerStudyLevel.setAdapter(adapter);

                    MaterialBetterSpinner spinnerOrder = (MaterialBetterSpinner) dialog.findViewById(R.id.spinner_order);
                    List<Integer> listOrderSpinner = new ArrayList<Integer>();
                    for (int i = product.getMinOrder(); i < product.getMaxOrder() + 1; i = i+product.getMultiple()){
                        listOrderSpinner.add(i);
                    }
                    ArrayAdapter<Integer> adapterOrder = new ArrayAdapter<Integer>(getContext(),
                            android.R.layout.simple_dropdown_item_1line, listOrderSpinner);
                    spinnerOrder.setAdapter(adapterOrder);

                    spinnerStudyLevel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            studyLevelId = listStudyClassSpinnerId.get(position);
                        }
                    });

                    spinnerOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            studyOrder = listOrderSpinner.get(position);
                        }
                    });

                    Button submitButton = (Button) dialog.findViewById(R.id.submit_rating);
                    // if button is clicked, submit rating
                    submitButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mBinding.llHomeList.showLoading(true, "Loading..");
                            DataManager.can().getSubject(studyLevelId).observeOn(AndroidSchedulers.mainThread())
                                    .defaultIfEmpty(new ArrayList<Subject>())
                                    .subscribe(new Consumer<List<Subject>>()
                                    {
                                        @Override
                                        public void accept (List<Subject> subjects) throws Exception
                                        {
                                            dialog.dismiss();
                                            mBinding.llHomeList.showLoading(false);
                                            if (mSubject != null) {
                                                mSubject.clear();
                                                mSubject.addAll(subjects);
                                                OrderClassFragment fragment = new OrderClassFragment();
                                                Bundle args = new Bundle();
                                                args.putInt("id", product.getId());
                                                args.putString("name", product.getName());
                                                args.putInt("min_order", product.getMinOrder());
                                                args.putInt("max_order", product.getMaxOrder());
                                                args.putInt("multiple", product.getMultiple());
                                                args.putInt("study_level_id", studyLevelId);
                                                args.putInt("order", studyOrder);
                                                fragment.setArguments(args);
                                                getFragmentManager().beginTransaction().
                                                        replace(R.id.frame, fragment).
                                                        addToBackStack(null).
                                                        commit();
                                            } else {
                                                Toast.makeText(getContext(), "Mata Pelajaran Belum Tersedia", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    }, new Consumer<Throwable>() {
                                        @Override
                                        public void accept (Throwable throwable) throws Exception
                                        {
                                            mBinding.llHomeList.showLoading(false);
                                            dialog.dismiss();
                                            RetrofitErrorAdapter error = new RetrofitErrorAdapter(throwable);
                                            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }
                    });

                    Button cancelButton = (Button) dialog.findViewById(R.id.cancel_rating);
                    // if button is clicked, close the custom dialog
                    cancelButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                } else {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
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
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
