package com.example.joser.liceoelroble;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;


public class NoticiaDetailFragment extends Fragment
{
    private static final String ARGUMENT_IMAGE = "imageResId";
    private static final String ARGUMENT_NAME = "name";

    public NoticiaDetailFragment()
    {
    }

    public static NoticiaDetailFragment newInstance(String image, String name)
    {
        final Bundle args = new Bundle();
        args.putString(ARGUMENT_IMAGE, name);
        args.putString(ARGUMENT_NAME, name);

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

        final Bundle args = getArguments();
        nameTextView.setText(args.getString(ARGUMENT_NAME));

        return view;
    }






}
