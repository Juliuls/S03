package com.example.juliuls.s03;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class SimpleTask extends AsyncTask<String, Void, Bitmap> {

    ProgressDialog pDialog;
    Context context;
    //private WeakReference<Context> contextRef;
    ImageView imageView;

    public SimpleTask(final Context activity, ImageView imageView){
        //contextRef = new WeakReference<>(context);
        this.context = activity;
        this.imageView = imageView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        //pDialog = new ProgressDialog(context);
        //pDialog.setMessage("Cargando Imagen");
        //pDialog.setCancelable(true);
        //pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //pDialog.show();

    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String url = params[0];
        Bitmap imagen = descargarImagen(url);
        return imagen;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        imageView.setImageBitmap(result);
        //pDialog.dismiss();
    }

    private Bitmap descargarImagen (String urlString){
        URL url = null;
        String contentType;
        Bitmap imagen = null;

        try{
            url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            contentType = urlConnection.getContentType();
            System.out.println("Content type: "+contentType.toString());
            imagen = BitmapFactory.decodeStream(urlConnection.getInputStream());

        }catch(IOException ex){
            ex.printStackTrace();
        }

        return imagen;
    }

}
