package id.co.lesku.views.adapters.schedule;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.List;

import id.co.lesku.R;
import id.co.lesku.data.DataManager;
import id.co.lesku.databinding.RvItemUpcomingScheduleBinding;
import id.co.lesku.model.UpcomingSchedule;
import id.co.lesku.utils.RetrofitErrorAdapter;
import id.co.lesku.viewmodels.UpcomingScheduleViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class UpcomingScheduleAdapter extends RecyclerView.Adapter<UpcomingScheduleAdapter.ListViewHolder>  {
    private RvItemUpcomingScheduleBinding upcomingItemBinding;
    private List<UpcomingSchedule> mUpcomingSchedule;
    private Context mContext;
    private OnItemClickListener listener;
    private Button btnConfirmSchedule, btnRescheduleSchedule;

    public UpcomingScheduleAdapter(List<UpcomingSchedule> upcomingSchedules, Context context) {
        mUpcomingSchedule = upcomingSchedules;
        mContext = context;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        upcomingItemBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.rv_item_upcoming_schedule, parent, false);

        UpcomingScheduleAdapter.ListViewHolder vh = new UpcomingScheduleAdapter.ListViewHolder(upcomingItemBinding.getRoot());
        vh.setBinding(upcomingItemBinding);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingScheduleAdapter.ListViewHolder holder, int position) {
        holder.setSchedule(mUpcomingSchedule.get(position));
    }

    @Override
    public int getItemCount() {
        return (mUpcomingSchedule == null) ? 0 : mUpcomingSchedule.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        public ListViewHolder(View itemView) {
            super(itemView);

            btnConfirmSchedule = (Button) itemView.findViewById(R.id.btn_confirm_Schedule);
            btnConfirmSchedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = getAdapterPosition();
                    final UpcomingSchedule upcomingSchedule = mUpcomingSchedule.get(position);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(
                            mContext);
                    builder.setMessage("Confirm Schedule?")
                            .setCancelable(false)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        //do something
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            confirmSchedule(upcomingSchedule.getId(), position);
                                        }
                                    })
                            .setNegativeButton("No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            dialog.cancel();
                                        }
                                    });
                    final AlertDialog alert = builder.create();
                    alert.show();
                }
            });

            btnRescheduleSchedule = (Button) itemView.findViewById(R.id.btn_reschedule_Schedule);
            btnRescheduleSchedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = getAdapterPosition();
                    final UpcomingSchedule upcomingSchedule = mUpcomingSchedule.get(position);
//                    Toast.makeText(mContext, "" + UpcomingSchedule.getId(), Toast.LENGTH_SHORT).show();
                    final AlertDialog.Builder builder = new AlertDialog.Builder(
                            mContext);
                    builder.setMessage("Re-Schedule?")
                            .setCancelable(false)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        //do something
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            reSchedule(upcomingSchedule.getId(), position);
                                        }
                                    })
                            .setNegativeButton("No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            dialog.cancel();
                                        }
                                    });
                    final AlertDialog alert = builder.create();
                    alert.show();
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(mUpcomingSchedule.get(position));
                    }
                }
            });
        }

        void setBinding (RvItemUpcomingScheduleBinding binding)
        {
            upcomingItemBinding = binding;
        }

        public void setSchedule (UpcomingSchedule UpcomingSchedules)
        {
            if (upcomingItemBinding.getUpcomingSchedule() == null)
            {
                upcomingItemBinding.setUpcomingSchedule(new UpcomingScheduleViewModel(UpcomingSchedules));
            }
            else
            {
                upcomingItemBinding.getUpcomingSchedule().setSchedule(UpcomingSchedules);
            }

        }
    }

    public interface OnItemClickListener{
        void onItemClick(UpcomingSchedule UpcomingSchedules);
    }

    public void setOnClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public void confirmSchedule(int id, final int position) {
        DataManager.can().confirmSchedule(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JsonObject>()
                {
                    @Override
                    public void accept (JsonObject object) throws Exception
                    {
                        Toast.makeText(mContext, "Schedule Accepted", Toast.LENGTH_SHORT).show();
                        // Reload current fragment
                        removeAt(position);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept (Throwable throwable) throws Exception
                    {
                        RetrofitErrorAdapter error = new RetrofitErrorAdapter(throwable);
                        Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void reSchedule(int id, final int position) {
        DataManager.can().reSchedule(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JsonObject>()
                {
                    @Override
                    public void accept (JsonObject object) throws Exception
                    {
                        Toast.makeText(mContext, "Schedule Decline", Toast.LENGTH_SHORT).show();
                        // Reload current fragment
                        removeAt(position);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept (Throwable throwable) throws Exception
                    {
                        RetrofitErrorAdapter error = new RetrofitErrorAdapter(throwable);
                        Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void removeAt(int position) {
        mUpcomingSchedule.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mUpcomingSchedule.size());
    }
}
