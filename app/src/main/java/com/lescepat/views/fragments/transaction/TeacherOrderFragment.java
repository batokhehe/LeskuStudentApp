package com.lescepat.views.fragments.transaction;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.lescepat.R;
import com.lescepat.data.DataManager;
import com.lescepat.databinding.FragmentTeacherOrderBinding;
import com.lescepat.manager.ConfigManager;
import com.lescepat.model.TeacherOrder;
import com.lescepat.utils.RetrofitErrorAdapter;
import com.lescepat.viewmodels.TeacherOrderListViewModel;
import com.lescepat.views.adapters.transaction.TeacherOrderAdapter;
import com.lescepat.views.fragments.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class TeacherOrderFragment extends BaseFragment {

    FragmentTeacherOrderBinding mBinding;
    List<TeacherOrder> mTeacherOrder;
    private OnFragmentInteractionListener mListener;
    TeacherOrderAdapter adapter;
    private int position, subjectId, studyLevel;
    private String subject, schedule;

    public TeacherOrderFragment() {
        // Required empty public constructor
        setArguments(new Bundle());
        mTeacherOrder = new ArrayList<>();
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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_teacher_order, container, false);
        mBinding.setTeacherOrders(new TeacherOrderListViewModel());

        adapter = new TeacherOrderAdapter(mTeacherOrder, getContext());

        mBinding.rvTeacherOrder.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.rvTeacherOrder.setAdapter(adapter);

        subject = getArguments().getString("subject");
        subjectId = getArguments().getInt("subjectId", 0);
        position = getArguments().getInt("position");
        studyLevel = getArguments().getInt("studyLevel");
        schedule = getArguments().getString("schedule");

        Toast.makeText(getContext(), "Subject : " + subject + " Subject ID : " + subjectId + " Position : " + position + " Schedule : " + schedule, Toast.LENGTH_SHORT).show();

        mBinding.llTeacherOrder.showLoading(true, "Loading Teacher List...");

        DataManager.can().getTeacherOrderList(subjectId, studyLevel, schedule).observeOn(AndroidSchedulers.mainThread())
                .defaultIfEmpty(new ArrayList<TeacherOrder>())
                .subscribe(new Consumer<List<TeacherOrder>>()
                {
                    @Override
                    public void accept (List<TeacherOrder> teachers) throws Exception
                    {
                        if (mTeacherOrder != null) { mTeacherOrder.clear(); }
                        mTeacherOrder.addAll(teachers);
                        mBinding.rvTeacherOrder.getAdapter().notifyDataSetChanged();
                        mBinding.llTeacherOrder.showLoading(false);
                        if (mTeacherOrder.size() == 0)
                        {
                            mBinding.llTeacherOrder.showEmptyView(true);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept (Throwable throwable) throws Exception
                    {
                        RetrofitErrorAdapter error = new RetrofitErrorAdapter(throwable);
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        mBinding.llTeacherOrder.showLoading(false);
                    }
                });

        adapter.setOnClickListener(new TeacherOrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TeacherOrder teacherOrder) {
                Intent intent = new Intent();
                intent.putExtra("image", teacherOrder.getImage());
                intent.putExtra("teacherId", teacherOrder.getId());
                intent.putExtra("position", position);
                intent.putExtra("subject", subject);
                intent.putExtra("subjectId", subjectId);
                intent.putExtra("schedule", schedule);
//                intent.putExtra("date", date);
//                intent.putExtra("schedule", teacherOrder.getSchedule());
                getActivity().setResult(ConfigManager.REQUEST_CODE_TEACHER, intent);
                getActivity().finish();
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
