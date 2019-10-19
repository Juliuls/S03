package com.example.juliuls.s03;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    XmlPullParserFactory parserFactory;
    List<String> descriptions;
    List<String> names;
    List<Cameras> camerasList;

    public ListView listView;
    TextView selectionText;

    ProgressBar progressBar;
    ImageView imageView;
    private Bitmap loadedImage;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            lectorXML();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        listView = (ListView)findViewById(R.id.cameraName);
        selectionText = (TextView)findViewById(R.id.selection);
        imageView = (ImageView) findViewById(R.id.cameraImage);

        listView.setOnItemClickListener(this);

        CamerasArrayAdapter camerasArrayAdapter = new CamerasArrayAdapter(this, camerasList);
        listView.setAdapter(camerasArrayAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

    }


    public void lectorXML() throws XmlPullParserException {
        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser =  parserFactory.newPullParser();
            descriptions = new ArrayList<String>();
            names = new ArrayList<String>();
            camerasList = new ArrayList<>();

            int pos = 0;

            InputStream is = getAssets().open("CCTV.kml.html");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);

            int eventType = parser.getEventType();


            while (eventType != XmlPullParser.END_DOCUMENT) {
                String elementName = null;
                elementName = parser.getName();

                switch (eventType) {
                    case XmlPullParser.START_TAG:

                        if ("description".equals(elementName)) {

                            Cameras camera = new Cameras();
                            String cameraURL = parser.nextText();
                            cameraURL = cameraURL.substring(cameraURL.indexOf("http:"));
                            cameraURL = cameraURL.substring(0, cameraURL.indexOf(".jpg") + 4);
                            camera.setDescription(cameraURL);
                            camerasList.add(pos, camera);
                            descriptions.add(cameraURL);
                        }
                        if ("ExtendedData".equals(elementName)){
                            String name=dataReader(parser);
                            Cameras camera = camerasList.get(pos);
                            camera.setName(name);
                            camerasList.add(pos, camera);
                            pos++;
                        }

                    default:
                            break;

                }

                eventType = parser.next();

            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String dataReader(XmlPullParser parser) throws IOException, XmlPullParserException {

        parser.require(XmlPullParser.START_TAG, null, "ExtendedData");
        String value = null;

        while (parser.next() != XmlPullParser.END_TAG || value == null) {
            //System.out.println(parser.next());
            if (parser.getEventType() != XmlPullParser.START_TAG) {
               continue;
            }
            String data =parser.getName();
            //System.out.println("data: " + data);

            switch (data) {
                case "Data":

                    //parser.require(XmlPullParser.START_TAG, null, "Data");
                    String name = parser.getAttributeValue(null, "name");
                    //System.out.println("name: " + name);
                    if (name.equals("Nombre")){
                        parser.require(XmlPullParser.START_TAG, null, "Data");
                        while (parser.next() != XmlPullParser.END_TAG) {
                            value = parser.getText();
                            //System.out.println("value: " + value);
                        }
                        //parser.require(XmlPullParser.END_TAG, null, "Data");
                    }
                    //parser.nextTag();
                    //parser.require(XmlPullParser.END_TAG, null, "Data");
                    break;
            }
        }

        return value;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            SimpleTask nuevaTarea = new SimpleTask(getApplicationContext(), imageView);
            nuevaTarea.execute(camerasList.get(position).getDescription());

        selectionText.setText(camerasList.get(position).getDescription());
    }

    /*public class SimpleTask extends AsyncTask<String, Void, Bitmap> {

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Cargando Imagen");
            pDialog.setCancelable(true);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.show();

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
            pDialog.dismiss();
        }

        private Bitmap descargarImagen (String urlString){
            URL url = null;
            String contentType;
            Bitmap imagen = null;

            try{
                url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                //contentType = urlConnection.getContentType();
                imagen = BitmapFactory.decodeStream(urlConnection.getInputStream());

            }catch(IOException ex){
                ex.printStackTrace();
            }

            return imagen;
        }

    }*/

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
