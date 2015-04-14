package com.parkingapp.sample;

import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.parkingapp.sample.R;
import com.parkingapp.connection.SFParkHandler;
import com.parkingapp.exception.ParkingAppException;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.zip.GZIPInputStream;


public class MainActivity extends ActionBarActivity {

    private static final int SECOND_IN_MILLIS = (int) DateUtils.SECOND_IN_MILLIS;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView t = (TextView)findViewById(R.id.apimsgTextView);
        SFParkHandler sfParkHandler = new SFParkHandler();
        String latitude= "37.792275";
        String longitude ="-122.397089";
        String radius = "0.25";
        StringBuilder response = null;
        try {
            response = sfParkHandler.callAvailabilityService(latitude, longitude, radius);
        } catch(ParkingAppException e) {

            }
        if(response != null) {
            t.setText(response);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.




        getMenuInflater().inflate(R.menu.menu_main, menu);
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



private static class InflatingEntity extends HttpEntityWrapper {
    public InflatingEntity(HttpEntity wrapped) {
        super(wrapped);
    }

    @Override
    public InputStream getContent() throws IOException {
        return new GZIPInputStream(wrappedEntity.getContent());
    }

    @Override
    public long getContentLength() {
        return -1;
    }
}

}