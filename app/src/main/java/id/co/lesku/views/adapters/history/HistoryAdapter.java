package id.co.lesku.views.adapters.history;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.List;

import id.co.lesku.R;
import id.co.lesku.data.DataManager;
import id.co.lesku.databinding.RvItemHistoryBinding;
import id.co.lesku.model.History;
import id.co.lesku.utils.RetrofitErrorAdapter;
import id.co.lesku.viewmodels.HistoryViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ListViewHolder>  {
    private RvItemHistoryBinding historyItemBinding;
    private List<History> mHistory;
    private Context mContext;
    private OnItemClickListener listener;

    public HistoryAdapter(List<History> Historys, Context context) {
        mHistory = Historys;
        mContext = context;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        historyItemBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.rv_item_history, parent, false);

        HistoryAdapter.ListViewHolder vh = new HistoryAdapter.ListViewHolder(historyItemBinding.getRoot());
        vh.setBinding(historyItemBinding);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ListViewHolder holder, int position) {
        holder.setSchedule(mHistory.get(position));
        History history = mHistory.get(position);
        if(history.getRating() != null && !TextUtils.isEmpty(history.getRating())){
            holder.linearRating.setVisibility(View.VISIBLE);
            holder.btnRating.setVisibility(View.GONE);
            holder.tvRating.setText(history.getRating());
        } else {
            holder.linearRating.setVisibility(View.GONE);
            holder.btnRating.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return (mHistory == null) ? 0 : mHistory.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearRating;
        public Button btnRating;
        public TextView tvRating;

        public ListViewHolder(View itemView) {
            super(itemView);

            linearRating = (LinearLayout) itemView.findViewById(R.id.ll_rating);
            tvRating = (TextView) itemView.findViewById(R.id.tv_rating);
            btnRating = (Button) itemView.findViewById(R.id.btn_rating);
            btnRating.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = getAdapterPosition();
                    final History history = mHistory.get(position);
                    final Dialog dialog = new Dialog(mContext);
                    dialog.setContentView(R.layout.dialog_rating);
                    dialog.setTitle(R.string.rating);

                    RatingBar ratingBar = (RatingBar) dialog.findViewById(R.id.rb_rating_teacher);
                    ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                        @Override
                        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                        }
                    });

                    Button submitButton = (Button) dialog.findViewById(R.id.submit_rating);
                    // if button is clicked, submit rating
                    submitButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EditText etComment = (EditText) dialog.findViewById(R.id.et_comment);
                            String comment = etComment.getText().toString();
                            rating(history.getId(), ratingBar.getRating(), comment, dialog, position);
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
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(mHistory.get(position));
                    }
                }
            });
        }

        void setBinding (RvItemHistoryBinding binding)
        {
            historyItemBinding = binding;
        }

        public void setSchedule (History Historys)
        {
            if (historyItemBinding.getHistory() == null)
            {
                historyItemBinding.setHistory(new HistoryViewModel(Historys));
            }
            else
            {
                historyItemBinding.getHistory().setHistory(Historys);
            }

        }
    }

    public interface OnItemClickListener{
        void onItemClick(History Historys);
    }

    public void setOnClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public void rating(int id, float rating, String comment, Dialog dialog, final int position) {
        DataManager.can().rating(id, rating, comment)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JsonObject>()
                {
                    @Override
                    public void accept (JsonObject object) throws Exception
                    {
                        dialog.dismiss();
                        Toast.makeText(mContext, "Submitted", Toast.LENGTH_SHORT).show();
                        final AlertDialog.Builder builder = new AlertDialog.Builder(
                                mContext);
                        builder.setMessage("Rating Submitted, Thanks.")
                                .setCancelable(false)
                                .setPositiveButton("Ok",
                                        new DialogInterface.OnClickListener() {
                                            //do something
                                            public void onClick(DialogInterface dialog,
                                                                int id) {
                                                dialog.cancel();
                                            }
                                        });
                        final AlertDialog alert = builder.create();
                        alert.show();
                        removeAt(position, Math.round(rating), id);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept (Throwable throwable) throws Exception
                    {
                        dialog.dismiss();
                        RetrofitErrorAdapter error = new RetrofitErrorAdapter(throwable);
                        Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void removeAt(int position, int rating, int id) {
        History history = new History();
        history.setRating(String.valueOf(rating));
        history.setId(id);

        mHistory.set(position, history);
//        notifyItemRemoved(position);
        notifyItemChanged(position, mHistory.size());
    }
}
