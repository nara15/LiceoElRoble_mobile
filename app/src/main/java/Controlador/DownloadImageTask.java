package Controlador;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Jose Mario on 30/05/2017.
 */

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap>
{
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage)
    {
        this.bmImage = bmImage;
    }

    @Override
    protected Bitmap doInBackground(String... params)
    {
        Bitmap mIcon11 = null;
        try
        {
            InputStream in = new java.net.URL(params[0]).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result)
    {
        this.bmImage.setImageBitmap(result);
    }
}