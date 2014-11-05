package org.oyvang.oving2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class calculator extends Activity {
    final CharSequence[] valg = {"add", "sub", "mult"};
    private double tall1,tall2,svar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        fillText();
    }

    public void fillText(){
        tall1 = 0;
        tall2 = 0;
        svar = 0;
        fillTextBox(tall1, tall2, svar);
    }

    private void fillTextBox(Double tall1, Double tall2, Double svar) {
        EditText txtTall1 = (EditText) findViewById(R.id.txtTall1);
        EditText txtTall2 = (EditText) findViewById(R.id.txtTall2);
        TextView txtsvar = (TextView) findViewById(R.id.txtSvar);
        if(tall1!=null){
            txtTall1.setText(String.valueOf(tall1));
        }
        if(tall2!=null){
            txtTall2.setText(String.valueOf(tall2));
        }
        if(svar!=null){
            txtsvar.setText(String.valueOf(svar));
        }
    }

    public void onClickSvar(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setItems(valg, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {
                //Toast.makeText(getApplicationContext(), valg[i], Toast.LENGTH_SHORT).show();
                switch (i) {
                    case 0:
                        add();
                        break;
                    case 1:
                        sub();
                        break;
                    case 2:
                        mult();
                        break;
                }
            }
        });
        AlertDialog alert = builder.create();

        alert.show();
    }

    public void onClickTall1(View v){
        Intent i = new Intent(this, Random.class);
        i.putExtra("max", 10.0);
        startActivityForResult(i, 1);
    }

    public void onClickTall2(View v){
        Intent i = new Intent(this, Random.class);
        i.putExtra("max",10.0);
        startActivityForResult(i, 2);
    }

    private void add() {
        double tall1 = Double.parseDouble(((EditText)findViewById(R.id.txtTall1)).getText().toString());
        double tall2 = Double.parseDouble(((EditText)findViewById(R.id.txtTall2)).getText().toString());
        fillTextBox(tall1, tall2, tall1 + tall2);
    }

    private void sub() {
        double tall1 = Double.parseDouble(((EditText)findViewById(R.id.txtTall1)).getText().toString());
        double tall2 = Double.parseDouble(((EditText)findViewById(R.id.txtTall2)).getText().toString());
        fillTextBox(tall1, tall2, tall1-tall2);
    }

    private void mult() {
        double tall1 = Double.parseDouble(((EditText)findViewById(R.id.txtTall1)).getText().toString());
        double tall2 = Double.parseDouble(((EditText)findViewById(R.id.txtTall2)).getText().toString());
        fillTextBox(tall1, tall2, tall1*tall2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.calculator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.title_activity_random:
                startActivity(new Intent(this, Random.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case 1:
                Double o = Double.parseDouble(((EditText)findViewById(R.id.txtTall1)).getText().toString());
                fillTextBox(data.getDoubleExtra("num", 0.0), null, null);
                break;
            case 2:
                Double i = Double.parseDouble(((EditText)findViewById(R.id.txtTall2)).getText().toString());
                fillTextBox(null, data.getDoubleExtra("num", 0.0), null);
                break;
        }
    }
}
