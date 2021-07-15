package algonquin.cst2335.lean0016;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    EditText edit;
    TextView tv;
    Button btn;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.textView);
        btn = findViewById(R.id.theButton);
        edit = findViewById(R.id.editText);


        btn.setOnClickListener( click -> {



            //still on GUI thread, can't connect to server here
            Executor newThread = Executors.newSingleThreadExecutor();
            newThread.execute(  ( ) -> {

                URL url = null;
                try {

                    String serverURL = "https://api.openweathermap.org/data/2.5/weather?q="
                            + URLEncoder.encode(edit.getText().toString(), "UTF-8")
                            + "&appid=7e943c97096a9784391a981c4d878b22&units=metric&mode=xml";



                    url = new URL(serverURL);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());



                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

                    factory.setNamespaceAware(false);


                    XmlPullParser xpp = factory.newPullParser();

                    xpp.setInput( in  , "UTF-8");

                    String current;
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

                                if(xpp.getName().equals("temperature")) //which opening tag are we looking at?
                                {
                                    current = xpp.getAttributeValue(null, "value");
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
                    }


                        String text = (new BufferedReader(
                                new InputStreamReader(in, StandardCharsets.UTF_8)))
                                .lines()
                                .collect(Collectors.joining("\n"));

                        JSONObject theDocument = new JSONObject( text );
                        JSONObject coord = theDocument.getJSONObject("coord");
                        double lat = coord.getDouble("lat");
                        double lon = coord.getDouble("lon");

                        JSONArray weatherArray = theDocument.getJSONArray("weather");
                        JSONObject obj0 = weatherArray.getJSONObject(0);
                        JSONObject main = theDocument.getJSONObject("main");
                        double currentTemp = main.getDouble("temp");
                        double min = main.getDouble("temp_min");
                        double max = main.getDouble("temp_max");




                }
                catch (IOException | XmlPullParserException | JSONException e) {
                    e.printStackTrace();
                }

            }  );




        });

    }
}




