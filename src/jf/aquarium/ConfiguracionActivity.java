package jf.aquarium;

import jf.aquarium.R;
import jf.aquarium.alarma.GestorAlarma;
import jf.aquarium.bd.Acuario;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


public class ConfiguracionActivity extends Activity {
	private SharedPreferences pref;
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        setContentView(R.layout.configuracion_layout);
        
     	EditText editPhMax = (EditText) findViewById(R.id.editTextPHMax);
    	EditText editPhMin = (EditText) findViewById(R.id.editTextPHMin);
    	EditText editGhMax = (EditText) findViewById(R.id.editTextGHMax);
    	EditText editGhMin = (EditText) findViewById(R.id.editTextGHMin);
    	EditText editNh3 = (EditText) findViewById(R.id.editTextNH3);
    	EditText editNo2 = (EditText) findViewById(R.id.editTextNO2);
    	
        
     	editPhMax.setText((String) pref.getString(Acuario.PH_MAX, ""));
    	editPhMin.setText((String) pref.getString(Acuario.PH_MIN, ""));
    	editGhMax.setText((String) pref.getString(Acuario.GH_MAX, ""));
    	editGhMin.setText((String) pref.getString(Acuario.GH_MIN, ""));
    	editNh3.setText((String) pref.getString(Acuario.NH3_MAX, ""));
    	editNo2.setText((String) pref.getString(Acuario.NO2_MAX , ""));
    	
    	
        
    }
    
    public void onClickGuardar(View v){
    	final Editor edPref =  pref.edit();
    	
    	EditText editPhMax = (EditText) findViewById(R.id.editTextPHMax);
    	EditText editPhMin = (EditText) findViewById(R.id.editTextPHMin);
    	EditText editGhMax = (EditText) findViewById(R.id.editTextGHMax);
    	EditText editGhMin = (EditText) findViewById(R.id.editTextGHMin);
    	EditText editNh3 = (EditText) findViewById(R.id.editTextNH3);
    	EditText editNo2 = (EditText) findViewById(R.id.editTextNO2);
    	
    	if(!editPhMax.getText().toString().equalsIgnoreCase("")) 
    		edPref.putString(Acuario.PH_MAX,(String) editPhMax.getText().toString());
    	if(!editPhMin.getText().toString().equalsIgnoreCase("")) 
    		edPref.putString(Acuario.PH_MIN,(String) editPhMin.getText().toString());
    	if(!editGhMax.getText().toString().equalsIgnoreCase("")) 
    		edPref.putString(Acuario.GH_MAX,(String) editGhMax.getText().toString());
    	if(!editGhMin.getText().toString().equalsIgnoreCase(""))
    		edPref.putString(Acuario.GH_MIN,(String) editGhMin.getText().toString());
    	if(!editNh3.getText().toString().equalsIgnoreCase(""))
    		edPref.putString(Acuario.NH3_MAX,(String) editNh3.getText().toString());
    	if(!editNo2.getText().toString().equalsIgnoreCase(""))
    		edPref.putString(Acuario.NO2_MAX,(String) editNo2.getText().toString());
    	edPref.putString(Acuario.NH3_MIN,"0");
    	edPref.putString(Acuario.NO2_MIN,"0");
    	
    	edPref.commit();
    	Toast.makeText(this, getResources().getString(R.string.avisoValoresActualizados), Toast.LENGTH_LONG).show();
    }
    
    public void onClickAbonadoPo(View v) {
    	muestraDialog(Acuario.TipoAbonado.P);
    }
    
    public void onClickAbonadoNi(View v) {
    	muestraDialog(Acuario.TipoAbonado.N);
    }
    
    public void onClickAbonadoC(View v) {
    	muestraDialog(Acuario.TipoAbonado.C);
    }
            
    public void muestraDialog(Acuario.TipoAbonado tipoAbonado) {
    	final Acuario.TipoAbonado tipo = tipoAbonado; 
    	final Editor edPref =  pref.edit();
    	final boolean[] diasAbono={false, false, false, false, false, false, false};    			
    	//d es dia de la semana lunes:1 ...
    	int d;
    	
    	for (int i =0; i<7; i++){
    		d=i+1;
    		diasAbono[i] = pref.getBoolean(tipo+"_"+d, false);
    	}
    	AlertDialog.Builder  dialog = new AlertDialog.Builder(this);
    	
    	//Poner el acceso al abonado en la clase Acuario que devuelba int o str
    	dialog.setMultiChoiceItems  (getResources().getStringArray(R.array.arraySemana), diasAbono,
    								new DialogInterface.OnMultiChoiceClickListener() {
    											public void onClick(DialogInterface dialog,	int whichButton, boolean isChecked) {
    															};
    								});
    	dialog.setPositiveButton(getResources().getText(R.string.literalAceptar), new DialogInterface.OnClickListener() {
			    		public void onClick(DialogInterface dialog, int which) {
			    			ListView list = ((AlertDialog)dialog).getListView();
			    			int d ;
			    			for (int i=0; i<list.getCount(); i++) {
			    				d=i+1;
			    				diasAbono[i] = list.isItemChecked(i);
			    				edPref.putBoolean(tipo+"_"+d, list.isItemChecked(i));
			    				edPref.commit();
			    				new GestorAlarma(ConfiguracionActivity.this).setAlarma(pref.getBoolean("P_"+d,false ),pref.getBoolean("N_"+d,false),pref.getBoolean("C_"+d,false),d); 
			    			}
			       		}
		    		});
    	dialog.show();
    }
    
    
    
    
    
    
    
}