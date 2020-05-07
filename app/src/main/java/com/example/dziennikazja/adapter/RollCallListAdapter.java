package com.example.dziennikazja.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.dziennikazja.R;
import com.example.dziennikazja.db.Member;

import java.util.List;

public class RollCallListAdapter extends RecyclerView.Adapter<RollCallListAdapter.MemberViewHandler> {

    private static final String TAG = "RollCallListAdapter";
    private OnMemberListener mOnMemberListener;
    private final LayoutInflater mInflater;
    private List<Member> mMembers; // Cached copy of words
    private List<Integer> attendances;

    public void setAttendances(List<Integer> attendanceOnDay) {
        attendances = attendanceOnDay;
        notifyDataSetChanged();
    }

    public interface OnMemberListener {
        void onMemberClicked(Integer position);
    }

    class MemberViewHandler extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView tvFirstName, tvLastName;
        private final CheckBox checkBox;
        private final LinearLayout linearLayout;
        private OnMemberListener onMemberListener;

        private MemberViewHandler(View itemView, OnMemberListener onMemberListener) {
            super(itemView);
            tvFirstName = itemView.findViewById(R.id.textview_recyclerview_member_name);
            tvLastName = itemView.findViewById(R.id.textview_recyclerview_member_last_name);
            checkBox = itemView.findViewById(R.id.checkbox_recyclerview_rollcall);
            linearLayout = itemView.findViewById(R.id.linearlayout_recyclerview_rollcall);

            this.onMemberListener = onMemberListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (!checkBox.isChecked()) {
                checkBox.setChecked(true);
                linearLayout.setBackgroundColor(0xFF00FF00);
            } else {
                checkBox.setChecked(false);
                linearLayout.setBackgroundColor(0xFFFFFFFF);
            }

            int mid = mMembers.get(getAdapterPosition()).id;
            onMemberListener.onMemberClicked(mid);
            Log.d(TAG, "onClick: mid" + mid);
        }
    }

    public RollCallListAdapter(Context context, OnMemberListener onMemberListener) {
        mInflater = LayoutInflater.from(context);
        mOnMemberListener = onMemberListener;
    }

    @Override
    public MemberViewHandler onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_rollcall, parent, false);
        return new MemberViewHandler(itemView, mOnMemberListener);
    }

    @Override
    public void onBindViewHolder(MemberViewHandler holder, int position) {
        if (mMembers != null) {
            Member member = mMembers.get(position);
            holder.tvFirstName.setText(member.firstName);
            holder.tvLastName.setText(member.lastName);
            if (attendances.contains(member.id)) { //jeśli member był obecny
                holder.checkBox.setChecked(true);
                holder.linearLayout.setBackgroundColor(0xFF00FF00);
            }
        } else {
            // Covers the case of data not being ready yet.
            holder.tvFirstName.setText("No Word");
            holder.tvLastName.setText("No Word");
        }
    }

    public void setMembers(List<Member> members) {
        mMembers = members;
        notifyDataSetChanged();
    }


    // getItemCount() is called many times, and when it is first called,
    // mMembers has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mMembers != null)
            return mMembers.size();
        else return 0;
    }
}