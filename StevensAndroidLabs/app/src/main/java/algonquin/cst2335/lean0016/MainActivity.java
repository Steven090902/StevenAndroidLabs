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

            checkPasswordComplexity(password);
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

        if (!foundUpperCase) {
            tv.setText("You shall not pass!");
            Toast.makeText(this, "Missing UPPERCASE letter!", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (!foundLowerCase) {
            tv.setText("You shall not pass!");
            Toast.makeText(this, "Missing LOWERCASE letter!", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (!foundNumber) {
            tv.setText("You shall not pass!");
            Toast.makeText(this, "Missing NUMBERS!", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (!foundSpecial) {
            tv.setText("You shall not pass!");
            Toast.makeText(this, "Missing SPECIAL letters", Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            tv.setText("Your password met the requirements!");
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
}