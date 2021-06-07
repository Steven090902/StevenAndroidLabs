package algonquin.cst2335.lean0016;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;




public class MainActivity extends AppCompatActivity {
    final private static String TAG = "MainActivity";

    @Override //screen is now visible
    protected void onStart() {
        super.onStart();

        Log.w (TAG, "In onStart() - The application is now visible on screen" );

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.w("MainActivity", "In onCreate() - Loading Widgets" );

        Button loginBtn = findViewById(R.id.nextPageButton);
        EditText words = findViewById(R.id.inputEditText);

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String emailAddress = prefs.getString("LoginName", "");
        prefs.getString("LoginName", emailAddress);
        words.setText(emailAddress);


        loginBtn.setOnClickListener( clk -> {

            Intent nextPage = new Intent(MainActivity.this, SecondActivity.class);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("LoginName", String.valueOf(words.getText()));
            editor.apply();
            nextPage.putExtra("EmailAddress",words.getText().toString());

            nextPage.putExtra("Name", words.getText().toString());
            nextPage.putExtra("Age", 0);
            nextPage.putExtra("PostalCode",words.getText().toString());


            startActivityForResult( nextPage , 5739);
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Intent fromNextPage = data;
        if(requestCode == 5739) //coming back from SecondActivity
        {
            if(resultCode == 535) {
                String city = fromNextPage.getStringExtra("City");
                int age = fromNextPage.getIntExtra("Age", 0);
            }
        }
    }

    @Override  //screen is now listening for input
    protected void onResume() {
        super.onResume();
        Log.w(TAG, "In onResume() - The application is now responding to user input" );
    }
    //onStop()

    @Override
    protected void onStop() {
        super.onStop();
        Log.w(TAG,"In onStop()");
    }
    // onPause()

    @Override
    protected void onPause() {
        super.onPause();
        Log.w(TAG,"In onPause");
    }
    // onDestroy()
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w(TAG,"In onDestroy");
    }

}







