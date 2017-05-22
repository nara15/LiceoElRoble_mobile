package Model;

import android.content.Context;

import java.io.File;

/**
 * Created by JoseR on 3/5/2017.
 */

public class BaseDatosF  {

    static File path;
    public static String NombreArvhivo = "BD-LR.txt";



    public static File crearArchivo(Context pContext) {
        BaseDatosF.path = pContext.getFilesDir();
        File file = new File(BaseDatosF.path, NombreArvhivo);
        return file;
    }
}
