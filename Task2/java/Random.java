package org.oyvang.oving2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Random extends Activity {

    Boolean calcIntent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);
        try{
            toast((int)getIntent().getExtras().getDouble("max"));
            finish();
        }catch (Exception e){
            Log.d("MyDebug", ""+e); // should be NullPointerException on app startup
        }

        final Button button = (Button) findViewById(R.id.generateButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                toast(100);
            }
        });
    }

    private void toast(int max){
        Toast.makeText(getApplicationContext(), String.valueOf(generateRandomNr(max)), Toast.LENGTH_SHORT).show();
    }

    private int generateRandomNr(int max){
        java.util.Random r = new java.util.Random();
        int rand = r.nextInt(max);
            Intent calc = new Intent(this, calculator.class);
            calc.putExtra("num", (double)rand);
            setResult(RESULT_OK, calc);
        return rand;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.random, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.title_activity_calculator:
                startActivity(new Intent(this, calculator.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
