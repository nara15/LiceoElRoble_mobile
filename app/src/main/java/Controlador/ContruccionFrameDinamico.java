package Controlador;

import android.content.Context;
import android.graphics.Color;
import android.icu.lang.UCharacter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.solver.widgets.ConstraintHorizontalLayout;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import Model.ObtencionDatosWeb;

/**
 * Created by JoseR on 25/4/2017.
 */

public class ContruccionFrameDinamico  {

    Context contexto;
    ArrayList<String> secc = new ArrayList<>();
    ArrayList<String> seccBD = new ArrayList<>();
    ControladorBD bd ;
    LinearLayout ll;

    public ContruccionFrameDinamico(Context pContext)  {
        contexto = pContext;
        bd = new ControladorBD(pContext);

    }


    public boolean isOnline()
    {

            ConnectivityManager cm =
                    (ConnectivityManager) contexto.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();


    }


    public LinearLayout actualizarContenido() {
        if(!isOnline()){
            ObtencionDatosWeb.mostrarTexto(contexto,"No hay conección a internet.");
            return null;
        }
        else{
        final ObtencionDatosWeb obtSec = new ObtencionDatosWeb();
        AsyncTask<String, Void, String> res = obtSec.execute(
                "http://liceoelroble.com/MODEL/SeccionesController.php?operacion=obtenerSecciones");
        String imp = "";

        String  Consulta  = bd.getTexto(contexto);

        try{
            JSONObject bdSecciones = new JSONObject(Consulta);
            for (int Item = 0; Item < bdSecciones.getJSONArray("Secciones").length(); Item++) {
                String seccion = bdSecciones.getJSONArray("Secciones").getJSONObject(Item).getString("Seccion");
                seccBD.add(seccion);
            }
        }
        catch (JSONException e)
        {

        }
        try {

            String ress = res.get();

            if(ress==""){
                return null;
            }

            imp = "{Secciones:"+ress+"}";

            JSONObject json = new JSONObject(imp);
            final LinearLayout ll = new LinearLayout(contexto);

            ll.setOrientation(LinearLayout.VERTICAL);

            ArrayList<String> suscrito = new ArrayList<>();

            TextView susTxtVie = new TextView(contexto.getApplicationContext());
            susTxtVie.setText("Disponibles");
            susTxtVie.setTextSize((float) 30);
            susTxtVie.setTextColor(Color.WHITE);

            TextView linTxtVie = new TextView(contexto.getApplicationContext());
            linTxtVie.setText("_____________________________________________________");
            linTxtVie.setTextColor(Color.WHITE);

            ll.addView(susTxtVie);
            ll.addView(linTxtVie);

            for (int Item = 0; Item < json.getJSONArray("Secciones").length(); Item++) {

                final String seccion = json.getJSONArray("Secciones").getJSONObject(Item).getString("Seccion");

                Switch sw = new Switch(contexto.getApplicationContext());
                sw.setBackgroundColor(Color.green(5));
                sw.setTextColor(Color.WHITE);
                sw.setText(seccion);
                sw.setTextSize((float) 25);
                if (seccBD.contains(seccion)) {

                    sw.setTag("NoSus");
                    sw.setChecked(true);


                }
                if (!seccBD.contains(seccion)) {
                    sw.setTag("Sus");
                    sw.setChecked(false);

                }
                ll.addView(sw);
                sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            if (!seccBD.contains((String)buttonView.getText())) {

                                String seccion = (String) buttonView.getText();
                                seccBD.add(seccion);
                                guardarCambios();
                                try {
                                    FirebaseMessaging.getInstance().subscribeToTopic(parse(seccion));
                                    ObtencionDatosWeb.mostrarTexto(contexto.getApplicationContext(), "Se ha suscrito a: " + seccion/* + " ID:" + id*/);//+" -Seg "
                                }
                                catch (Exception e){
                                    ObtencionDatosWeb.mostrarTexto(contexto.getApplicationContext(), "Ha suscedido un error " + e.toString()/* + " ID:" + id*/);//+" -Seg "
                                }

                            } else {

                                ObtencionDatosWeb.mostrarTexto(contexto.getApplicationContext(), "Ya está suscrito a esta sección");
                            }
                        } else {

                            seccBD.remove((String)buttonView.getText());
                            guardarCambios();

                            FirebaseMessaging.getInstance().unsubscribeFromTopic(parse(seccion));

                            ObtencionDatosWeb.mostrarTexto(contexto.getApplicationContext(), "  Se ha dessuscrito a: " + buttonView.getText());

                        }

                    }
                });

            }
           /**/
            //http://stackoverflow.com/questions/13226353/android-checkbox-dynamically
            // cargar dinamicamente arr
            return ll;
        } catch (InterruptedException e) {
            imp = e.getMessage();
            ObtencionDatosWeb.mostrarTexto(contexto.getApplicationContext(), imp);
            return null;
        } catch (ExecutionException e) {
            imp = e.getMessage();
            ObtencionDatosWeb.mostrarTexto(contexto.getApplicationContext(), imp);
            return null;
        } catch (Exception e) {
            imp = e.getMessage();
            ObtencionDatosWeb.mostrarTexto(contexto.getApplicationContext(), imp);
            return null;
        }


    }}

    public LinearLayout getLiner(){
        return ll;
    }

    private void guardarCambios(){

        try {
            JSONArray secciones = new JSONArray();

            for (int i = 0; i < seccBD.size(); i++) {
                JSONObject seccion = new JSONObject();
                seccion.put("Seccion", seccBD.get(i));
                secciones.put(seccion);
            }
            JSONObject Principal = new JSONObject();
            Principal.put("Secciones", secciones);
            bd.escribirLinea(contexto,Principal.toString());


        } catch (JSONException e) {
            ObtencionDatosWeb.mostrarTexto(contexto,"Ha sucedido un error al guardar los datos.");
        }

    }

    private String parse(String pEntrada){
        for(int i =0; i < pEntrada.length()-1;i++){
            if(pEntrada.substring(i,i+1).equals(" ")){
                pEntrada = pEntrada.substring(0,i)+ ""+pEntrada.substring(i+1,pEntrada.length());
            }

        }
        return pEntrada;
    }


}
