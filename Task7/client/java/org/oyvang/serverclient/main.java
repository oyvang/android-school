package org.oyvang.serverclient;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class main extends Activity {
    static private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = ((TextView)findViewById(R.id.resultView));
        //new Client().start();
    }

   static public void viewResult(Double num1, Double num2, String text){
        tv.setText(num1 + " + " + num2 + " = " + text);
    }

    public void onClickSend(View view){
        Double num1;
        Double num2;
        try {
             num1 =  Double.parseDouble(((EditText)findViewById(R.id.num1)).getText().toString());
        }catch (Exception e){
            num1 = 0.0;
        }
        try {
            num2 =  Double.parseDouble(((EditText)findViewById(R.id.num2)).getText().toString());
        }catch (Exception e){
            num2 = 0.0;
        }
        Log.e("MyClient", "Starter client");

        new Client(num1, num2).start();

    }
}
