package org.oyvang.friendlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;


public class addFriend extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_person);
    }

    public void onClickSave(View v) {
        String name = ((EditText) findViewById(R.id.name_input_str)).getText().toString();
        DatePicker date = (DatePicker) findViewById(R.id.datePicker);
        Integer d = date.getDayOfMonth();
        Integer m = date.getMonth();
        Integer y = date.getYear();
        if (name.isEmpty() || d == null || m == null || y == null) {
            Toast toast = Toast.makeText(getApplicationContext(), "ERROR: Wrong name or date", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();

        } else {
            Intent i = new Intent(this, main.class);
            i.putExtra("name", name);
            i.putExtra("day", d);
            i.putExtra("month", m);
            i.putExtra("year", y);
            setResult(RESULT_OK, i);
            finish();
        }
    }


}
