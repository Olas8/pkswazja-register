package com.example.dziennikazja.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.dziennikazja.R;
import com.example.dziennikazja.db.Group;
import com.example.dziennikazja.viewmodel.GroupViewModel;

import java.util.ArrayList;

public class NewTrainingActivity extends AppCompatActivity {
    private EditText etDay, etStartTime, etEndTime;
    private Spinner spinnerGroup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_training);

        etDay = findViewById(R.id.edittext_add_training_day);
        etStartTime = findViewById(R.id.edittext_add_training_start_time);
        etEndTime = findViewById(R.id.edittext_add_training_end_time);
        spinnerGroup = findViewById(R.id.spinner_add_training_group);

        ArrayList<String> groupsList = new ArrayList<>();
        ArrayList<Integer> groupsIdList = new ArrayList<>();

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, groupsList);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGroup.setAdapter(adapter);

        GroupViewModel groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);

        groupViewModel.getAllGroups().observe(this, (groups -> {
            adapter.addAll(groups);
            adapter.notifyDataSetChanged();
        }));

        Button btnAdd = findViewById(R.id.button_add);
        Button btnCancel = findViewById(R.id.button_cancel);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();

                String day = etDay.getText().toString();
                String startTime = etStartTime.getText().toString();
                String endTime = etEndTime.getText().toString();

                if (areStringsEmpty(day, startTime, endTime)) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {

                    Bundle bundle = new Bundle();
                    bundle.putString("DAY", day);
                    bundle.putString("START_TIME", startTime);
                    bundle.putString("END_TIME", endTime);
                    bundle.putInt("GROUP_ID", ((Group) spinnerGroup.getSelectedItem()).id);

                    replyIntent.putExtra("NEW_TRAINING_BUNDLE", bundle);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });

        btnCancel.setOnClickListener(v -> finish());
    }

    boolean areStringsEmpty(String... inputs) {
        for (String input : inputs) {
            if (TextUtils.isEmpty(input))
                return true;
        }

        return false;
    }
}
