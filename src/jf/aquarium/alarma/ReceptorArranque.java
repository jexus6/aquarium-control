package jf.aquarium.alarma;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Programa las alarmas de todos los valores de las rutinas de abonado 
 *
 */
public class ReceptorArranque extends BroadcastReceiver {

	/* (non-Javadoc)
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
	
		Log.d("ReceptorArranque", "Recibido arranque");
		SharedPreferences pref;
		pref = PreferenceManager.getDefaultSharedPreferences(context);
		GestorAlarma gestorAlarma = new GestorAlarma(context); 
		try {
			//Para cada dia de la semana
			for (int i =0; i<7; i++){
				gestorAlarma.setAlarma(pref.getBoolean("P_"+(i+1),false ),pref.getBoolean("N_"+(i+1),false),pref.getBoolean("C_"+(i+1),false), i);
			}
		} catch (Exception e) {
			Log.e("ReceptorArranque", e.getMessage(), e);
		}
	}
}
