package com.example.dziennikazja.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.dziennikazja.R;

import java.util.Objects;

public class SettingsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Switch switchShowPseudonyms = view.findViewById(R.id.switch_settings_show_pseudonyms);

        SharedPreferences sharedPref = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);

        boolean showPseudonyms = sharedPref.getBoolean(getString(R.string.saved_show_pseudonym_key), false);
        if (showPseudonyms)
            switchShowPseudonyms.setChecked(true);
        else
            switchShowPseudonyms.setChecked(false);

        Button btnSave = view.findViewById(R.id.button_settings_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean(getString(R.string.saved_show_pseudonym_key), switchShowPseudonyms.isChecked());
                editor.apply();
            }
        });
    }
}
