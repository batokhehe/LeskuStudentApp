package id.co.lesku.views.fragments.transaction;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import id.co.flipbox.sosoito.LoadingLayout;
import id.co.lesku.R;
import id.co.lesku.data.DataManager;
import id.co.lesku.manager.ConfigManager;
import id.co.lesku.model.OrderClass;
import id.co.lesku.model.Subject;
import id.co.lesku.utils.constants.K;
import id.co.lesku.views.activities.order.InvoiceActivity;
import id.co.lesku.views.adapters.transaction.OrderClassAdapter;
import id.co.lesku.views.fragments.BaseFragment;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OrderClassFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OrderClassFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderClassFragment extends BaseFragment {
    private static final String TAG = OrderClassFragment.class.getSimpleName();
    private RecyclerView rvOrder;
    private OrderClassAdapter mOrderClassAdapter;
    ArrayList<OrderClass> orderClassArrayList = new ArrayList<>();

    FloatingActionButton fabAddOrder, fabRemoveOrder;
    Button btnAddOrder, btnRemoveOrder, btnOrderClass;
    private int multiplier, min_order, max_order, productId;
    private FloatingActionButton addAssembly, removeAssembly;

    private OnFragmentInteractionListener mListener;
    private ArrayList<String> listSubjectSpinner;
    private ArrayList<Integer> listSubjectSpinnerId;
    private List<Subject> mSubject;
    private LoadingLayout llOrderClass;
    private View view;

    public OrderClassFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderClassFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderClassFragment newInstance(String param1, String param2) {
        OrderClassFragment fragment = new OrderClassFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public void initUI() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        productId = getArguments().getInt("id");
        multiplier = getArguments().getInt("multiple");
        min_order = getArguments().getInt("min_order");
        max_order = getArguments().getInt("max_order");

        //Subject
        listSubjectSpinner = new ArrayList<String>();
        listSubjectSpinnerId = new ArrayList<Integer>();
        mSubject = Hawk.get(K.SUBJECT_LIST);
        for (int i = 0; i < mSubject.size(); i++){
            listSubjectSpinner.add(mSubject.get(i).getName());
            listSubjectSpinnerId.add(mSubject.get(i).getId());
        }

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_order_class, container, false);
        this.view = rootView;

        mOrderClassAdapter = new OrderClassAdapter(getContext(), orderClassArrayList);
        mOrderClassAdapter.setMaxOrder(max_order);

        rvOrder = (RecyclerView) rootView.findViewById(R.id.rv_order);
        fabAddOrder = (FloatingActionButton) rootView.findViewById(R.id.fab_add_assembly);
        fabRemoveOrder = (FloatingActionButton) rootView.findViewById(R.id.fab_remove_assembly);
        fabRemoveOrder.hide();

        btnOrderClass = (Button) rootView.findViewById(R.id.btn_order_class);

        fabAddOrder.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v) {
                if(mOrderClassAdapter.getItemCount() < max_order){
                    addOrder(multiplier);
                }
                if(mOrderClassAdapter.getItemCount() > (max_order - multiplier)) {
                    fabAddOrder.hide();
                }

                if(mOrderClassAdapter.getItemCount() > min_order){
                    fabRemoveOrder.show();
                }
            }
        });

        fabRemoveOrder.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v) {
                if(mOrderClassAdapter.getItemCount() > min_order){
                    removeOrder(multiplier);
                }
                if(mOrderClassAdapter.getItemCount() < (min_order + multiplier)) {
                    fabRemoveOrder.hide();
                }
                if(mOrderClassAdapter.getItemCount() < max_order){
                    fabAddOrder.show();
                }
            }
        });

        btnOrderClass.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v) {
                orderClass();
            }
        });
        rvOrder.setHasFixedSize(true);
        rvOrder.setLayoutManager(new LinearLayoutManager(getContext()));
        rvOrder.setAdapter(mOrderClassAdapter);

        addOrder(multiplier);

        return rootView;
    }

    private void addOrder(int multiplier) {
        Toast.makeText(getContext(), "Added Item", Toast.LENGTH_SHORT).show();
        OrderClass orderClass = new OrderClass();
        for (int i = 0; i < multiplier; i++) {
            orderClass.setId(0);
            orderClass.setName("");
            orderClass.setSubject(listSubjectSpinner.get(0));
            orderClass.setImage("blank_photo.png");
            orderClass.setSchedule(null);
            orderClassArrayList.add(orderClass);
        }
        mOrderClassAdapter.notifyDataSetChanged();
    }

    private void removeOrder(int multiplier) {
        int size = orderClassArrayList.size();
        if(size > 0) {
            int initialize = size - 1;
            int limit = initialize - multiplier;
            for (int i = initialize; i > limit; i--) {
                Log.d(TAG, "Remove Item ~ i = " + i);
                orderClassArrayList.remove(i);
                mOrderClassAdapter.notifyItemRemoved(i);
                mOrderClassAdapter.notifyItemRangeChanged(i, orderClassArrayList.size());
            }
        } else {
            Toast.makeText(getContext(), "Cannot Exceed The Limit", Toast.LENGTH_SHORT).show();
        }
        
    }

    private void orderClass() {
        JsonObject obj = new JsonObject();
        JsonArray jOuter = new JsonArray();

        for (int i = 0; i < orderClassArrayList.size(); i++) {
            JsonObject jGroup = new JsonObject();
            OrderClass orderClass = new OrderClass();
            orderClass = orderClassArrayList.get(i);
            jGroup.addProperty("teacherId", orderClass.getTeacherId());
            jGroup.addProperty("subject", orderClass.getSubjectId());
            jGroup.addProperty("selectedSchedule", orderClass.getSelectedSchedule());
            jOuter.add(jGroup);
        }
        obj.addProperty("productId", productId);
        obj.add("orderedClass", jOuter);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(obj).toString());

        Toast.makeText(getContext(), "Ordered Class : " + obj, Toast.LENGTH_SHORT).show();

        DataManager.can().addOrderClass(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>()
                {
                    @Override
                    public void accept (ResponseBody object) throws Exception
                    {
                        Toast.makeText(getContext(), "Good", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), InvoiceActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept (Throwable throwable) throws Exception
                    {
                        Toast.makeText(getContext(), "Bad", Toast.LENGTH_SHORT).show();
                    }
                });
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == ConfigManager.REQUEST_CODE_TEACHER && data != null) {
            String image = data.getStringExtra("image");
            String subject = data.getStringExtra("subject");
            int subjectId = data.getIntExtra("subjectId", 0);
            int teacherId = data.getIntExtra("teacherId", 0);
            int position = data.getIntExtra("position", 0);
            ArrayList<String> schedule = data.getStringArrayListExtra("schedule");
            String selectedSchedule = data.getStringExtra("selectedSchedule");
            mOrderClassAdapter.changeImage(image, subject, subjectId, position, teacherId, schedule);
        }
    }
}
