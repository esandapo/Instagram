package com.example.oladapo.instagram;

import android.content.Context;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * In this adapter class every element is loaded and resized.
 *
 *
 *
 */
public class MyAdapter extends BaseAdapter implements Interface_API{

    private static MyAdapter instance;

    private ArrayList<String> data;
    private Context context;
    private View container;

    public MyAdapter(){
        data = new ArrayList<String>();
    }

    public static MyAdapter getInstance(View view){
        if(instance == null) instance = new MyAdapter();
        instance.container = view;
        instance.context = MainActivity_instagram.getApplicationStaticContext();
        return instance;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public String getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;

        if(view == null){
            imageView = new ImageView(context);
        }
        else imageView = (ImageView) view;

        int size = container.getWidth();

        //Depending on the element number we repeat the size pattern.
            if (i % 3 != 0) size /= 2;




      imageView.setMinimumHeight(size);
        imageView.setMinimumWidth(size);

//        //We use Picasso library to handle memory and performance.
        Picasso.with(context).load(getItem(i)).resize(size, size).into(imageView);
        imageView.setTag(getItem(i));
        imageView.setOnDragListener(new View.OnDragListener() {


            @Override
            public boolean onDrag(View v, DragEvent event) {
                if (event.getAction() == DragEvent.ACTION_DROP){
                    View view = (View) event.getLocalState();
                    int selectedIndex = data.indexOf(v.getTag().toString());
                    data.remove(view.getTag().toString());
                    data.add(selectedIndex, view.getTag().toString());
                    notifyDataSetChanged();

                }


                return true;
            }
        });

        return imageView;
    }

    @Override
    public void onSuccess(List<String> imagesUrl, String nextPageUrl) {
        data.addAll(imagesUrl);
        notifyDataSetChanged();
    }

    @Override
    public void onFail(Exception exception) {

    }

    public List<String> getData() {
        return data;

    }

}
