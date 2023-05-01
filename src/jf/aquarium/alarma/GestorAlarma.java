package jf.aquarium.alarma;

import java.util.Calendar;

import jf.aquarium.ConfiguracionActivity;
import jf.aquarium.R;
import jf.aquarium.bd.Acuario;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class GestorAlarma {
	private Context contexto;
	private AlarmManager gestorAlarma;
	
	public GestorAlarma (Context context){
		contexto = context;
		gestorAlarma = (AlarmManager) contexto.getSystemService(Context.ALARM_SERVICE);
		
	}
	
	public void setAlarma(boolean p, boolean n, boolean c, int diaSemana){
		 Intent i = new Intent(null, Uri.parse(""+diaSemana),contexto, ReceptorAlarma.class);
		
		//Sí hay algún abono seleccionado para ese día se actualiza/crea alarma
		if (p || n || c){
			Calendar fechaInicio  = Calendar.getInstance();
			int diasHasta = (diaSemana) - fechaInicio.get(Calendar.DAY_OF_WEEK);
			diasHasta = diasHasta <= 0 ?  7+diasHasta: diasHasta;  
			fechaInicio.add(Calendar.DATE, diasHasta);
			fechaInicio.set(Calendar.HOUR_OF_DAY, 12);
			fechaInicio.set(Calendar.MINUTE,0);
			fechaInicio.set(Calendar.SECOND,0);
			
			i.putExtra("jf.aquarium.alarma.p", p);
			i.putExtra("jf.aquarium.alarma.n", n);
			i.putExtra("jf.aquarium.alarma.c", c);
		    PendingIntent pi = PendingIntent.getBroadcast(contexto, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
			
		    gestorAlarma.setRepeating(AlarmManager.RTC_WAKEUP, fechaInicio.getTimeInMillis(), AlarmManager.INTERVAL_DAY*7, pi);
			Log.d("GestorAlarma", "Alarma iniciada a:"+ fechaInicio.getTime());
		    Toast.makeText(contexto, R.string.avisoAlarma, Toast.LENGTH_LONG).show();
		}
	   //Si no se borra alarma para ese día
	   else{
			PendingIntent pi = PendingIntent.getBroadcast(contexto, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
			gestorAlarma.cancel(pi);
			pi.cancel();
		}
		
	}
	
	

}
