package com.example.dziennikazja.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.dziennikazja.R;
import com.example.dziennikazja.databinding.ActivityNewGroupBinding;
import com.example.dziennikazja.db.Group;
import com.example.dziennikazja.db.TrainingSchedule;
import com.example.dziennikazja.viewmodel.GroupViewModel;
import com.example.dziennikazja.viewmodel.TrainingScheduleViewModel;
import com.google.android.material.textfield.TextInputEditText;

import static com.example.dziennikazja.util.HelperFunctions.areInputsValid;
import static com.example.dziennikazja.util.HelperFunctions.convertDayNameToDayNumber;

public class NewGroupActivity extends AppCompatActivity {
    private static final String TAG = "NewGroupActivity";
    private GroupViewModel groupViewModel;
    private TrainingScheduleViewModel trainingScheduleViewModel;
    private Group group;
    private TextInputEditText etName;
    private AutoCompleteTextView tvDay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityNewGroupBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_new_group);

        Toolbar toolbar = findViewById(R.id.toolbar_new_group);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> finish());

        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        trainingScheduleViewModel = new ViewModelProvider(this).get(TrainingScheduleViewModel.class);

        int groupIdForEdit = getIntent().getIntExtra("GROUP_ID", -1);
        if (groupIdForEdit != -1) {
            group = groupViewModel.getGroupById(groupIdForEdit);
        } else {
            group = new Group();
        }

        binding.setGroup(group);

        tvDay = findViewById(R.id.textview_new_group_day);
        ArrayAdapter gradeAdapter = ArrayAdapter.createFromResource(this, R.array.days_array, android.R.layout.simple_dropdown_item_1line);
        tvDay.setAdapter(gradeAdapter);

        etName = findViewById(R.id.edittext_add_group_name);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_entity_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            if (areInputsValid(etName)) {
                if (group.id != 0) {
                    groupViewModel.update(group);
                } else {
                    int newGroupId = (int) groupViewModel.insert(group);

                    TrainingSchedule trainingSchedule1 = new TrainingSchedule(
                            newGroupId,
                            convertDayNameToDayNumber(tvDay.getText().toString()),
                            "12:00",
                            "13:00"
                    );
                    trainingScheduleViewModel.insert(trainingSchedule1);
                }
                Intent replyIntent = new Intent();
                setResult(RESULT_OK, replyIntent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Pole 'Nazwa grupy' oraz co najmniej jeden trening muszą być wypełnione!", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
