package algonquin.cst2335.lean0016;

import androidx.appcompat.app.AppCompatActivity;


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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button mybtn = findViewById(R.id.button);
        TextView mytext = findViewById(R.id.textview);
        EditText myedit = findViewById(R.id.myedittext);
        CheckBox checkBox1 = findViewById(R.id.mycheckbox_yes);
        Switch myswitch1 = findViewById(R.id.myswitch1);
        RadioButton radioButton1 = findViewById(R.id.radiobutton1);

        mybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editString = myedit.getText().toString();
                mytext.setText("Your edit text has: " + editString);
            }
        });

            checkBox1.setOnCheckedChangeListener((btn, isChecked) -> {
                CharSequence Text = "You clicked the Checkbox and it is now:" + isChecked;
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(this, Text, duration);
                toast.show();
            });


             myswitch1.setOnCheckedChangeListener((btn, isChecked) -> {
                String Text = "You clicked the Switch and it is now:" + isChecked;
                Toast toast = Toast.makeText(this, Text, Toast.LENGTH_SHORT);
                toast.show();
        });

            radioButton1.setOnCheckedChangeListener((btn, isChecked) -> {
                String Text = "You clicked the RadioButton and it is now:" + isChecked;
                Toast toast = Toast.makeText(this, Text, Toast.LENGTH_SHORT);
                toast.show();
            });

        ImageButton imgbtn = (ImageButton) findViewById(R.id.myimagebutton);
        ImageView myimage = findViewById(R.id.myimg);
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int width = myimage.getWidth();
                int height = myimage.getHeight();
                Toast.makeText(getApplicationContext(), "The width = " + width + "and height = " + height, Toast.LENGTH_LONG).show();

            }
        });

    }


    }



