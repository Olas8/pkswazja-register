package com.example.dziennikazja.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.dziennikazja.R;
import com.example.dziennikazja.db.Training;
import com.example.dziennikazja.viewmodel.TrainingViewModel;

import java.util.List;

public class TrainingListAdapter extends RecyclerView.Adapter<TrainingListAdapter.TrainingViewHandler> {

    private static final String TAG = "TrainingListAdapter";
    private OnTrainingListener mOnTrainingListener;
    private TrainingViewModel trainingViewModel;

    public interface OnTrainingListener {
        void onTrainingClicked(int position);
    }

    class TrainingViewHandler extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView tvDayAndTime, tvGroupName;
        private OnTrainingListener onTrainingListener;

        private TrainingViewHandler(View itemView, OnTrainingListener onTrainingListener) {
            super(itemView);
            tvDayAndTime = itemView.findViewById(R.id.textview_training_day);
            tvGroupName = itemView.findViewById(R.id.textview_training_group);

            this.onTrainingListener = onTrainingListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int tId = mTrainings.get(getAdapterPosition()).id;
            onTrainingListener.onTrainingClicked(tId);
        }
    }

    private final LayoutInflater mInflater;
    private List<Training> mTrainings; // Cached copy of words

    public TrainingListAdapter(Context context, OnTrainingListener onTrainingListener, TrainingViewModel trainingViewModel) {
        mInflater = LayoutInflater.from(context);
        mOnTrainingListener = onTrainingListener;
        this.trainingViewModel = trainingViewModel;
    }

    @Override
    public TrainingViewHandler onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_training, parent, false);
        return new TrainingViewHandler(itemView, mOnTrainingListener);
    }

    @Override
    public void onBindViewHolder(TrainingViewHandler holder, int position) {
        if (mTrainings != null) {
            Training training = mTrainings.get(position);
            holder.tvDayAndTime.setText(training.day + " " + training.startTime + " - " + training.endTime);
            holder.tvGroupName.setText(trainingViewModel.getGroupName(training.id));
        } else {
            // Covers the case of data not being ready yet.
            holder.tvDayAndTime.setText("No Word");
        }
    }

    public void setTrainings(List<Training> trainings) {
        mTrainings = trainings;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mTrainings has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mTrainings != null)
            return mTrainings.size();
        else return 0;
    }
}