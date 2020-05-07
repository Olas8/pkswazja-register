package com.example.dziennikazja.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.dziennikazja.R;
import com.example.dziennikazja.db.Group;
import com.example.dziennikazja.viewmodel.GroupViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FilterMembersDialog extends DialogFragment {
    public interface FilterMembersDialogListener {
        void onDialogPositiveClick(DialogFragment dialog, Bundle bundle);
    }

    FilterMembersDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (FilterMembersDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(" must implement FilterMembersDialogListener");
        }
    }

    private static final String TAG = "FilterMembersDialog";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_filter_members, null);

        GroupViewModel groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        List<Group> allGroups = groupViewModel.getAllAsList();
        allGroups.forEach(System.out::println);

        LinearLayout groupsLinearLayout = dialogView.findViewById(R.id.linearlayout_dialog_filter_members_groups);
        ArrayList<CheckBox> groupCheckBoxes = new ArrayList<>(allGroups.size());
        allGroups.forEach(
                group -> {
                    CheckBox cb = new CheckBox(dialogView.getContext());
                    cb.setText(group.name + " " + group.id);
                    cb.setChecked(true);
                    groupsLinearLayout.addView(cb);
                    groupCheckBoxes.add(cb);
                    Log.d(TAG, "onCreateDialog: stworzono chechboxa = " + cb.getText().toString());
                }

        );

        builder.setView(dialogView);
        builder.setMessage("Filtruj")
                .setPositiveButton("Zapisz", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Spinner spinnerGradeFrom = dialogView.findViewById(R.id.spinner_dialog_filter_members_grade_from);
                        Spinner spinnerGradeTo = dialogView.findViewById(R.id.spinner_dialog_filter_members_grade_to);
                        String gradeFrom = spinnerGradeFrom.getSelectedItem().toString();
                        String gradeTo = spinnerGradeTo.getSelectedItem().toString();

                        List<String> filteredGroupNames = groupCheckBoxes.stream()
                                .filter(CheckBox::isChecked)
                                .map(CheckBox::toString)
                                .collect(Collectors.toList());

                        List<Integer> filteredGroupsIds = allGroups.stream()
                                .filter(group -> {
                                    for (String name : filteredGroupNames) {
                                        return name.equals(group.name);
                                    }
                                    return false;
                                })
                                .map(group -> group.id)
                                .collect(Collectors.toList());

                        Bundle bundle = new Bundle();
                        bundle.putString("FILTER_GRADE_FROM", gradeFrom);
                        bundle.putString("FILTER_GRADE_TO", gradeTo);
                        bundle.putIntegerArrayList("FILTER_GROUPS_IDS", new ArrayList<>(filteredGroupsIds));
                        listener.onDialogPositiveClick(FilterMembersDialog.this, bundle);
                    }
                })
                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FilterMembersDialog.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }
}
