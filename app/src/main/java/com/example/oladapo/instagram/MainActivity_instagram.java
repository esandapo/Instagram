package com.example.oladapo.instagram;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * The main and only activity of the project.
 * It listen for the scrolling to detect wheter or not the user reach the end of the photos.
 */
public class MainActivity_instagram extends ActionBarActivity implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener {
    private GridView gridView;
    private static Context context;


    public static Context getApplicationStaticContext() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram);
        context = getApplicationContext();

        gridView = (GridView) findViewById(R.id.pic);
       MyAdapter instagramAdapter = MyAdapter.getInstance(gridView);
        gridView.setAdapter(instagramAdapter);

        gridView.setOnScrollListener(this);
        My_Instagram_API.addEventsListener(instagramAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyAdapter instagramAdapter = MyAdapter.getInstance(gridView);

                Intent fullScreenIntent = new Intent(getApplicationContext(), FullScreenImage.class);
                fullScreenIntent.putExtra(MainActivity_instagram.class.getName(), instagramAdapter.getItem(position));
                startActivity(fullScreenIntent);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.instagram_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int j, int k) {
        if (i + j == k) My_Instagram_API.requestNextPageAsync();
    }


    public void onClick(View v) {

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
