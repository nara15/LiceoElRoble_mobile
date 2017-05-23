package com.example.joser.liceoelroble;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import Controlador.ControladorBD;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NoticiaListFragment.OnNoticiaListener} interface
 * to handle interaction events.
 * Use the {@link NoticiaListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoticiaListFragment extends Fragment
{
    private ArrayList<Model.Noticia> _noticias;
    private OnNoticiaListener mListener;
    Context contexto;

    public NoticiaListFragment()
    {

    }

    public static NoticiaListFragment newInstance()
    {
        return new NoticiaListFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        ControladorBD vbd = new ControladorBD(contexto);
        String hola = vbd.getTexto(contexto);
        System.out.println("Holaaaaaaaa " + hola);

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_noticias_list, container, false);
        final Activity activity = getActivity();
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(activity,2));

        _noticias = new ArrayList<>();
        new JSONAsyncTask().execute("http://microblogging.wingnity.com/JSONParsingTutorial/jsonActors");

        NoticiaAdapter noticiaAdapter = new NoticiaAdapter(activity);
        noticiaAdapter.setListener(mListener);
        noticiaAdapter.set_noticias(_noticias);

        recyclerView.setAdapter(noticiaAdapter);

        return view;
    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        contexto = this.getContext();
        if (context instanceof OnNoticiaListener)
        {
            mListener = (OnNoticiaListener) context;

        } else
        {
            throw new RuntimeException(context.toString()
                    + " must implement OnNoticiaListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnNoticiaListener
    {
        void OnNoticiaListenerSelected(String imageResId, String name, String description);
    }

    private class JSONAsyncTask extends AsyncTask<String, Void, Boolean>
    {
        String result;
        ProgressDialog dialog;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Cargando, espera por favor");
            dialog.setTitle("Conectando al servicio");
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(String... params)
        {
            URL url = null;
            try
            {
                url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                result = readStream(in);


                JSONObject jsonRes = new JSONObject(result);
                JSONArray jsonArray = jsonRes.getJSONArray("actors");

                for (int i = 0; i < jsonArray.length(); i ++)
                {
                    JSONObject object = jsonArray.getJSONObject(i);

                    Model.Noticia actor = new Model.Noticia();
                    actor.set_name(object.getString("name"));
                    actor.set_newsImageURL(object.getString("image"));

                    _noticias.add(actor);
                }

                return true;
            }
            catch(MalformedURLException e)
            {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result)
        {
            dialog.dismiss();

            if (result == false)
            {
                Toast.makeText(getActivity(), "No se puede conectar al servidor", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getActivity(), "Funciona " + this.result, Toast.LENGTH_LONG).show();
            }

        }

        private String readStream(InputStream is)
        {
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
    }
}
