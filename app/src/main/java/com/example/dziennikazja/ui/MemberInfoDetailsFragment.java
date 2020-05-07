package com.example.dziennikazja.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.dziennikazja.R;
import com.example.dziennikazja.databinding.FragmentMemberInfoDetailsBinding;
import com.example.dziennikazja.db.Member;
import com.example.dziennikazja.util.HelperFunctions;
import com.example.dziennikazja.viewmodel.MemberViewModel;

public class MemberInfoDetailsFragment extends Fragment {
    private static final String TAG = "MemberInfoDetailsFragme";
    private int memberId;
    private FragmentMemberInfoDetailsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_member_info_details, container, false);

        MemberViewModel mMemberViewModel = new ViewModelProvider(this).get(MemberViewModel.class);

        Bundle args = getArguments();
        if (args != null) {
            memberId = args.getInt("MEMBER_ID");
            Member member = mMemberViewModel.getMemberById(memberId);
            binding.setMember(member);
        }

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView ivBelt = view.findViewById(R.id.imageview_member_info_details_belt);
        HelperFunctions.setBeltIconColor(ivBelt, binding.getMember().tkdGrade);

        TextView tvName = view.findViewById(R.id.textview_member_details_name);
        if (!TextUtils.isEmpty(binding.getMember().pseudonym)) {
            tvName.setText(binding.getMember().firstName + " \"" + binding.getMember().pseudonym + "\" " + binding.getMember().lastName);
        } else {
            tvName.setText(binding.getMember().firstName + " " + binding.getMember().lastName);
        }
    }
}
