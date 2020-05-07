package com.example.dziennikazja.ui;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dziennikazja.R;
import com.example.dziennikazja.adapter.TrainingScheduleListAdapter;
import com.example.dziennikazja.viewmodel.TrainingScheduleViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.joda.time.LocalDate;

import static android.app.Activity.RESULT_OK;

public class TrainingsFragment extends Fragment implements TrainingScheduleListAdapter.OnTrainingListener {
    public static final int NEW_MEMBER_ACTIVITY_REQUEST_CODE = 1;
    private TrainingScheduleViewModel trainingViewModel;
    private static final String TAG = "TrainingsFragment";
    private CalendarView calendarView;
    private Calendar selectedDay;
    private LocalDate chosenDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trainings, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedDay = Calendar.getInstance();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        trainingViewModel = new ViewModelProvider(this).get(TrainingScheduleViewModel.class);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_trainings);
        final TrainingScheduleListAdapter adapter = new TrainingScheduleListAdapter(getContext(), this, trainingViewModel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        // Update the cached copy of the words in the adapter.
        //trainingViewModel.getAllTrainings().observe(getViewLifecycleOwner(), adapter::setTrainings);

        FloatingActionButton fab = view.findViewById(R.id.fab_trainings);
        fab.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), NewTrainingActivity.class);
            startActivityForResult(intent, NEW_MEMBER_ACTIVITY_REQUEST_CODE);
        });

        calendarView = view.findViewById(R.id.calendarview_trainings);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                chosenDate = new LocalDate(year, month + 1, dayOfMonth);
                int dayOfWeek = chosenDate.getDayOfWeek();
                trainingViewModel.getScheduleForDay(dayOfWeek).observe(getViewLifecycleOwner(), adapter::setTrainings);
            }
        });
        //calendarView.setDate(Calendar.getInstance().getTimeInMillis());
    }

    @Override
    public void onStart() {
        super.onStart();
        //nic to nie daje, miało ustawiać dzisiejszą datę na starcie zeby sie treningi wczytaly
        /*String selectedDate = "21/02/2020";
        try {
            calendarView.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(selectedDate).getTime(), true, true);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_MEMBER_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle bundle = data.getBundleExtra("NEW_TRAINING_BUNDLE");
            /*Training training = new Training(
                    bundle.getInt("GROUP_ID"),
                    bundle.getString("DAY"),
                    bundle.getString("START_TIME"),
                    bundle.getString("END_TIME")
            );
            trainingViewModel.insert(training);*/
        } else {
            Toast.makeText(
                    getContext(),
                    R.string.not_created,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onTrainingClicked(int position, int groupId) {
        Intent intent = new Intent(getActivity(), CheckAttendanceActivity.class);
        intent.putExtra("TRAINING_GROUP_ID", groupId);
        intent.putExtra("TRAINING_DATE", chosenDate);

        startActivity(intent);
    }
}
