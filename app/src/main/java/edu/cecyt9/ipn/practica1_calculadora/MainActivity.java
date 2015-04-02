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

    Double number1, number2, result;
    String operator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void clearText() {
        TextView numberTextView = (TextView) findViewById(R.id.numberTextView);
        TextView operationTextView = (TextView) findViewById(R.id.operationTextView);
        numberTextView.setText("");
        operationTextView.setText("");
        numberTextView.setTextColor(Color.parseColor("#555555"));
        operationTextView.setTextColor(Color.parseColor("#555555"));
    }

    public void onClickNumberButton(View numberButton) {
        Button button = (Button) numberButton;
        String number = button.getText().toString();
        TextView numberTextView = (TextView) findViewById(R.id.numberTextView);
        TextView operationTextView = (TextView) findViewById(R.id.operationTextView);
        if(!operationTextView.getText().toString().equals("")) {
            operationTextView.setText("");
            numberTextView.setText(number);
        }
        else {
            numberTextView.append(number);
        }
    }

    public void onClickDot(View dotButton) {
        TextView numberTextView = (TextView) findViewById(R.id.numberTextView);
        TextView operationTextView = (TextView) findViewById(R.id.operationTextView);
        if(operationTextView.getText().toString().equals("=")) {
            operationTextView.setText("");
            numberTextView.setText(".");
        }
        else if(!numberTextView.getText().toString().contains(".")) {
            numberTextView.append(".");
        }
        else {
            numberTextView.setTextColor(Color.parseColor("#FF0000"));
        }
    }

    public void onClickOperationButton(View operationButton) {
        Button button = (Button) operationButton;
        TextView numberTextView = (TextView) findViewById(R.id.numberTextView);
        TextView operatorTextView = (TextView) findViewById(R.id.operationTextView);
        if(operator.equals("")) {
            operator = button.getText().toString();
            String textViewContent = numberTextView.getText().toString();
            if(textViewContent.equals("")) {
                number1 = 0.0;
            }
            else {
                number1 = Double.parseDouble(textViewContent);
            }
            if(operator.equals("√") && !number1.toString().equals("0.0")) {
                operator = "*" + operator;
            }
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
        if(textViewContent.equals("") || textViewContent.equals(".")) {
            number2 = 0.0;
        }
        else {
            number2 = Double.parseDouble(textViewContent);
        }
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
        else if(operator.equals("MOD")) {
            result = number1 % number2;
        }
        else {
            result = number2;
        }
        clearText();
        result = Math.floor(result * 1000) / 1000;
        numberTextView.setText(result.toString());
        operationTextView.setText("=");
        defaultValues(result);
    }

    public void onClickReset(View resetButton) {
        clearText();
        defaultValues();
    }

    public void onClickClearScreen(View clearButton) {
        clearText();
    }
}