package com.example.oladapo.instagram;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

/**
 * The main and only activity of the project.
 * It listen for the scrolling to detect wheter or not the user reach the end of the photos.
 */
public class MainActivity_instagram extends ActionBarActivity implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener, View.OnLongClickListener {
    public static GridView gridView;
    private static Context context;

    int pos;
    public static Context getApplicationStaticContext() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram);
        context = getApplicationContext();

        gridView = (GridView) findViewById(R.id.pic);
        final MyAdapter instagramAdapter = MyAdapter.getInstance(gridView);
        gridView.setAdapter(instagramAdapter);

        gridView.setOnScrollListener(this);
        My_Instagram_API.addEventsListener(instagramAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyAdapter instagramAdapter = MyAdapter.getInstance(gridView);
                pos = position;
                Intent fullScreenIntent = new Intent(getApplicationContext(), FullScreenImage.class);
                fullScreenIntent.putExtra(MainActivity_instagram.class.getName(), instagramAdapter.getItem(position));
                startActivity(fullScreenIntent);
            }
        });



       gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           float initialX , initialY;
           @Override
           public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

               ClipData data = ClipData.newPlainText("", "");
               View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
               view.startDrag(data, shadowBuilder, view, 0);

               return true;
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


    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
