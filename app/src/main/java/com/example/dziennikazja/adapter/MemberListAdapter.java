package com.example.dziennikazja.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.dziennikazja.R;
import com.example.dziennikazja.db.MemberMinimal;
import com.example.dziennikazja.util.HelperFunctions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MemberListAdapter extends RecyclerView.Adapter<MemberListAdapter.MemberViewHandler> implements Filterable {

    private static final String TAG = "MemberListAdapter";
    private final boolean showPseudonyms;
    private OnMemberListener mOnMemberListener;
    private final LayoutInflater mInflater;
    private List<MemberMinimal> mMembers;
    private List<MemberMinimal> allMembersBackup; // Cached copy of words

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<MemberMinimal> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(allMembersBackup);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                filteredList = allMembersBackup.stream()
                        .filter(x -> x.firstName.toLowerCase().contains(filterPattern) || x.lastName.toLowerCase().contains(filterPattern) || x.pseudonym.toLowerCase().contains(filterPattern))
                        .collect(Collectors.toList());
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values != null) {
                mMembers.clear();
                mMembers.addAll((List) results.values);
                notifyDataSetChanged();
            }
        }
    };

    public interface OnMemberListener {
        void onMemberClicked(int position);
    }

    class MemberViewHandler extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView tvName, tvGroup;
        private final ImageView ivBelt;
        private final LinearLayout linearLayout;
        private OnMemberListener onMemberListener;

        private MemberViewHandler(View itemView, OnMemberListener onMemberListener) {
            super(itemView);
            tvName = itemView.findViewById(R.id.textview_recyclerview_member_name);
            linearLayout = itemView.findViewById(R.id.linearlayout_recyclerview_rollcall);
            tvGroup = itemView.findViewById(R.id.textview_recyclerview_member_group);
            ivBelt = itemView.findViewById(R.id.imageview_recyclerview_member_belt);
            this.onMemberListener = onMemberListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int mid = mMembers.get(getAdapterPosition()).id;
            onMemberListener.onMemberClicked(mid);
        }
    }


    public MemberListAdapter(Context context, OnMemberListener onMemberListener, boolean showPseudonyms) {
        mInflater = LayoutInflater.from(context);
        mOnMemberListener = onMemberListener;
        this.showPseudonyms = showPseudonyms;
    }

    @Override
    public MemberViewHandler onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_member, parent, false);
        return new MemberViewHandler(itemView, mOnMemberListener);
    }

    @Override
    public void onBindViewHolder(MemberViewHandler holder, int position) {
        if (mMembers != null) {
            MemberMinimal member = mMembers.get(position);
            if (showPseudonyms && !TextUtils.isEmpty(member.pseudonym)) {
                holder.tvName.setText(member.pseudonym);
            } else {
                holder.tvName.setText(member.firstName + " " + member.lastName);
            }

            holder.tvGroup.setText(member.groupName);

            HelperFunctions.setBeltIconColor(holder.ivBelt, member.tkdGrade);
        } else {
            // Covers the case of data not being ready yet.
            holder.tvName.setText("");
        }
    }

    public void setMembers(List<MemberMinimal> members) {
        mMembers = members;
        allMembersBackup = new ArrayList<>(members);
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