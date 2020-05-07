package com.example.dziennikazja.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.dziennikazja.R;
import com.example.dziennikazja.db.TrainingSchedule;
import com.example.dziennikazja.viewmodel.TrainingScheduleViewModel;

import java.util.List;

public class TrainingScheduleListAdapter extends RecyclerView.Adapter<TrainingScheduleListAdapter.TrainingScheduleViewHandler> {

    private static final String TAG = "TrainingListAdapter";
    private OnTrainingListener mOnTrainingListener;
    private TrainingScheduleViewModel trainingViewModel;

    public interface OnTrainingListener {
        void onTrainingClicked(int position, int groupId);
    }

    class TrainingScheduleViewHandler extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView tvDayAndTime, tvGroupName;
        private OnTrainingListener onTrainingListener;

        private TrainingScheduleViewHandler(View itemView, OnTrainingListener onTrainingListener) {
            super(itemView);
            tvDayAndTime = itemView.findViewById(R.id.textview_training_day);
            tvGroupName = itemView.findViewById(R.id.textview_training_group);

            this.onTrainingListener = onTrainingListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            TrainingSchedule training = mTrainings.get((getAdapterPosition()));
            int tId = training.id;
            int groupId = training.groupId;
            onTrainingListener.onTrainingClicked(tId, groupId);
        }
    }

    private final LayoutInflater mInflater;
    private List<TrainingSchedule> mTrainings; // Cached copy of words

    public TrainingScheduleListAdapter(Context context, OnTrainingListener onTrainingListener, TrainingScheduleViewModel trainingViewModel) {
        mInflater = LayoutInflater.from(context);
        mOnTrainingListener = onTrainingListener;
        this.trainingViewModel = trainingViewModel;
    }

    @Override
    public TrainingScheduleViewHandler onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_training_schedule, parent, false);
        return new TrainingScheduleViewHandler(itemView, mOnTrainingListener);
    }

    @Override
    public void onBindViewHolder(TrainingScheduleViewHandler holder, int position) {
        if (mTrainings != null) {
            TrainingSchedule training = mTrainings.get(position);
            String nameOfDay = "";
            switch (training.dayOfWeek) {
                case 1:
                    nameOfDay = "Poniedziałek";
                    break;
                case 2:
                    nameOfDay = "Wtorek";
                    break;
                case 3:
                    nameOfDay = "Środa";
                    break;
                case 4:
                    nameOfDay = "Czwartek";
                    break;
                case 5:
                    nameOfDay = "Piątek";
                    break;
                case 6:
                    nameOfDay = "Sobota";
                    break;
                case 7:
                    nameOfDay = "Niedziela";
                    break;
            }
            holder.tvDayAndTime.setText(nameOfDay + " " + training.timeStart + "-" + training.timeEnd);
            holder.tvGroupName.setText(trainingViewModel.getGroupName(training.id));
        } else {
            // Covers the case of data not being ready yet.
            holder.tvDayAndTime.setText("No Word");
        }
    }

    public void setTrainings(List<TrainingSchedule> trainings) {
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