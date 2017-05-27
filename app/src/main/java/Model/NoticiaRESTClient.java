package Model;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Jose Mario on 27/05/2017.
 */

public class NoticiaRESTClient extends AsyncTask<String, Void, String>
{
    private String _jsonToSend;

    public NoticiaRESTClient(String _jsonToSend)
    {
        this._jsonToSend = _jsonToSend;
    }

    @Override
    protected String doInBackground(String... params)
    {
        URL url = null;
        HttpURLConnection urlConnection = null;
        try
        {
            url = new URL(params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setInstanceFollowRedirects(false);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.setRequestProperty("charset", "utf-8");
            urlConnection.setUseCaches (false);

            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream ());
            wr.writeBytes(this._jsonToSend);
            wr.flush();
            wr.close();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String result = readStream(in);
            return result;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return "ERROR";
        }
    }

    private String readStream(InputStream is)
    {
        try
        {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while (i != -1)
            {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e)
        {
            return "";
        }

    }
}
