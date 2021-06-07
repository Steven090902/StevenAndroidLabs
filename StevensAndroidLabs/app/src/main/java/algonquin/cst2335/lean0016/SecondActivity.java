package algonquin.cst2335.lean0016;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.SharedPreferences;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {
    private Bitmap mBitmap;
    ImageView profileImage;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 5739){
            if(resultCode == RESULT_OK){
                Bitmap thumbnail = data.getParcelableExtra("data");


                profileImage.setImageBitmap(thumbnail);

                FileOutputStream fOut = null;
                try {
                    fOut = openFileOutput( "Picture.png", Context.MODE_PRIVATE);
                    thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                    fOut.flush();
                    fOut.close();
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView topOfScreen = findViewById(R.id.textView);

        Intent fromPrevious = getIntent(); //gets the intent that caused the transition
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        profileImage = findViewById(R.id.imageView);
        EditText editText = findViewById(R.id.editTextPhone);

        String emailAddress = fromPrevious.getStringExtra("EmailAddress");
        int age = fromPrevious.getIntExtra("Age", 0);
        String name = fromPrevious.getStringExtra("Name");
        String pCode = fromPrevious.getStringExtra("PostalCode");


        topOfScreen.setText("Welcome back " + emailAddress);

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String phoneNum = prefs.getString("phoneNumber", "");
        prefs.getString("phoneNumber", phoneNum);
        Button btn1 = findViewById(R.id.button);

        editText.setText(phoneNum);

        btn1.setOnClickListener( clk -> {
            Intent call = new Intent(Intent.ACTION_DIAL);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("phoneNumber", String.valueOf(editText.getText()));
            editor.apply();
            String phonenumber = editText.getText().toString();
            call.setData(Uri.parse("tel:" + phonenumber ));
            startActivity(call);

        });
        Button btn2 = findViewById(R.id.button2);
        btn2.setOnClickListener( click ->
        {   //one line to return
            Intent dataBack = new Intent();
            dataBack.putExtra("Age", 0);
            dataBack.putExtra("City", "Ottawa");

            setResult(535, dataBack);//send data back to first page
            //  finish(); //actuall goes back
            //onActivityResult() in first page is next
            startActivityForResult(cameraIntent, 5739);
        });

        String filename =getFilesDir().getPath(); //where you're installed on disk
        File file = new File(filename + "/Picture.png");
        if(file.exists()) {
            Bitmap theImage = BitmapFactory.decodeFile(filename+"/Picture.png");
            profileImage.setImageBitmap(theImage);
        }





    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String phoneNum = prefs.getString("phoneNumber", "");
        prefs.getString("phoneNumber", phoneNum);
    }
}
