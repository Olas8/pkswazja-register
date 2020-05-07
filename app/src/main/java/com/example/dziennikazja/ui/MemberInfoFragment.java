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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.dziennikazja.R;
import com.example.dziennikazja.viewmodel.MemberViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MemberInfoFragment extends AppCompatActivity {
    private static final String TAG = "MemberInfoFragment";
    private static final int EDIT_MEMBER_REQUEST_CODE = 234;
    private int memberId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_member_info);

        Toolbar toolbar = findViewById(R.id.toolbar_member_info);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> finish());

        FragmentStateAdapter pagerAdapter = new ScreenSlidePagerAdapter(this);
        ViewPager2 viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText(getResources().getText(R.string.personal_data).toString());
                            break;
                        case 1:
                            tab.setText(getResources().getText(R.string.attendance_stats).toString());
                            break;
                        case 2:
                            tab.setText(getResources().getText(R.string.payments).toString());
                            break;
                        case 3:
                            tab.setText(getResources().getText(R.string.exams).toString());
                            break;
                        default:
                            tab.setText("TAB");
                    }
                }
        ).attach();

        Intent receivedIntent = getIntent();
        memberId = receivedIntent.getIntExtra("MEMBER_ID", -1);
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
                MemberViewModel viewModel = new ViewModelProvider(this).get(MemberViewModel.class);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                //builder.setTitle("Usuwanie");
                builder.setMessage("Czy na pewno chcesz usunąć tego uczestnika?");
                builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        viewModel.deleteById(memberId);
                        Toast.makeText(MemberInfoFragment.this, "Usunięto uczestnika", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(this, NewMemberActivity.class);
                intent.putExtra("MEMBER_ID", memberId);
                startActivityForResult(intent, EDIT_MEMBER_REQUEST_CODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_MEMBER_REQUEST_CODE && resultCode == RESULT_OK) {
            recreate();
            Toast.makeText(
                    this,
                    "Zmiany zostały zapisane.",
                    Toast.LENGTH_LONG).show();
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            Fragment fragment;

            switch (position) {
                case 1:
                    fragment = new MemberInfoAttendanceFragment();
                    break;
                case 2:
                    fragment = new MemberInfoPaymentsFragment();
                    break;
                case 3:
                    fragment = new MemberInfoExamsFragment();
                    break;
                default:
                    fragment = new MemberInfoDetailsFragment();
            }

            Bundle args = new Bundle();
            args.putInt("MEMBER_ID", memberId);
            fragment.setArguments(args);

            return fragment;
        }

        @Override
        public int getItemCount() {
            return 4;
        }
    }
}