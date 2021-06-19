package algonquin.cst2335.lean0016;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Chanreach Leang
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    /** This holds the text at the centre of the screen*/
    private TextView tv = null;
    /** This holds the edit text at the centre of the screen*/
    private EditText et = null;
    /** This holds the button at the centre of the screen*/
    private Button btn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         tv = findViewById(R.id.textView);
         et = findViewById(R.id.editText);
        btn = findViewById(R.id.button);

        btn.setOnClickListener(clk -> {
            String password = et.getText().toString();

            if(checkPasswordComplexity(password))
                tv.setText("Your password meet the requirements!");
            else
             tv.setText("You shall not pass!");
        });
    }

    /**
     * This is checkPasswordComplexity function
     *
     * @param pw The String object that we are checking
     * @return Return if the password is complex enough
     */
    boolean checkPasswordComplexity(String pw) {

        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;
        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;

        for(int i = 0; i < pw.length(); i++)
        {
            char c = pw.charAt(i);
            if(Character.isUpperCase(c)) foundUpperCase = true;
            else if(Character.isLowerCase(c)) foundLowerCase = true;
            else if(isSpecialCharacter(c)) foundSpecial = true;
            else if(isDigit(c)) foundNumber = true;
        }

        if (!foundUpperCase) {

            Toast.makeText(this, "Missing UPPERCASE letter!", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (!foundLowerCase) {

            Toast.makeText(this, "Missing LOWERCASE letter!", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (!foundNumber) {

            Toast.makeText(this, "Missing NUMBERS!", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (!foundSpecial) {

            Toast.makeText(this, "Missing SPECIAL letters", Toast.LENGTH_LONG).show();
            return false;
        }
        else {

            tv.setTextColor(getResources().getColor(R.color.Green));
            return true; //only get here if they're all true
        }
    }

    /** This is SpecialCharacter function
     *
     * @param c The object that we are checking
     * @return return if c is on of: #$%^&*!@? otherwise return false
     */
    boolean isSpecialCharacter(char c){
        switch(c) {
            case '!':
            case '@':
            case '#':
            case '$':
            case '%':
            case '^':
            case '&':
            case '*':
                return true;
            default:
                return false;
        }
    }
    boolean isDigit(char c){
        switch (c){
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                return true;
            default:
                return false;

        }
    }

}