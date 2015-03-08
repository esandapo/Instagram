package com.example.oladapo.instagram;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Oladapo on 2/25/2015.
 */
public class FullScreenImage extends Activity {
  ImageView fullimage;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullscreen);
        Intent intent = getIntent();
        Object imageId =intent.getExtras().get(MainActivity_instagram.class.getName());
       fullimage = (ImageView) findViewById(R.id.fullImage);
        //converting the Object (imageId) into a type string
         String url = imageId.toString();
        String image_url = url;

        //Create an Object for subclass of the AsyncTask
        GetXMLTask task = new GetXMLTask();

        //Executing the specified Task
        task.execute (new String[] {url});

    }
    private class GetXMLTask extends AsyncTask<String, Void,Bitmap>{

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            for (String url : params){
                bitmap = downLoadImage(url);
            }
            return bitmap;
        }
        //This will set the Bitmap returned by doInBackground Method
        @Override
        protected void onPostExecute(Bitmap result){
            fullimage.setImageBitmap(result);
        }

        // Creates Bitmap from the Inputstream and then returns it
        private Bitmap downLoadImage (String url){
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bfo = new BitmapFactory.Options();
            bfo.inSampleSize = 1;
            try{
                stream = getHttpConnection(url);
                bitmap = BitmapFactory.decodeStream(stream, null, bfo);
                stream.close();

            }catch (IOException e){
                e.printStackTrace();
            }
            return bitmap;
        }

        // This will make the HttpURLConnection and then returns the Inputstream
        private InputStream getHttpConnection (String urlString) throws IOException{
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            try{
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
            }
        }catch (Exception ex){
                ex.printStackTrace();
            }
            return stream;
    }

}
}


