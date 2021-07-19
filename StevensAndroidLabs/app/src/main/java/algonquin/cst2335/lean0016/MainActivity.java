package algonquin.cst2335.lean0016;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    private String stringURL;
    EditText edit;
    TextView tv;
    Button btn;
    ImageView iv;
    Bitmap image = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.textView);
        btn = findViewById(R.id.forecastButton);
        edit = findViewById(R.id.city_text);


        Button forecastBtn = findViewById(R.id.forecastButton);
        EditText cityText = findViewById(R.id.city_text);

        forecastBtn.setOnClickListener((click) -> {
            String cityName = cityText.getText().toString();

            AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Getting forecast")
                    .setMessage("We're calling people in " + cityName + " to look outside their windows and tell us what's the weather like over there.")
                    .setView(new ProgressBar(MainActivity.this))
                    .show();

            Executor newThread = Executors.newSingleThreadExecutor();
            newThread.execute(() -> {


                try {

                    stringURL = "https://api.openweathermap.org/data/2.5/weather?q="
                            + URLEncoder.encode(cityName, "UTF-8")
                            + "&appid=7e943c97096a9784391a981c4d878b22&units=metric";


                    URL url = new URL(stringURL);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                    /*XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    factory.setNamespaceAware(false);
                    XmlPullParser xpp = factory.newPullParser();
                    xpp.setInput( in  , "UTF-8");

                    String currentTemp;
                    String minTemp;
                    String maxTemp;
                    String value;

                    while(xpp.next() != XmlPullParser.END_DOCUMENT)
                    {
                        switch(xpp.getEventType())
                        {
                            case XmlPullParser.START_DOCUMENT:
                                break;
                            case XmlPullParser.END_DOCUMENT:
                                break;
                            case   XmlPullParser.START_TAG:

                                if(xpp.getName().equals("temperature"))
                                {
                                    currentTemp = xpp.getAttributeValue(null, "value");
                                    minTemp = xpp.getAttributeValue(null, "min");
                                    maxTemp = xpp.getAttributeValue(null, "max");
                                }
                                else if(xpp.getName().equals("weather"))
                                {
                                    value = xpp.getAttributeValue(null, "value");
                                }
                                else if(xpp.getName().equals("Longitude")) {

                                    xpp.next();
                                    xpp.getText();
                                    xpp.nextText();

                                }
                                break;
                            case XmlPullParser.END_TAG:
                                break;
                            case   XmlPullParser.TEXT:
                                break;
                        }
                    }*/

                    String text = (new BufferedReader(
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

                    iv = findViewById(R.id.icon);
                    URL imgUrl = new URL("https://openweathermap.org/img/w/" + iconName + ".png");
                    HttpURLConnection connection = (HttpURLConnection) imgUrl.openConnection();
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200) {
                        image = BitmapFactory.decodeStream(connection.getInputStream());
                        image.compress(Bitmap.CompressFormat.PNG, 100, openFileOutput(iconName + ".png", Context.MODE_PRIVATE));
                    }


                    runOnUiThread(() -> {
                        TextView tv = findViewById(R.id.temp);
                        tv.setText("The current temperature is: " + current);
                        tv.setVisibility(View.VISIBLE);

                        tv = findViewById(R.id.minTemp);
                        tv.setText("The minimum temperature is: " + min);
                        tv.setVisibility(View.VISIBLE);

                        tv = findViewById(R.id.maxTemp);
                        tv.setText("The maximum temperature is: " + max);
                        tv.setVisibility(View.VISIBLE);

                        tv = findViewById(R.id.humidity);
                        tv.setText("The humidity is: " + humitidy + "%");
                        tv.setVisibility(View.VISIBLE);

                        tv = findViewById(R.id.description);
                        tv.setText(description);
                        tv.setVisibility(View.VISIBLE);

                        iv.setImageBitmap(image);
                        iv.setVisibility(View.VISIBLE);

                        dialog.hide();


                    });

                } catch (IOException | JSONException  e) {
                    Log.e("Connection error: ", e.getMessage());
                }


            });
        });

    }
}






