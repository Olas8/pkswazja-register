package com.example.dziennikazja.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.dziennikazja.R;
import com.example.dziennikazja.databinding.ActivityNewMemberBinding;
import com.example.dziennikazja.db.Group;
import com.example.dziennikazja.db.Member;
import com.example.dziennikazja.util.HelperFunctions;
import com.example.dziennikazja.viewmodel.MemberViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import static com.example.dziennikazja.util.HelperFunctions.areInputsValid;

public class NewMemberActivity extends AppCompatActivity {
    private static final String TAG = "NewMemberActivity";
    private MemberViewModel viewModel;
    private Member member;
    private AutoCompleteTextView tvGrade;
    private TextInputEditText etFirstName, etLastName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityNewMemberBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_new_member);

        Toolbar toolbar = findViewById(R.id.toolbar_new_member);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> finish());

        viewModel = new ViewModelProvider(this).get(MemberViewModel.class);

        int memberIdForEdit = getIntent().getIntExtra("MEMBER_ID", -1);
        if (memberIdForEdit != -1) {
            toolbar.setTitle("Edytuj uczestnika");
            member = viewModel.getMemberById(memberIdForEdit);
            member.groupName = null;
            member.tkdGrade = "";
        } else {
            member = new Member();
        }
        binding.setMember(member);

        tvGrade = findViewById(R.id.textview_new_member_grade);
        ArrayAdapter gradeAdapter = ArrayAdapter.createFromResource(this, R.array.tkd_grade_array, android.R.layout.simple_dropdown_item_1line);
        tvGrade.setAdapter(gradeAdapter);

        ImageView ivBelt = findViewById(R.id.imageview_new_member_icon_belt);
        tvGrade.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HelperFunctions.setBeltIconColor(ivBelt, parent.getItemAtPosition(position).toString());
            }
        });

        AutoCompleteTextView tvGroup = findViewById(R.id.textview_new_member_group);
        ArrayList<Group> groups = viewModel.getGroups();
        ArrayAdapter<Group> groupAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, groups);
        tvGroup.setAdapter(groupAdapter);

        etFirstName = findViewById(R.id.edittext_add_member_first_name);
        etLastName = findViewById(R.id.edittext_add_member_last_name);
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
            if (areInputsValid(etFirstName, etLastName)) {
                if (TextUtils.isEmpty(tvGrade.getText()))
                    member.tkdGrade = getResources().getStringArray(R.array.tkd_grade_array)[0];
                viewModel.insert(member);
                Intent replyIntent = new Intent();
                setResult(RESULT_OK, replyIntent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Wypełnij wymagane pola!", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
