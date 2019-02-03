package id.co.lesku.views.adapters.transaction;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.orhanobut.hawk.Hawk;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import id.co.lesku.R;
import id.co.lesku.manager.ConfigManager;
import id.co.lesku.models.OrderClass;
import id.co.lesku.models.Subject;
import id.co.lesku.utils.constants.K;
import id.co.lesku.views.activities.orders.TeacherOrderActivity;

public class OrderClassAdapter extends RecyclerView.Adapter<OrderClassAdapter.OrderViewHolder> {

    private ArrayList<String> listSubjectSpinner;
    private ArrayList<Integer> listSubjectSpinnerId;
    private List<Subject> mSubject;
    public ArrayList<OrderClass> mOrderClass;
    private Context mContext;
    private String dateSet;
    private ArrayList<String> subject = new ArrayList<String>();
    private ArrayList<Integer> subjectId = new ArrayList<Integer>();
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

        public OrderViewHolder(View itemView) {
            super(itemView);

            sSubject = (MaterialBetterSpinner) itemView.findViewById(R.id.sSubject);
            ivTeacher = (ImageView) itemView.findViewById(R.id.ivTeacher);
            btnDatePicker = (Button) itemView.findViewById(R.id.btnOrderDatePicker);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            ivTeacher.setOnClickListener(this);

            listSubjectSpinner = new ArrayList<String>(maxOrder);
            listSubjectSpinnerId = new ArrayList<Integer>(maxOrder);
            mSubject = Hawk.get(K.SUBJECT_LIST);
            for (int i = 0; i < mSubject.size(); i++){
                listSubjectSpinner.add(mSubject.get(i).getName());
                listSubjectSpinnerId.add(mSubject.get(i).getId());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(itemView.getContext(),
                    android.R.layout.simple_dropdown_item_1line, listSubjectSpinner);
//                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sSubject.setAdapter(adapter);

            btnDatePicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    position = getAdapterPosition();
                    showDatePicker(mOrderClass.get(getAdapterPosition()), position);
                }
            });

            sSubject.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int itemposition, long id) {
//                Log.d(TAG," selected spinner "+ position);
//                Toast.makeText(getContext(), "" + position, Toast.LENGTH_SHORT).show();
                    position = getAdapterPosition();
                    Toast.makeText(itemView.getContext(), "Position : " + position + " ~ Item : " + itemposition + " " + listSubjectSpinner.get(itemposition) + " Id : " + listSubjectSpinnerId.get(itemposition), Toast.LENGTH_SHORT).show();

                    subject.add(position, listSubjectSpinner.get(itemposition));
                    subjectId.add(position, listSubjectSpinnerId.get(itemposition));
                }
            });
        }

        @Override
        public void onClick(View v) {
            position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION) {
                String date = tvDate.getText().toString();
                String selectedSubject = subject.get(position);
                int selectedSubjectId = subjectId.get(position);

                Intent intent = new Intent(mContext, TeacherOrderActivity.class);
                intent.putExtra("subject", selectedSubject);
                intent.putExtra("subjectId", selectedSubjectId);
                intent.putExtra("position", position);
                intent.putExtra("date", date);
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
        holder.tvDate.setText(orderClass.getDate());
        holder.sSubject.setText(orderClass.getSubject());
    }

    public void changeImage(String url, String subject, int subjectId, String date, int position, int teacherId){
        OrderClass orderClass = new OrderClass();
        orderClass.setImage(url);
        orderClass.setTeacherId(teacherId);
        orderClass.setSubject(subject);
        orderClass.setSubjectId(subjectId);
//        sSubject.setText(subject);
        orderClass.setDate(date);
        mOrderClass.set(position, orderClass);
        this.notifyItemChanged(position);
    }

    private void showDatePicker(OrderClass orderClass, int position) {
        Toast.makeText(mContext, "OrderClass " + orderClass, Toast.LENGTH_SHORT).show();
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog formDatePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String tempNewDate = newDate.get(Calendar.DATE) + "/" + (newDate.get(Calendar.MONTH) + 1) + "/" + newDate.get(Calendar.YEAR);
                orderClass.setDate(tempNewDate);
                notifyItemChanged(position);
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        formDatePickerDialog.show();
    }

    public ArrayList<OrderClass> getArrayList(){
        return mOrderClass;
    }

    public void setMaxOrder(int maxOrder){
        this.maxOrder = maxOrder;
        for (int i=0; i<maxOrder;i++){
            subject.add(i, "");
            subjectId.add(i, 0);
        }
    }
}
