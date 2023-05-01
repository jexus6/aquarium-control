package jf.aquarium;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

public class CamaraCaptura extends Activity {

    protected boolean _taken = true;
    File sdImageMainDirectory;
    protected static final String FOTO_HECHA = "foto_hecha";
    protected static final Integer CAMARA_CODIGO = 666;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);   
            Bundle extras= getIntent().getExtras();
            File root = new File(Environment.getExternalStorageDirectory()+ File.separator + EntradaDatosActivity.DIRECTORIO + File.separator);
            root.mkdirs();
            sdImageMainDirectory = new File(root, extras.getString("nombreFichero"));
            startCameraActivity();
            }
         catch (Exception e) {
            finish();
            Toast.makeText(this, "Error occured. Please try again later.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    protected void startCameraActivity() {

      // Uri outputFileUri = Uri.fromFile(sdImageMainDirectory);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
      //  intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(intent, CAMARA_CODIGO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       if (requestCode==CAMARA_CODIGO){
    	switch (resultCode) {
        case  0:
            finish();
            break;
        case -1:
            try {
            	//GuardaImagen(this, Uri.parse(data.toURI()),sdImageMainDirectory);
            	GuardaImagen(this, data,sdImageMainDirectory);
            	setResult(RESULT_OK);
            } catch (Exception e) {
                e.printStackTrace();
            }
           finish();
          // startActivity(new Intent(this, EntradaDatosActivity.class));
        }
    	}
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.getBoolean(CamaraCaptura.FOTO_HECHA)) {
            _taken = true;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(CamaraCaptura.FOTO_HECHA, _taken);
    }

//    public static void GuardaImagen(Context mContext, Uri imageLoc, File imageDir) {
    public static void GuardaImagen(Context mContext, Intent data, File imageDir) {
        Bitmap bm = null;
        try {
            //bm = Media.getBitmap(mContext.getContentResolver(), imageLoc);
        	bm = (Bitmap) data.getExtras().get("data");
            FileOutputStream out = new FileOutputStream(imageDir);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
           // bm.recycle();
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
