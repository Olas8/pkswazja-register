package com.example.dziennikazja.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dziennikazja.R;
import com.example.dziennikazja.adapter.RollCallListAdapter;
import com.example.dziennikazja.db.Attendance;
import com.example.dziennikazja.viewmodel.AttendanceViewModel;
import com.example.dziennikazja.viewmodel.MemberViewModel;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckAttendanceActivity extends AppCompatActivity implements RollCallListAdapter.OnMemberListener {
    List<Integer> presentMemebersIds;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_attendance);

        Intent receivedIntent = getIntent();
        int groupId = receivedIntent.getIntExtra("TRAINING_GROUP_ID", -1);
        LocalDate trainingDate = (LocalDate) receivedIntent.getSerializableExtra("TRAINING_DATE");

        MemberViewModel memberViewModel = new ViewModelProvider(this).get(MemberViewModel.class);
        AttendanceViewModel attendanceViewModel = new ViewModelProvider(this).get(AttendanceViewModel.class);

        List<Attendance> testAtt = attendanceViewModel.getAttendanceBetweenTwoDates(new LocalDate(2020, 3, 3), new LocalDate(2020, 3, 13));
        Log.d(TAG, "onCreate: testAttendance between dates: " + testAtt.size());

        RecyclerView recyclerView = findViewById(R.id.recyclerview_check_attendance);
        final RollCallListAdapter adapter = new RollCallListAdapter(this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Log.d(TAG, "onCreate: znowu oncreate we fgragmencie");
        // Update the cached copy of the words in the adapter.
        adapter.setMembers(memberViewModel.getMembersInGroup(groupId));
        adapter.setAttendances(attendanceViewModel.getAttendanceOnDay(trainingDate));
        presentMemebersIds = new ArrayList<>();

        Button btnSave = findViewById(R.id.button_check_attendance_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Attendance> attendances = new ArrayList<>(presentMemebersIds.size());
                for (int memberId : presentMemebersIds) {
                    attendances.add(new Attendance(memberId, trainingDate, true));
                }
                attendanceViewModel.insertAll(attendances);
            }
        });

    }

    private static final String TAG = "CheckAttendanceActivity";

    @Override
    public void onMemberClicked(Integer memberId) {
        if (presentMemebersIds.contains(memberId)) {
            presentMemebersIds.remove(memberId);
        } else
            presentMemebersIds.add(memberId);
        Log.d(TAG, "onMemberClicked: " + Arrays.deepToString(presentMemebersIds.toArray()));
    }
}
