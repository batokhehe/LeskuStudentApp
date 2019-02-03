package id.co.lesku.views.fragments.transaction;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.co.lesku.R;
import id.co.lesku.data.DataManager;
import id.co.lesku.databinding.FragmentTeacherOrderBinding;
import id.co.lesku.manager.ConfigManager;
import id.co.lesku.models.TeacherOrder;
import id.co.lesku.utils.RetrofitErrorAdapter;
import id.co.lesku.viewmodels.TeacherOrderListViewModel;
import id.co.lesku.views.adapters.transaction.TeacherOrderAdapter;
import id.co.lesku.views.fragments.BaseFragment;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class TeacherOrderFragment extends BaseFragment {

    FragmentTeacherOrderBinding mBinding;
    List<TeacherOrder> mTeacherOrder;
    private OnFragmentInteractionListener mListener;
    TeacherOrderAdapter adapter;
    private int position, subjectId;
    private String subject, date;

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
        date = getArguments().getString("date");

        Toast.makeText(getContext(), "Subject : " + subject + " Subject ID : " + subjectId + " Position : " + position + " Date : " + date, Toast.LENGTH_SHORT).show();

        mBinding.llTeacherOrder.showCustomLoading(true, "Loading Teacher List...");

        DataManager.can().getTeacherOrderList().observeOn(AndroidSchedulers.mainThread())
                .defaultIfEmpty(new ArrayList<TeacherOrder>())
                .subscribe(new Consumer<List<TeacherOrder>>()
                {
                    @Override
                    public void accept (List<TeacherOrder> teachers) throws Exception
                    {
                        if (mTeacherOrder != null) { mTeacherOrder.clear(); }
                        mTeacherOrder.addAll(teachers);
                        mBinding.rvTeacherOrder.getAdapter().notifyDataSetChanged();
                        mBinding.llTeacherOrder.showCustomLoading(false);
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
                        mBinding.llTeacherOrder.showCustomLoading(false);
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
                intent.putExtra("date", date);
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
