package com.example.dziennikazja.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.dziennikazja.R;
import com.google.android.material.navigation.NavigationView;

/*TODO layout imion rodziców w MemberDetails
  TODO przechowywanie zdjęcia u Membera
  TODO poprawić layout dodawania grup
  TODO przyciski wstecz i zapisz przy tworzeniu Membera
  TODO wyświetlanie pseudonimu w cudzysłowiu w MemberDetails
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        appBarConfiguration =
                //new AppBarConfiguration.Builder(navController.getGraph()).setDrawerLayout(drawerLayout).build();
                new AppBarConfiguration.Builder(
                        R.id.trainingsFragment,
                        R.id.attendanceStatsFragment,
                        R.id.competitionsFragment,
                        R.id.groupsFragment,
                        R.id.membersFragment,
                        R.id.paymentsFragment,
                        R.id.settingsFragment,
                        R.id.homeFragment,
                        R.id.examsFragment,
                        R.id.newMemberActivity).setDrawerLayout(drawerLayout).build();

        setupActionBar(navController, appBarConfiguration);
        setupNavigationMenu(navController);
    }

    private void setupNavigationMenu(NavController navController) {
        NavigationView navView = findViewById(R.id.nav_view);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void setupActionBar(NavController navController, AppBarConfiguration appBarConfig) {
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.overflow_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }
}
