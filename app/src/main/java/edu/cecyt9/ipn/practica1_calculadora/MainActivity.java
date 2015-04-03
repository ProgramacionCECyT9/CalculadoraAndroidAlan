package edu.cecyt9.ipn.practica1_calculadora;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    // Calculator variables
    Double number1, number2, result;
    String operator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Variables inizialitation
        defaultValues();
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

    // Inicialization methods
    public void defaultValues() {
        number1 = 0.0;
        number2 = 0.0;
        result = 0.0;
        operator = "";
    }

    public void defaultValues(Double new_number1) {
        number1 = new_number1;
        number2 = 0.0;
        result = 0.0;
        operator = "";
    }

    // Clearing text views
    public void clearText() {
        TextView numberTextView = (TextView) findViewById(R.id.numberTextView);
        TextView operationTextView = (TextView) findViewById(R.id.operationTextView);
        numberTextView.setText("");
        operationTextView.setText("");
        numberTextView.setTextColor(Color.parseColor("#555555"));
        operationTextView.setTextColor(Color.parseColor("#555555"));
    }

    // Add digits to the current number
    public void onClickNumberButton(View numberButton) {
        // Get the number from button's text
        Button button = (Button) numberButton;
        String number = button.getText().toString();
        TextView numberTextView = (TextView) findViewById(R.id.numberTextView);
        TextView operationTextView = (TextView) findViewById(R.id.operationTextView);
        if(operationTextView.getText().toString().equals("=")) {
            operationTextView.setText("");
            numberTextView.setText(number);
        }
        else {
            numberTextView.append(number);
        }
    }

    // Add a decimal point to the current number
    public void onClickPointButton(View pointButton) {
        TextView numberTextView = (TextView) findViewById(R.id.numberTextView);
        TextView operationTextView = (TextView) findViewById(R.id.operationTextView);
        // Add the point in a new number after the result of the previous operation is shown
        if(operationTextView.getText().toString().equals("=")) {
            operationTextView.setText("");
            numberTextView.setText(".");
        }
        // Allows just one decimal point in the number
        else if(numberTextView.getText().toString().contains(".")) {
            numberTextView.setTextColor(Color.parseColor("#FF0000"));
        }
        else {
            numberTextView.append(".");
        }
    }

    public void onClickOperationButton(View operationButton) {
        // Get the operation symbol from button's text
        Button button = (Button) operationButton;
        TextView numberTextView = (TextView) findViewById(R.id.numberTextView);
        TextView operatorTextView = (TextView) findViewById(R.id.operationTextView);
        // Allows just one operation before do click on equals button
        if(operator.equals("")) {
            operator = button.getText().toString();
            String textViewContent = numberTextView.getText().toString();
            // If the text view is empty, takes number1 as 0
            if(textViewContent.equals("")) {
                number1 = 0.0;
            }
            else {
                number1 = Double.parseDouble(textViewContent);
            }
            // If there is another number before square root operation
            // it will be multiply by the second number's square root
            if(operator.equals("√") && !number1.toString().equals("0.0")) {
                operator = "*" + operator;
            }
            // Show the operation symbol in screen and clear the number text view
            operatorTextView.setText(operator);
            numberTextView.setText("");
            numberTextView.setTextColor(Color.parseColor("#555555"));
        }
        else {
            operatorTextView.setTextColor(Color.parseColor("#FF0000"));
        }
    }

    public void onClickEqualsButton(View equalsButton) {
        TextView numberTextView = (TextView) findViewById(R.id.numberTextView);
        TextView operationTextView = (TextView) findViewById(R.id.operationTextView);
        String textViewContent = numberTextView.getText().toString();
        // If the number text view is empty or is just a point
        // the second number will be 0
        if(textViewContent.equals("") || textViewContent.equals(".")) {
            number2 = 0.0;
        }
        else {
            number2 = Double.parseDouble(textViewContent);
        }
        // Do the correct math operation
        if(operator.equals("+")) {
            result = number1 + number2;
        }
        else if(operator.equals("-")) {
            result = number1 - number2;
        }
        else if(operator.equals("*")) {
            result = number1 * number2;
        }
        else if(operator.equals("/")) {
            // Prevent 0/0 error
            try {
                result = number1 / number2;
            }catch(ArithmeticException ae){
                result = 0.0;
            }
        }
        else if(operator.equals("^")) {
            result = Math.pow(number1, number2);
        }
        else if(operator.equals("√")) {
            result = Math.sqrt(number2);
        }
        else if(operator.equals("*√")) {
            result = number1 * Math.sqrt(number2);
        }
        else if(operator.equals("mod")) {
            result = number1 % number2;
        }
        // If there's no a selected operation, the result 
        // will be te last number introduced
        else {
            result = number2;
        }
        // Clear screen 
        clearText();
        // Takes just three digits after decimal point
        result = Math.floor(result * 1000) / 1000;
        numberTextView.setText(result.toString());
        operationTextView.setText("=");
        // Allows to take the current result as the first number
        // of a new operation
        defaultValues(result);
    }

    // Methods for clear buttons
    public void onClickReset(View resetButton) {
        clearText();
        defaultValues();
    }

    public void onClickClearScreen(View clearButton) {
        clearText();
    }
}