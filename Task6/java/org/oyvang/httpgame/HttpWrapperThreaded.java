package org.oyvang.httpgame;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.List;


public class HttpWrapperThreaded {
    final private String URL = "http://tomcat.stud.aitel.hist.no/studtomas/tallspill.jsp?";
    private HttpClient client = new DefaultHttpClient();
    private MainActivity activity;

    public HttpWrapperThreaded(MainActivity activity) {
        this.activity = activity;
    }

    public void runHttpRequestInThread(List<BasicNameValuePair> requestValues) throws Exception {
        new RequestThread().execute(requestValues);
    }

    public String httpGet(List<BasicNameValuePair> parameterList) throws IOException {
        String url = URL + URLEncodedUtils.format(parameterList, null);
        Log.e("my", "URL = " + url);
        String responseStr = "";
        HttpGet request = new HttpGet(url);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        responseStr = client.execute(request, responseHandler);

        return responseStr;
    }

    private class RequestThread extends AsyncTask<List<BasicNameValuePair>, String, String> {

        protected String doInBackground(List<BasicNameValuePair>... v) {
            try {
                return httpGet(v[0]);
            } catch (Exception e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String response) {
            activity.showResponse(response);
        }
    }


}

