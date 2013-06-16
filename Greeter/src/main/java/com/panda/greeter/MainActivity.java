package com.panda.greeter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends Activity {
    private TextView greetingView;
    private Greeter greeter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        greetingView = (TextView) findViewById(R.id.greeting);
        greeter = new Greeter();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void greetToWorld(View view) {
        greetingView.setText(greeter.greetToWorld());
    }

    public void greetToPerson(View view) {
        EditText personNameView = (EditText) findViewById(R.id.personName);
        String name = personNameView.getText().toString();
        Person person = new Person(name);

        greetingView.setText(greeter.greetToPerson(person));
    }

    public void serverGreet(View view) throws IOException {
        String urlString = "http://example.iana.org/";

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebPageTask().execute(urlString);
        } else {
            greetingView.setText("No network connection available.");
        }
    }

    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            greetingView.setText(result);
        }

        private String downloadUrl(String myurl) throws IOException {
            InputStream is = null;
            // Only display the first 500 characters of the retrieved
            // web page content.
            int len = 500;

            try {
                URL url = new URL(myurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                is = conn.getInputStream();

                // Convert the InputStream into a string
                String contentAsString = readIt(is, len);
                return contentAsString;

                // Makes sure that the InputStream is closed after the app is
                // finished using it.
            } finally {
                if (is != null) {
                    is.close();
                }
            }
        }

        public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[len];
            reader.read(buffer);
            return new String(buffer);
        }
    }

    public void helloWorldImage(View view) {
        String urlString = "http://www.cocos2d-iphone.org/wiki/lib/exe/fetch.php/wiki:hello_world.jpg";

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadImageTask().execute(urlString);
        } else {
            greetingView.setText("No network connection available.");
        }

        greetingView.setText("Downloading...");
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Void> {
        private Drawable imageDrawable;

        @Override
        protected Void doInBackground(String... urls) {
            try {
                downloadImage(urls[0]);
            } catch (IOException e) {
                greetingView.setText("Unable to retrieve web page. URL may be invalid.");
            }

            return null;
        }

        private Void downloadImage(String stringUrl) throws IOException {
            InputStream is = null;

            try {
                is = (InputStream) new URL(stringUrl).getContent();
                imageDrawable = Drawable.createFromStream(is, "src name");
            } finally {
                if (is != null) {
                    is.close();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            greetingView.setText("Done.");

            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageDrawable(imageDrawable);
        }

        private void displayImage(InputStream stream) {
            Bitmap bitmap = BitmapFactory.decodeStream(stream);
            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageBitmap(bitmap);
        }
    }
}
