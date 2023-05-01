package jf.aquarium.alarma;

import jf.aquarium.ConfiguracionActivity;
import jf.aquarium.bd.Acuario;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager.WakeLock;
import android.util.Log;



public class ReceptorAlarma extends  BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent p_intent) {
		Log.d("ReceptorAlarma", "Alarma recibida");
		
		//Paso toda la informacion (dia_PNC) del intent que ha crado la alarma al que va a realizarla
		String mensaje = p_intent.getData().toString()+"_";
		if(p_intent.getExtras().getBoolean("jf.aquarium.alarma.p", false)) mensaje = mensaje+" (P)";
		if(p_intent.getExtras().getBoolean("jf.aquarium.alarma.n", false)) mensaje = mensaje+" (N)";
		if(p_intent.getExtras().getBoolean("jf.aquarium.alarma.c", false)) mensaje = mensaje+" (C)";

		WakeRecordatorioIntentService.tomarLock(context);
		Intent i = new Intent(context, RecordatorioService.class);
		i.putExtra("mensaje", mensaje);
		
		context.startService(i);
	}
	
	

}
