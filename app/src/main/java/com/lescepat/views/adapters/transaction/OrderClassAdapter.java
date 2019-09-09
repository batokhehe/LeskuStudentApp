package com.lescepat.views.adapters.transaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lescepat.R;
import com.lescepat.manager.ConfigManager;
import com.lescepat.model.OrderClass;
import com.lescepat.model.Subject;
import com.lescepat.utils.constants.K;
import com.lescepat.views.activities.order.TeacherOrderActivity;
import com.orhanobut.hawk.Hawk;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class OrderClassAdapter extends RecyclerView.Adapter<OrderClassAdapter.OrderViewHolder> {

    private ArrayList<String> listSubjectSpinner;
    private ArrayList<Integer> listSubjectSpinnerId;
    private List<Subject> mSubject;
    public ArrayList<OrderClass> mOrderClass;
    private Context mContext;
    private Activity mActivity;
    private ArrayList<String> subject = new ArrayList<String>();
    private ArrayList<Integer> subjectId = new ArrayList<Integer>();
    private ArrayList<String> schedule = new ArrayList<String>();
    private ArrayList<String> scheduleShow = new ArrayList<String>();
    private int maxOrder = 0, mStudyLevel;
    private int lastPosition = 0;

    public OrderClassAdapter(Context context, Activity activity, ArrayList<OrderClass> orderClass, int studyLevelId) {
        mOrderClass = orderClass;
        mContext = context;
        mActivity = activity;
        mStudyLevel = studyLevelId;
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
        public TextView tvDate, tvDateHidden;
        public int position;
        public MaterialBetterSpinner sSubject;
//        public MaterialBetterSpinner sSchedule;
        public LinearLayout lSchedule;
        public View dialogView;
        public AlertDialog alertDialog;

        public OrderViewHolder(View itemView) {
            super(itemView);

            dialogView = itemView.inflate(mActivity, R.layout.date_time_picker, null);
            alertDialog = new AlertDialog.Builder(mActivity).create();

            sSubject = (MaterialBetterSpinner) itemView.findViewById(R.id.sSubject);
//            sSchedule = (MaterialBetterSpinner) itemView.findViewById(R.id.sSchedule);
            btnDatePicker = itemView.findViewById(R.id.btn_date_picker);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvDateHidden = itemView.findViewById(R.id.tv_date_hidden);
            lSchedule = (LinearLayout) itemView.findViewById(R.id.linear_schedule);
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

                    subject.set(position, listSubjectSpinner.get(itemposition));
                    subjectId.set(position, listSubjectSpinnerId.get(itemposition));
                }
            });

//            sSchedule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int itemposition, long id) {
//                    position = getAdapterPosition();
//                    String selectedSchedule= parent.getItemAtPosition(itemposition).toString();
////                    schedule.set(position, selectedSchedule);
//                    OrderClass orderClass = mOrderClass.get(position);
//                    orderClass.setSelectedSchedule(selectedSchedule);
//                    mOrderClass.set(position, orderClass);
//                    Toast.makeText(itemView.getContext(), "Update After Selected Schedule : " + selectedSchedule, Toast.LENGTH_SHORT).show();
//                    OrderClass newOrderClass = mOrderClass.get(position);
//                    Log.d("Schedule", "Subject: " + orderClass.getSubjectId());
//                    Log.d("Schedule", "Teacher: " + orderClass.getTeacherId());
//                    Log.d("Schedule", "Schedule: " + orderClass.getSelectedSchedule());
//                }
//            });



            dialogView.findViewById(R.id.date_time_set).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
                    TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);

//                    timePicker.setIs24HourView(true);
//                    timePicker.setCurrentHour(8);
//                    timePicker.setCurrentMinute(0);

                    Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                            datePicker.getMonth(),
                            datePicker.getDayOfMonth(),
                            timePicker.getCurrentHour(),
                            timePicker.getCurrentMinute());

                    SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",
                            Locale.ENGLISH);
                    Date parsedDate = null;
                    try {
                        parsedDate = sdf.parse(calendar.getTime().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    SimpleDateFormat simpleDateFormat  = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Log.d("UNIX", "onClick: " + calendar.getTimeInMillis() + " " + simpleDateFormat.format(parsedDate));
                    position = getAdapterPosition();
                    tvDate.setText(String.valueOf(calendar.getTime()));
                    scheduleShow.set(position, String.valueOf(calendar.getTime()));
                    tvDateHidden.setText(String.valueOf(calendar.getTimeInMillis()));
                    schedule.set(position, String.valueOf(calendar.getTimeInMillis()));
                    alertDialog.dismiss();
                    lastPosition = getAdapterPosition();
                }});

            btnDatePicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.setView(dialogView);
                    alertDialog.show();
                }
            });
        }

        @Override
        public void onClick(View v) {
            position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION) {
//                String date = tvDate.getText().toString();
                String selectedSubject = subject.get(position);
                if(selectedSubject.isEmpty()){
                    selectedSubject = listSubjectSpinner.get(0);
                }
                int selectedSubjectId = subjectId.get(position);
                String selectedSchedule = schedule.get(position);

                Intent intent = new Intent(mContext, TeacherOrderActivity.class);
                intent.putExtra("subject", selectedSubject);
                intent.putExtra("subjectId", selectedSubjectId);
                intent.putExtra("studyLevel", mStudyLevel);
                intent.putExtra("position", position);
                intent.putExtra("schedule", selectedSchedule);
                ((Activity) mContext).startActivityForResult(intent, ConfigManager.REQUEST_CODE_TEACHER);
                Log.d("Adapter", "Intent: " + "Subject : " + selectedSubject + " Position : " + position + " schedule : " + selectedSchedule);
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
        holder.tvDateHidden.setText(orderClass.getSchedule());
        holder.tvDate.setText(scheduleShow.get(position));
//        ArrayAdapter<String> scheduleAdapter = new ArrayAdapter<String>(mContext,
//                android.R.layout.simple_dropdown_item_1line, orderClass.getSchedule());
//        holder.sSchedule.setAdapter(scheduleAdapter);
//        if(orderClass.getSchedule() != null){
//            holder.lSchedule.setVisibility(View.VISIBLE);
//        }
//        if(orderClass.getSelectedSchedule() != null){
//            holder.sSchedule.setText(orderClass.getSelectedSchedule());
//        }
    }

    public void changeImage(String url, String subject, int subjectId, int teacherId, String schedule, int position){
        OrderClass orderClass = new OrderClass();
        orderClass.setImage(url);
        orderClass.setTeacherId(teacherId);
        orderClass.setSubject(subject);
        orderClass.setSubjectId(subjectId);
        orderClass.setSchedule(schedule);
        mOrderClass.set(position, orderClass);
        this.notifyItemChanged(position);
        OrderClass orderClass1 = mOrderClass.get(position);
        Log.d("Adapter", "Change Image: " + "Subject : " + orderClass1.getSubject() + " Position : " + position + " schedule : " + orderClass1.getSchedule());
    }

    public ArrayList<OrderClass> getArrayList(){
        return mOrderClass;
    }

    public void setMaxOrder(int maxOrder){
        this.maxOrder = maxOrder;
        for (int i=0; i<maxOrder;i++){
            subject.add(i, "");
            subjectId.add(i, 1);
            schedule.add(i, "");
            scheduleShow.add(i, "");
        }
    }
}
