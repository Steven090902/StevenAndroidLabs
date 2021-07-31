package algonquin.cst2335.lean0016;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.google.android.material.navigation.NavigationView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class MainActivity extends AppCompatActivity {
    private String stringURL;
    EditText edit;
    TextView tv;
    Button btn;
    ImageView iv;
    Bitmap image = null;


    String currentTemp,minTemp,maxTemp,humidity,description,IconName;
    TextView currentView, minView, maxView, humidityView, descView;
    EditText cityText;

    private void runForecast(String cityName) {
        Executor newThread = Executors.newSingleThreadExecutor();
        newThread.execute(() -> {


            try {

                stringURL = "https://api.openweathermap.org/data/2.5/weather?q="
                        + URLEncoder.encode(cityName, "UTF-8")
                        + "&appid=7e943c97096a9784391a981c4d878b22&units=metric&mode=xml";


                URL url = new URL(stringURL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(in, "UTF-8");



                while (xpp.next() != XmlPullParser.END_DOCUMENT) {
                    switch (xpp.getEventType()) {
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.END_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:

                            if (xpp.getName().equals("temperature")) {
                                currentTemp = xpp.getAttributeValue(null, "value");
                                minTemp = xpp.getAttributeValue(null, "min");
                                maxTemp = xpp.getAttributeValue(null, "max");

                            } else if (xpp.getName().equals("weather")) {
                                description = xpp.getAttributeValue(null, "value");
                                IconName = xpp.getAttributeValue(null, "icon");
                            } else if (xpp.getName().equals("humidity")) {

                                humidity = xpp.getAttributeValue(null, "value");
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            break;
                        case XmlPullParser.TEXT:
                            break;
                    }
                }

                    /*String text = (new BufferedReader(
                            new InputStreamReader(in, StandardCharsets.UTF_8)))
                            .lines()
                            .collect(Collectors.joining("\n"));

                    JSONObject theDocument = new JSONObject(text);
                    JSONObject coord = theDocument.getJSONObject("coord");
                    double lat = coord.getDouble("lat");
                    double lon = coord.getDouble("lon");

                    JSONArray weatherArray = theDocument.getJSONArray("weather");
                    JSONObject obj0 = weatherArray.getJSONObject(0);
                    JSONObject main = theDocument.getJSONObject("main");
                    double current = main.getDouble("temp");
                    double min = main.getDouble("temp_min");
                    double max = main.getDouble("temp_max");
                    int humitidy = main.getInt("humidity");
                    String description = obj0.getString("description");
                    String iconName = obj0.getString("icon");
*/
                    URL imgUrl = new URL("https://openweathermap.org/img/w/" + IconName + ".png");
                    HttpURLConnection connection = (HttpURLConnection) imgUrl.openConnection();
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200) {
                        image = BitmapFactory.decodeStream(connection.getInputStream());
                        image.compress(Bitmap.CompressFormat.PNG, 100, openFileOutput(IconName + ".png", Context.MODE_PRIVATE));
                    }

                runOnUiThread(() -> {

                    currentView.setText("The current temperature is: " + currentTemp);
                    currentView.setVisibility(View.VISIBLE);

                    minView = findViewById(R.id.minTemp);
                    minView.setText("The minimum temperature is: " + minTemp);
                    minView.setVisibility(View.VISIBLE);

                    maxView = findViewById(R.id.maxTemp);
                    maxView.setText("The maximum temperature is: " + maxTemp);
                    maxView.setVisibility(View.VISIBLE);

                    humidityView = findViewById(R.id.humidity);
                    humidityView.setText("The humidity is: " + humidity + "%");
                    humidityView.setVisibility(View.VISIBLE);

                    descView = findViewById(R.id.description);
                    descView.setText(description);
                    descView.setVisibility(View.VISIBLE);

                    iv = findViewById(R.id.icon);
                    iv.setImageBitmap(image);
                    iv.setVisibility(View.VISIBLE);


                });

            } catch (IOException | XmlPullParserException e) {
                Log.e("Connection error: ", e.getMessage());
            }


        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, myToolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.popout_menu);
        navigationView.setNavigationItemSelectedListener((item) ->{
             onOptionsItemSelected(item);
             drawer.closeDrawer(GravityCompat.START);
                return false;
        });

        tv = findViewById(R.id.textView);
        btn = findViewById(R.id.forecastButton);
        edit = findViewById(R.id.city_text);
        currentView = findViewById(R.id.temp);

        Button forecastBtn = findViewById(R.id.forecastButton);
        cityText = findViewById(R.id.city_text);


        forecastBtn.setOnClickListener((click) -> {
                    String cityName = cityText.getText().toString();
                    myToolbar.getMenu().add(0, 5, 0, cityName).setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
                    runForecast(cityName);

            AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Getting forecast")
                    .setMessage("We're calling people in " + cityName + " to look outside their windows and tell us what's the weather like over there.")
                    .setView(new ProgressBar(MainActivity.this))
                    .show();
                    runForecast(cityName);

            dialog.hide();


        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_acitvity_actions, menu);

        return true;
    }

    float oldSize = 14;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case 5:
                String cityName = item.getTitle().toString();
                runForecast(cityName);
                break;

            case R.id.hide_views:
                currentView.setVisibility(View.INVISIBLE);
                maxView.setVisibility(View.INVISIBLE);
                minView.setVisibility(View.INVISIBLE);
                humidityView.setVisibility(View.INVISIBLE);
                descView.setVisibility(View.INVISIBLE);
                iv.setVisibility(View.INVISIBLE);
                cityText.setText("");
                break;

            case R.id.increase:
                oldSize++;
                currentView.setTextSize(oldSize);
                maxView.setTextSize(oldSize);
                minView.setTextSize(oldSize);
                humidityView.setTextSize(oldSize);
                descView.setTextSize(oldSize);
                cityText.setTextSize(oldSize);
                break;

            case R.id.decrease:
                oldSize = Float.max(oldSize-1, 5);
                currentView.setTextSize(oldSize);
                maxView.setTextSize(oldSize);
                minView.setTextSize(oldSize);
                humidityView.setTextSize(oldSize);
                descView.setTextSize(oldSize);
                cityText.setTextSize(oldSize);
                break;

        }
        return super.onOptionsItemSelected(item);
    }




}






