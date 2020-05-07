package com.example.dziennikazja.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.dziennikazja.R;
import com.example.dziennikazja.db.Group;

import java.util.List;

public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.GroupViewHandler> {

    private static final String TAG = "GroupListAdapter";
    private OnGroupListener mOnGroupListener;

    public interface OnGroupListener {
        void onGroupClicked(int position);
    }

    class GroupViewHandler extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView tvName;
        private OnGroupListener onGroupListener;

        private GroupViewHandler(View itemView, OnGroupListener onGroupListener) {
            super(itemView);
            tvName = itemView.findViewById(R.id.textview_recyclerview_group_name);

            this.onGroupListener = onGroupListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int gId = mGroups.get(getAdapterPosition()).id;
            onGroupListener.onGroupClicked(gId);
        }
    }

    private final LayoutInflater mInflater;
    private List<Group> mGroups; // Cached copy of words

    public GroupListAdapter(Context context, OnGroupListener onGroupListener) {
        mInflater = LayoutInflater.from(context);
        mOnGroupListener = onGroupListener;
    }

    @Override
    public GroupViewHandler onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_group, parent, false);
        return new GroupViewHandler(itemView, mOnGroupListener);
    }

    @Override
    public void onBindViewHolder(GroupViewHandler holder, int position) {
        if (mGroups != null) {
            Group group = mGroups.get(position);
            holder.tvName.setText(group.name);
        } else {
            // Covers the case of data not being ready yet.
            holder.tvName.setText("No Word");
        }
    }

    public void setGroups(List<Group> groups) {
        mGroups = groups;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mGroups has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mGroups != null)
            return mGroups.size();
        else return 0;
    }
}