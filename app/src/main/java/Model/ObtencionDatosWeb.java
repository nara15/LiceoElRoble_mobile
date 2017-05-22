package Model;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by JoseR on 14/4/2017.
 */

public class ObtencionDatosWeb extends AsyncTask<String, Void, String> {
    final String ERROR = "Error al leer";
    String response = "";

    @Override
    protected String doInBackground(String... params) {
        URL urlL = null;
        try {
            urlL = new URL(params[0]);//"http://chmi.cz..../"
            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection) urlL.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                response = readStream(in);
                return response;
            } catch (IOException e) {
                //throw new RuntimeException(e);
                return ERROR+" Error en el IO";
            } catch (Exception e){
                if (urlConnection != null)
                    urlConnection.disconnect();
                return ERROR+" Error en el link "+e.getMessage();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return ERROR;
        }

    }


    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while (i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }

    }
    public static void mostrarTexto(Context pContext, String pMensaje){
        Toast toast=Toast.makeText(pContext,pMensaje,Toast.LENGTH_SHORT);
        toast.setMargin(50,50);
        toast.show();

    }
}
