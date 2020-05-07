package com.example.dziennikazja.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dziennikazja.R;
import com.example.dziennikazja.adapter.MemberListAdapter;
import com.example.dziennikazja.dialog.FilterMembersDialog;
import com.example.dziennikazja.viewmodel.MemberViewModel;

import java.util.ArrayList;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class MembersFragment extends Fragment implements MemberListAdapter.OnMemberListener, FilterMembersDialog.FilterMembersDialogListener {
    private static final String TAG = "MembersFragment";
    public static final int NEW_MEMBER_ACTIVITY_REQUEST_CODE = 1;
    private MemberViewModel mMemberViewModel;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_members, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPref = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
        boolean showPseudonyms = sharedPref.getBoolean(getString(R.string.saved_show_pseudonym_key), false);

        recyclerView = view.findViewById(R.id.recyclerview_members);
        final MemberListAdapter adapter = new MemberListAdapter(getContext(), this, showPseudonyms);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mMemberViewModel = new ViewModelProvider(this).get(MemberViewModel.class);

        // Update the cached copy of the words in the adapter.
        mMemberViewModel.getAllMembersMinimal().observe(getViewLifecycleOwner(), adapter::setMembers);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_MEMBER_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Toast.makeText(
                    getContext(),
                    R.string.member_created,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onMemberClicked(int position) {
        Intent intent = new Intent(getActivity(), MemberInfoFragment.class);
        intent.putExtra("MEMBER_ID", position);
        startActivity(intent);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, Bundle bundle) {
        String gradeFrom = bundle.getString("FILTER_GRADE_FROM");
        String gradeTo = bundle.getString("FILTER_GRADE_TO");
        ArrayList<Integer> filteredGroupsIds = bundle.getIntegerArrayList("FILTER_GROUPS_IDS");

        // ((MemberListAdapter)recyclerView.getAdapter()).getFilter().filter(bundle);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchItem.setVisible(true);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                MemberListAdapter adapter = (MemberListAdapter) recyclerView.getAdapter();
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        MenuItem addItem = menu.findItem(R.id.action_add);
        addItem.setIcon(R.drawable.ic_person_add);
        addItem.setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(getActivity(), NewMemberActivity.class);
                startActivityForResult(intent, NEW_MEMBER_ACTIVITY_REQUEST_CODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
