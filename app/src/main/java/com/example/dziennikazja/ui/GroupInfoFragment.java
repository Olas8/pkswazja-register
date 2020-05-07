package com.example.dziennikazja.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.dziennikazja.R;
import com.example.dziennikazja.databinding.FragmentGroupInfoBinding;
import com.example.dziennikazja.db.Group;
import com.example.dziennikazja.viewmodel.GroupViewModel;

public class GroupInfoFragment extends AppCompatActivity {
    private static final String TAG = "MemberInfoDetailsFragme";
    private static final int EDIT_GROUP_REQUEST_CODE = 3432;
    private int groupId;
    private GroupViewModel groupViewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentGroupInfoBinding binding = DataBindingUtil.setContentView(this, R.layout.fragment_group_info);

        Toolbar toolbar = findViewById(R.id.toolbar_group_info);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> finish());

        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);

        Intent receivedIntent = getIntent();
        if (receivedIntent != null) {
            groupId = receivedIntent.getIntExtra("GROUP_ID", -1);
            Group group = groupViewModel.getGroupById(groupId);
            binding.setGroup(group);
        } else {
            Toast.makeText(
                    this,
                    "Coś poszło nie tak przy wyświetlaniu danych.",
                    Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.member_details_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                //TODO dialog czy na pewno usunąć uczestnika
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                //builder.setTitle("Usuwanie");
                builder.setMessage("Czy na pewno chcesz usunąć tę grupę?");
                builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        groupViewModel.deleteById(groupId);
                        Toast.makeText(GroupInfoFragment.this, "Usunięto grupę", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

                return true;
            case R.id.action_edit:
                Intent intent = new Intent(this, NewGroupActivity.class);
                intent.putExtra("GROUP_ID", groupId);
                startActivityForResult(intent, EDIT_GROUP_REQUEST_CODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_GROUP_REQUEST_CODE && resultCode == RESULT_OK) {
            recreate();
            Toast.makeText(
                    this,
                    "Zmiany zostały zapisane.",
                    Toast.LENGTH_LONG).show();
        }
    }
}