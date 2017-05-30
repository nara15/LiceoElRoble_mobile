package com.example.joser.liceoelroble;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import Controlador.DownloadImageTask;


public class NoticiaDetailFragment extends Fragment
{
    private static final String ARGUMENT_IMAGE = "imageResId";
    private static final String ARGUMENT_NAME = "name";
    private static final String ARGUMENT_URL = "url";

    public NoticiaDetailFragment()
    {
    }

    public static NoticiaDetailFragment newInstance(String image, String name, String url)
    {
        final Bundle args = new Bundle();
        args.putString(ARGUMENT_IMAGE, image);
        args.putString(ARGUMENT_NAME, name);
        args.putString(ARGUMENT_URL, url);

        final NoticiaDetailFragment fragment = new NoticiaDetailFragment();
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_noticia_detalle,container, false);

        final ImageView imageView = (ImageView) view.findViewById(R.id.news_detail_image);
        final TextView nameTextView = (TextView) view.findViewById(R.id.news_detail_name);
        final TextView urlText = (TextView) view.findViewById(R.id.description);

        final Bundle args = getArguments();
        nameTextView.setText(args.getString(ARGUMENT_NAME));
        urlText.setText(args.getString(ARGUMENT_URL));

        

        DownloadImageTask imageTask = new DownloadImageTask(imageView);
        AsyncTask<String, Void, Bitmap> res = imageTask.execute(args.getString(ARGUMENT_IMAGE));
        try
        {
            imageView.setImageBitmap(res.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return view;
    }
}
