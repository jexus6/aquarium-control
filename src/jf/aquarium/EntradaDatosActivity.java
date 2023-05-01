package jf.aquarium;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import jf.aquarium.bd.Acuario;
import jf.aquarium.bd.BdAdaptador;

import jf.aquarium.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class EntradaDatosActivity extends Activity {
    Acuario acuario = new Acuario(); 
    BdAdaptador bd = new BdAdaptador(this);
    Date fecha = new Date();
    String fechaActualString;
    String idFoto = NO_FOTO;
    String cambioAgua= NO_CAMBIO;
    private final static String ID_ACUARIO = "aquarium1"; 
    protected static final String DIRECTORIO = "Aquarium";
    protected static final String NO_FOTO = "x";
    protected static final String NO_CAMBIO = "N";
    protected static final String SI_CAMBIO = "S";
    
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datos_layout);
        Button botonGuardar=(Button)findViewById(R.id.botonGuardar);
        ImageButton botonImagenFoto=(ImageButton)findViewById(R.id.imageButtonFoto);
        EditText editTextPH=(EditText)findViewById(R.id.editTextPH);
        TextView textFecha =(TextView)findViewById(R.id.textFecha);

        fechaActualString = DateFormat.getDateInstance().format(fecha);
        textFecha.setText(fechaActualString);
        botonImagenFoto.setOnClickListener(onFoto);
        botonGuardar.setOnClickListener(onSave);
        editTextPH.requestFocus();
    }
       
    private View.OnClickListener onSave = new View.OnClickListener() {
        public void onClick(View v) {
	    	cargaDatosAcuario();
	        bd.open();
	        bd.insertDatos(acuario);
	        bd.close();
	        switchTabInActivity(1);
        }
     };
     
     private View.OnClickListener onFoto = new View.OnClickListener() {
         public void onClick(View v) {
        	 SimpleDateFormat formateador = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        	 Intent intent = new Intent(EntradaDatosActivity.this, CamaraCaptura.class);
          	 idFoto = ID_ACUARIO+"_"+ formateador.format(fecha);
        	 intent.putExtra("nombreFichero",idFoto);
        	 startActivityForResult(intent, CamaraCaptura.CAMARA_CODIGO);
         }
         
      };
      
      public void onClickCambioAgua(View view){
    	  ImageView i = (ImageView)findViewById(R.id.imageCambioAgua);
    	  if(cambioAgua.equalsIgnoreCase(NO_CAMBIO)){  
    		  i.setImageResource(R.drawable.ic_tab_cambio_agua_selected);
    		  cambioAgua =SI_CAMBIO;
    	  }else{
    		  i.setImageResource(R.drawable.ic_tab_cambio_agua_unselected);
    		  cambioAgua =NO_CAMBIO;
    	  }
      }
     
     public void switchTabInActivity(int indexTabToSwitchTo){
         AquariumControl ParentActivity;
         ParentActivity = (AquariumControl) this.getParent();
         ParentActivity.switchTab(indexTabToSwitchTo);
     }
     
    private void cargaDatosAcuario(){
        EditText editPh=(EditText)findViewById(R.id.editTextPH);
        EditText editGh=(EditText)findViewById(R.id.editTextGH);
        EditText editNh3=(EditText)findViewById(R.id.editTextNH3);
        EditText editNo2=(EditText)findViewById(R.id.editTextNO2);
        EditText editObserv=(EditText)findViewById(R.id.editTextObservaciones);
        CheckBox checkPo= (CheckBox)findViewById(R.id.checkPotasio);
        CheckBox checkNi = (CheckBox)findViewById(R.id.checkNi);
        CheckBox checkC = (CheckBox)findViewById(R.id.checkCarbono);
        ImageButton botonImagenFoto=(ImageButton)findViewById(R.id.imageButtonFoto);
       
        acuario.setPh(editPh.getText().toString());
       	acuario.setGh(editGh.getText().toString());
       	acuario.setNh3(editNh3.getText().toString());
       	acuario.setNo2(editNo2.getText().toString());
        acuario.setObservaciones((String)(editObserv.getText().toString()));
        acuario.setAbonoC(checkC.isChecked()?1:0);
        acuario.setAbonoN(checkNi.isChecked()?1:0);
        acuario.setAbonoP(checkPo.isChecked()?1:0);
        acuario.setFoto(idFoto);
        acuario.setCambioAgua(cambioAgua);
        
        editPh.setText("");
        editGh.setText("");
        editNh3.setText("");
        editNo2.setText("");
        editObserv.setText("");
        checkPo.setChecked(false);
        checkNi.setChecked(false);
        checkC.setChecked(false);
        botonImagenFoto.setImageResource(R.drawable.ic_menu_camara);
        editPh.setFocusable(true);
        editPh.requestFocus();
        idFoto= NO_FOTO;
       
        cambioAgua=NO_CAMBIO;
     }
    
    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (requestCode==CamaraCaptura.CAMARA_CODIGO){
        	switch (resultCode) {
            case  0:
                finish();
                break;
            case -1:
                try {
                	ImageButton botonImagenFoto=(ImageButton)findViewById(R.id.imageButtonFoto);
                	String pathImg = Environment.getExternalStorageDirectory()+ File.separator + EntradaDatosActivity.DIRECTORIO + File.separator;
                	Bitmap bmpImageAcuario = BitmapFactory.decodeFile(pathImg+idFoto);
                    botonImagenFoto.setImageBitmap(bmpImageAcuario);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        	}
        }
    }
    @Override
    protected void onResume() {
            super.onResume();
            EditText editPh=(EditText)findViewById(R.id.editTextPH);
            editPh.setFocusable(true);
            editPh.requestFocus();
    }
    
}
