package com.example.dziennikazja.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dziennikazja.R;
import com.example.dziennikazja.adapter.GroupListAdapter;
import com.example.dziennikazja.viewmodel.GroupViewModel;

import static android.app.Activity.RESULT_OK;

public class GroupsFragment extends Fragment implements GroupListAdapter.OnGroupListener {
    public static final int NEW_GROUP_ACTIVITY_REQUEST_CODE = 1;
    private GroupViewModel groupViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_groups, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_groups);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));

        final GroupListAdapter adapter = new GroupListAdapter(getContext(), this);
        recyclerView.setAdapter(adapter);

        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        groupViewModel.getAllGroups().observe(getViewLifecycleOwner(), adapter::setGroups);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_GROUP_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            Toast.makeText(
                    getContext(),
                    R.string.group_created,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onGroupClicked(int position) {
        Intent intent = new Intent(getActivity(), GroupInfoFragment.class);
        intent.putExtra("GROUP_ID", position);
        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        MenuItem addItem = menu.findItem(R.id.action_add);
        addItem.setIcon(R.drawable.ic_group_add);
        addItem.setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(getActivity(), NewGroupActivity.class);
                startActivityForResult(intent, NEW_GROUP_ACTIVITY_REQUEST_CODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
