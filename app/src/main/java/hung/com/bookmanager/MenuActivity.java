package hung.com.bookmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                break;
            case R.id.action_about:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_user: {
                Intent intent = new Intent(MenuActivity.this, UserActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.nav_book: {
                Intent intent = new Intent(MenuActivity.this, BookActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.nav_thongke:

                break;
            case R.id.nav_doimatkhau: {
                Intent intent = new Intent(MenuActivity.this, DoiMatKhauActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.nav_dangxuat:
                startActivity(new Intent(MenuActivity.this, LoginActivity.class));
                break;
            case R.id.nav_exit:
                System.exit(0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void viewUser(View view) {
        Intent intent = new Intent(MenuActivity.this, UserActivity.class);
        startActivity(intent);
    }

    public void viewBook(View view) {
        Intent intent = new Intent(MenuActivity.this, BookActivity.class);
        startActivity(intent);
    }

    public void viewSachbanchay(View view) {
        startActivity(new Intent(MenuActivity.this, SachBanChayActivity.class));
    }

    public void viewTheLoai(View view) {
        Intent intent = new Intent(MenuActivity.this, TypeActivity.class);
        startActivity(intent);
    }

    public void viewHoaDon(View view) {
        startActivity(new Intent(MenuActivity.this, BillActivity.class));
    }

    public void viewThongKe(View view) {
        startActivity(new Intent(MenuActivity.this, ThongKeActivity.class));
    }
}
