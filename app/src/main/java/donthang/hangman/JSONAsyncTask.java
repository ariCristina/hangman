package donthang.hangman;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

class JSONAsyncTask extends AsyncTask<String, Void, String> {

    StringBuilder sb = new StringBuilder();
    HttpURLConnection urlConn = null;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    private Context mContext;

    JSONAsyncTask(Context context) {
        mContext = context;
    }

    @Override
    protected String doInBackground(String... urlAndJSON ) {

        try {
            URL url = new URL(urlAndJSON[0]);
            String json = urlAndJSON[1];

            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestProperty("Content-Type", "application/json");
            urlConn.connect();

            DataOutputStream outputStream = new DataOutputStream(urlConn.getOutputStream());
            outputStream.writeBytes(json);
            outputStream.flush();
            outputStream.close();

            int HttpResult = urlConn.getResponseCode();
            if(HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream(),"utf-8"));
                String line = null;
                while((line = br.readLine()) != null) {
                    sb.append(line + '\n');
                }
                br.close();
                return "Success";
            }

            else return urlConn.getResponseMessage();
        } catch(IOException e) {
            e.printStackTrace();
            return "Error";
        }
        finally {
            if(urlConn!=null)
                urlConn.disconnect();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(mContext,result,Toast.LENGTH_LONG);
    }
}