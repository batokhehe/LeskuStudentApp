package id.co.lesku.views.adapters.transaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.orhanobut.hawk.Hawk;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

import id.co.lesku.R;
import id.co.lesku.manager.ConfigManager;
import id.co.lesku.model.OrderClass;
import id.co.lesku.model.Subject;
import id.co.lesku.utils.constants.K;
import id.co.lesku.views.activities.order.TeacherOrderActivity;

public class OrderClassAdapter extends RecyclerView.Adapter<OrderClassAdapter.OrderViewHolder> {

    private ArrayList<String> listSubjectSpinner;
    private ArrayList<Integer> listSubjectSpinnerId;
    private List<Subject> mSubject;
    public ArrayList<OrderClass> mOrderClass;
    private Context mContext;
    private ArrayList<String> subject = new ArrayList<String>();
    private ArrayList<Integer> subjectId = new ArrayList<Integer>();
    private ArrayList<String> schedule = new ArrayList<String>();
    private int maxOrder = 0;

    public OrderClassAdapter(Context context, ArrayList<OrderClass> orderClass) {
        mOrderClass = orderClass;
        mContext = context;
    }

    @NonNull
    @Override
    public OrderClassAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_order_class, parent, false);
        OrderViewHolder vh = new OrderViewHolder(view);
        return vh;
    }

    @Override
    public int getItemCount() {
        return mOrderClass.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView ivTeacher;
        public Button btnDatePicker;
        public TextView tvDate;
        public int position;
        public MaterialBetterSpinner sSubject;
        public MaterialBetterSpinner sSchedule;

        public OrderViewHolder(View itemView) {
            super(itemView);

            sSubject = (MaterialBetterSpinner) itemView.findViewById(R.id.sSubject);
            sSchedule = (MaterialBetterSpinner) itemView.findViewById(R.id.sSchedule);
            ivTeacher = (ImageView) itemView.findViewById(R.id.ivTeacher);
            ivTeacher.setOnClickListener(this);

            listSubjectSpinner = new ArrayList<String>(maxOrder);
            listSubjectSpinnerId = new ArrayList<Integer>(maxOrder);
            mSubject = Hawk.get(K.SUBJECT_LIST);
            for (int i = 0; i < mSubject.size(); i++){
                listSubjectSpinner.add(mSubject.get(i).getName());
                listSubjectSpinnerId.add(mSubject.get(i).getId());
            }
            ArrayAdapter<String> subjectAdapter = new ArrayAdapter<String>(itemView.getContext(),
                    android.R.layout.simple_dropdown_item_1line, listSubjectSpinner);
            sSubject.setAdapter(subjectAdapter);

            sSubject.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int itemposition, long id) {
                    position = getAdapterPosition();
                    Toast.makeText(itemView.getContext(), "Position : " + position + " ~ Item : " + itemposition + " " + listSubjectSpinner.get(itemposition) + " Id : " + listSubjectSpinnerId.get(itemposition), Toast.LENGTH_SHORT).show();

                    subject.add(position, listSubjectSpinner.get(itemposition));
                    subjectId.add(position, listSubjectSpinnerId.get(itemposition));
                }
            });

            sSchedule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int itemposition, long id) {
                    position = getAdapterPosition();
                    String selectedSchedule= parent.getItemAtPosition(position).toString();
//                    schedule.set(position, selectedSchedule);
                    OrderClass orderClass = mOrderClass.get(position);
                    orderClass.setSelectedSchedule(selectedSchedule);
                    mOrderClass.set(position, orderClass);
                    Toast.makeText(itemView.getContext(), "Update After Selected Schedule : " + selectedSchedule, Toast.LENGTH_SHORT).show();
                    OrderClass newOrderClass = mOrderClass.get(position);
                    Log.d("Schedule", "Subject: " + orderClass.getSubjectId());
                    Log.d("Schedule", "Teacher: " + orderClass.getTeacherId());
                    Log.d("Schedule", "Schedule: " + orderClass.getSelectedSchedule());
                }
            });
        }

        @Override
        public void onClick(View v) {
            position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION) {
//                String date = tvDate.getText().toString();
                String selectedSubject = subject.get(position);
                int selectedSubjectId = subjectId.get(position);

                Intent intent = new Intent(mContext, TeacherOrderActivity.class);
                intent.putExtra("subject", selectedSubject);
                intent.putExtra("subjectId", selectedSubjectId);
                intent.putExtra("position", position);
                ((Activity) mContext).startActivityForResult(intent, ConfigManager.REQUEST_CODE_TEACHER);
//                Toast.makeText(mContext, "Subject : " + selectedSubject + " Position : " + position + " Date : " + date, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        OrderClass orderClass = mOrderClass.get(position);
        ImageView iv = holder.ivTeacher;
        Glide.with(mContext)
        .load(ConfigManager.BASE_URL_IMAGE + "teacher_profile/" + orderClass.getImage())
        .apply(RequestOptions.circleCropTransform())
        .into(iv);
        holder.sSubject.setText(orderClass.getSubject());
        ArrayAdapter<String> scheduleAdapter = new ArrayAdapter<String>(mContext,
                android.R.layout.simple_dropdown_item_1line, orderClass.getSchedule());
        holder.sSchedule.setAdapter(scheduleAdapter);
        if(orderClass.getSelectedSchedule() != null){
            holder.sSchedule.setText(orderClass.getSelectedSchedule());
        }
    }

    public void changeImage(String url, String subject, int subjectId, int position, int teacherId, ArrayList<String> schedule){
        OrderClass orderClass = new OrderClass();
        orderClass.setImage(url);
        orderClass.setTeacherId(teacherId);
        orderClass.setSubject(subject);
        orderClass.setSubjectId(subjectId);
        orderClass.setSchedule(schedule);
        mOrderClass.set(position, orderClass);
        this.notifyItemChanged(position);
    }

    public ArrayList<OrderClass> getArrayList(){
        return mOrderClass;
    }

    public void setMaxOrder(int maxOrder){
        this.maxOrder = maxOrder;
        for (int i=0; i<maxOrder;i++){
            subject.add(i, "");
            subjectId.add(i, 0);
            schedule.add(i, "");
        }
    }
}
