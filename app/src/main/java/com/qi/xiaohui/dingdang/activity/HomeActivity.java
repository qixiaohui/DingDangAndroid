package com.qi.xiaohui.dingdang.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.qi.xiaohui.dingdang.R;
import com.qi.xiaohui.dingdang.adapter.ContentHomeAdapter;
import com.qi.xiaohui.dingdang.adapter.NewsAdapter;
import com.qi.xiaohui.dingdang.application.DingDangApplication;
import com.qi.xiaohui.dingdang.dao.DataStore;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private DataStore dataStore;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;

    public static final String URL = "URL";
    public static final String ID = "ID";

    public static void launchActivity(Activity fromActivity){
        Intent i = new Intent(fromActivity, HomeActivity.class);
        fromActivity.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        dataStore = DingDangApplication.getDataStore();
        _updateToolBarTitle(dataStore.getMenus().get(0).getGenre());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        tabLayout = (TabLayout) findViewById(R.id.submenuTab);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        _generateMenu();
        _generateTabs(0);
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
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return true;
    }

    private void _generateMenu(){
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        final Menu menu = navigationView.getMenu();
        SubMenu subMenu = menu.addSubMenu("Channels");
        for(com.qi.xiaohui.dingdang.model.menu.Menu menuItem : dataStore.getMenus()){
            subMenu.add(menuItem.getGenre());
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                for(com.qi.xiaohui.dingdang.model.menu.Menu menu1 : dataStore.getMenus()) {
                    if(menu1.getGenre().equals(item.getTitle())){
                        _generateTabs(dataStore.getMenus().indexOf(menu1));
                        drawer.closeDrawer(Gravity.LEFT);
                        _updateToolBarTitle(menu1.getGenre());
                        break;
                    }
                }
                return true;
            }
        });
    }

    private void _generateTabs(int index){
        ContentHomeAdapter contentHomeAdapter = new ContentHomeAdapter(getSupportFragmentManager(), dataStore.getMenus().get(index).getCategory());
        viewPager.setAdapter(contentHomeAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void _updateToolBarTitle(String title){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
    }
}
