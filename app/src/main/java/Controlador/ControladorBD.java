package Controlador;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import Model.BaseDatosF;
import Model.ObtencionDatosWeb;

/**
 * Created by JoseR on 3/5/2017.
 */

public class ControladorBD {

    BaseDatosF bd = new BaseDatosF();

    public ControladorBD(Context pContexto)
    {
        bd.crearArchivo(pContexto);

    }

    public void escribirLinea(Context pContexto, String pLinea)
    {

        try {
            FileOutputStream stream = new FileOutputStream(bd.crearArchivo(pContexto));
            stream.write(pLinea.getBytes());
            stream.close();
        } catch (IOException e) {
            ObtencionDatosWeb.mostrarTexto(pContexto,"Error en escribir mensaje, en la base de datos.");
        }
    }

    public String getTexto(Context pContext)
    {
        String datos = "";
        try {
            InputStream inputStream = pContext.openFileInput(bd.NombreArvhivo);
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                datos = stringBuilder.toString();
            }

        } catch (FileNotFoundException e) {
            ObtencionDatosWeb.mostrarTexto(pContext,"Nueva Base de datos creada.");
        } catch (IOException e) {
            ObtencionDatosWeb.mostrarTexto(pContext,"Error al acceder, en la base de datos.");
        } catch (Exception e) {
            ObtencionDatosWeb.mostrarTexto(pContext,"Error al acceder, en la base de datos.");
        }
        return datos;

    }
    
    
}
