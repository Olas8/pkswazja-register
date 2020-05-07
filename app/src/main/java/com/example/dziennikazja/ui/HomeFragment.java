package com.example.dziennikazja.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.dziennikazja.R;
import com.example.dziennikazja.databinding.FragmentMemberInfoDetailsBinding;

public class HomeFragment extends Fragment {
    private FragmentMemberInfoDetailsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_home, container, false);

        Member member = new Member("BoA");
        member.lastName = "Kim";
        binding.setMember(member);

        return binding.getRoot();*/
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*ImageButton btnTest = view.findViewById(R.id.test_button);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), binding.getMember().firstName +" " +binding.getMember().lastName, Toast.LENGTH_SHORT).show();
            }
        });*/
    }

}
