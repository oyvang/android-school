package org.oyvang.httpgame;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
    private HttpWrapperThreaded network;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        network = new HttpWrapperThreaded(this);
    }

    public void showResponse(String response) {
        UpdateStatusText(response);
        Log.i("my", "Server responded with: " + response);

    }

    public void onClickSendUserInfo(View view) {
        String name = ((EditText) findViewById(R.id.name_field)).getText().toString();
        String cardNumber = ((EditText) findViewById(R.id.card_number_field)).getText().toString();
        if (!name.isEmpty() && !cardNumber.isEmpty()) {
            try {
                network.runHttpRequestInThread(createRequestValues(name, cardNumber));
                setContentView(R.layout.game_guess);
            } catch (Exception e) {
                Log.e("my", "feil i nettwork  " + e);
            }

        }

    }

    public void onClickSendGuess(View view) {
        String number = ((EditText) findViewById(R.id.guess_number_field)).getText().toString();
        if (!number.isEmpty()) {
            try {
                network.runHttpRequestInThread(createRequestValues(number));
                EditText et = (EditText) findViewById(R.id.guess_number_field);
                et.setText("");
            } catch (Exception e) {
                Log.e("my", "feil i nettwork  " + e);
            }

        }
    }

    public void UpdateStatusText(String replayInfo) {
        TextView replayField = ((TextView) findViewById(R.id.game_info_textview));
        replayField.setText(replayInfo);
    }


    private List<BasicNameValuePair> createRequestValues(String name, String cardNumber) {
        List<BasicNameValuePair> valueList = new ArrayList<BasicNameValuePair>();
        valueList.add(new BasicNameValuePair("navn", name));
        valueList.add(new BasicNameValuePair("kortnummer", cardNumber));
        return valueList;
    }

    private List<BasicNameValuePair> createRequestValues(String guess) {
        List<BasicNameValuePair> valueList = new ArrayList<BasicNameValuePair>();
        valueList.add(new BasicNameValuePair("tall", guess));
        return valueList;
    }

    public void onClickRestart(View v) {
        setContentView(R.layout.activity_main);
    }

}
