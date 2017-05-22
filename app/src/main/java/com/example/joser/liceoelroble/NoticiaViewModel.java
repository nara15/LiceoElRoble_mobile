package com.example.joser.liceoelroble;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

/**
 * Created by Jose Mario on 22/05/2017.
 */

public class NoticiaViewModel extends RecyclerView.ViewHolder
{
    private ImageView _mImageView;
    private TextView _mTextView;
    Bitmap _bitmapImage;

    public NoticiaViewModel(View itemView)
    {
        super(itemView);
        _mImageView = (ImageView) itemView.findViewById(R.id.news_image);
        _mTextView = (TextView) itemView.findViewById(R.id.news_name);
    }

    public void setData(String imageURL, String name)
    {
        _mTextView.setText(name);
        new DownloadImageTask(_mImageView).execute(imageURL);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap>
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
            _bitmapImage = result;
        }
    }
}
