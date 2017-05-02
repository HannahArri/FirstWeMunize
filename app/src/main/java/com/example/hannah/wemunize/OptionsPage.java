package com.example.hannah.wemunize;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseUser;

import java.util.ArrayList;

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

public class OptionsPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FeatureCoverFlow coverFlow;
    private CoverViewAdapter adapter;
    private ArrayList<Images> images;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        coverFlow = (FeatureCoverFlow) findViewById(R.id.coverflow);
        settingData();
        //creating adapter and scroll listener for coverflow
        adapter = new CoverViewAdapter(this, images);
        coverFlow.setAdapter(adapter);
        coverFlow.setOnScrollPositionListener(onScrollListener());
    }

    //setting scroller for coverflow
private FeatureCoverFlow.OnScrollPositionListener onScrollListener(){
    return new FeatureCoverFlow.OnScrollPositionListener(){
        @Override
        public void onScrolledToPosition(int position){
            Log.v("MainActivity", "position: " + position);
        }
        @Override
        public  void onScrolling(){
            Log.i("MainActivity", "scrolling");
        }
    };
}
// setting up images for coverflow
    private void settingData(){
        images = new ArrayList<>();
        images.add(new Images(R.drawable.content1));
        images.add(new Images(R.drawable.content2));
        images.add(new Images(R.drawable.content3));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.options_page, menu);
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
        if(id == R.id.action_logout){
            ParseUser.logOut();
            startActivity(new Intent(this, SignIn.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager manager = getSupportFragmentManager();
        if (id == R.id.nav_RegisterChild) {
            startActivity(new Intent(OptionsPage.this, ChildRegiteration.class));
        }
        else if (id == R.id.nav_MissingAlbum) {
            MissingPhotoalbum missingPhotoalbum = new MissingPhotoalbum();
            manager.beginTransaction().replace(R.id.maincontentlayout, missingPhotoalbum, missingPhotoalbum.getTag()).addToBackStack("Missing").commit();
        }
        else if (id == R.id.nav_ForgottenAlbum) {
            ForgottenPhotoalbum forgottenPhotoalbum = new ForgottenPhotoalbum();
            manager.beginTransaction().replace(R.id.maincontentlayout, forgottenPhotoalbum, forgottenPhotoalbum.getTag()).addToBackStack("Forgotten").commit();

        }
        else if (id == R.id.nav_Rfidfail) {
            ForgottenPhotoalbum forgottenPhotoalbum = new ForgottenPhotoalbum();
            manager.beginTransaction().replace(R.id.maincontentlayout, forgottenPhotoalbum, forgottenPhotoalbum.getTag()).addToBackStack("Rfid fail").commit();
        }
        else if(id == R.id.nav_proceed){

        }
        else if (id == R.id.nav_share) {

        }
        else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed(){
        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }

    }
}
