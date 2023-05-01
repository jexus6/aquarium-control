package jf.aquarium;


import java.io.File;
import java.util.List;

import jf.aquarium.bd.Acuario;
import jf.aquarium.bd.BdAdaptador;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class VisualizacionDatosActivity extends Activity {
	public static final String KEY_ROWID = "_id";
	public static final String KEY_FECHA = "fecha";
	public static final String KEY_PH = "ph";
	private BdAdaptador datasource;
	private ListView listViewListado;
	
	private void mostrarDatos(){
		datasource.open();
		List<Acuario> values = datasource.getAllParametros();
		listViewListado = (ListView)findViewById(R.id.listViewListado);
		listViewListado.setAdapter(new AdapatadorListado(this, R.layout.listado_fila_layout,values));
	}
	
	public class AdapatadorListado extends ArrayAdapter<Acuario>{
		int layoutResourceId;
		List<Acuario> acuario= null;
		Context context;
		
		public AdapatadorListado (Context p_context, int layoutResourceId, List<Acuario> acuario) {
				super(p_context,  layoutResourceId, acuario);
				this.layoutResourceId = layoutResourceId;
				this.acuario= acuario;
				this.context = p_context;
			}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View fila = convertView;
			LayoutInflater inflater=getLayoutInflater();
			fila = inflater.inflate(R.layout.listado_fila_layout,parent, false);
			TextView textFecha = (TextView)fila.findViewById(R.id.textFechaFila);
			TextView textFechaHora = (TextView)fila.findViewById(R.id.textFechaFilaHora);
			TextView textPH = (TextView)fila.findViewById(R.id.textViewPH);
			TextView textGH = (TextView)fila.findViewById(R.id.textViewGH);
			TextView textNH3 = (TextView)fila.findViewById(R.id.textViewNH3);
			TextView textNO2 = (TextView)fila.findViewById(R.id.textViewNO2);
			ImageView imageAcuario = (ImageView)fila.findViewById(R.id.imageFoto);
			ImageView imageCambio = (ImageView)fila.findViewById(R.id.imageCambioAgua);
			ImageView imageAbonoN = (ImageView)fila.findViewById(R.id.imageAbonoN);
			ImageView imageAbonoP = (ImageView)fila.findViewById(R.id.imageAbonoP);
			ImageView imageAbonoC = (ImageView)fila.findViewById(R.id.imageAbonoC);
			
			LinearLayout layoutContenido = (LinearLayout) fila.findViewById(R.id.linearLayoutContenido);
			LinearLayout layoutAbono = (LinearLayout) fila.findViewById(R.id.linearLayoutAbono);
			layoutContenido.setOnClickListener(new  OnClickObservaciones (acuario.get(position).getObservaciones(),acuario.get(position).getFecha(), context));		
			layoutAbono.setOnClickListener(new  OnClickObservaciones (acuario.get(position).getObservaciones(),acuario.get(position).getFecha(), context));
			
			String pathImg = Environment.getExternalStorageDirectory()+ File.separator + EntradaDatosActivity.DIRECTORIO + File.separator;
			File fileImg = new File (pathImg+acuario.get(position).getFoto());
			if(fileImg.exists()){
				Bitmap bmpImageAcuario = BitmapFactory.decodeFile(pathImg+acuario.get(position).getFoto());
				imageAcuario.setImageBitmap(bmpImageAcuario);
				imageAcuario.setOnClickListener(new OnClickImagen(fileImg, context));
			}
			textFecha.setText(acuario.get(position).getFecha().substring(0, 10));
			textFechaHora.setText(acuario.get(position).getFecha().substring(10, 19));
			textPH.setText(String.valueOf(acuario.get(position).getPh()));
			textGH.setText(String.valueOf(acuario.get(position).getGh()));
			textNH3.setText(String.valueOf(acuario.get(position).getNh3()));
			textNO2.setText(String.valueOf(acuario.get(position).getNo2()));
				
			if (acuario.get(position).getAbonoN()==1)
				imageAbonoN.setImageResource(R.drawable.ic_listado_n_selected);
			if (acuario.get(position).getAbonoP()==1)
				imageAbonoP.setImageResource(R.drawable.ic_listado_p_selected);
			if (acuario.get(position).getAbonoC()==1)
				imageAbonoC.setImageResource(R.drawable.ic_listado_c_selected);
			if (acuario.get(position).getCambioAgua().equalsIgnoreCase(EntradaDatosActivity.SI_CAMBIO))
				imageCambio.setImageResource(R.drawable.ic_tab_cambio_agua_selected);
			
			return fila;
		}
	}
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listado_layout);
        datasource = new BdAdaptador(this);
     	datasource.open();
 		mostrarDatos();
    }	
    
	@Override
	protected void onResume() {
		mostrarDatos();
		super.onResume();
	}

	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}
	
	
    private class OnClickObservaciones implements OnClickListener{
    	private String observaciones; 
    	private String fecha;
    	final Dialog dialog;
    	
    	public OnClickObservaciones (String p_observaciones, String p_fecha, Context p_context){
    		observaciones = p_observaciones;
    		fecha = p_fecha;
    		dialog = new Dialog(p_context);
    	}
    	
		public void onClick(View v) {
	        dialog.setContentView(R.layout.listado_dialog_layout);
	        dialog.setTitle(R.string.literalObservaciones);
	        dialog.setCancelable(true);
	        	        
	        TextView textObservaciones = (TextView) dialog.findViewById(R.id.textViewObservaciones);
	        TextView textObservacionesFecha = (TextView) dialog.findViewById(R.id.textViewObservacionesFecha);
	        textObservacionesFecha.setText(fecha);
	        textObservaciones.setText(observaciones);
	          
	        Button button = (Button) dialog.findViewById(R.id.ButtonSalir);
	        button.setOnClickListener(new OnClickListener() {
										       public void onClick(View v) {
										    	   dialog.dismiss();
										       }
	          						  });
	          dialog.show();
		}
    }
    
    private class OnClickImagen implements OnClickListener{
    	private File imagen;
    	
    	public OnClickImagen (File p_imagen, Context p_context){
    		imagen = p_imagen;
    	}
    	
		public void onClick(View v) {
			Intent intVerFoto = new Intent(Intent.ACTION_VIEW);
			intVerFoto.setDataAndType(Uri.fromFile(imagen),"image/jpeg");
			startActivity(intVerFoto);
	        
		}
    }
	
 
}