package jf.aquarium.alarma;


import jf.aquarium.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

public class RecordatorioService extends WakeRecordatorioIntentService {

	public RecordatorioService() {
		super("RecordatorioService");
	}

	@Override
	void doTareaRecordatorio(Intent p_intent) {
	    String mensaje= p_intent.getExtras().getString("mensaje");
		NotificationManager ntManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Intent i = new Intent(this,jf.aquarium.AquariumControl.class);
		PendingIntent pi = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_ONE_SHOT);
		Notification nota = new Notification(R.drawable.ic_launcher,getResources().getString(R.string.literalAbonado)+": "+ mensaje.substring(2), System.currentTimeMillis()); 
		nota.setLatestEventInfo(this,getResources().getString(R.string.literalNombreAplicacion),getResources().getString(R.string.literalAbonado)+": "+ mensaje.substring(2), pi);
		nota.flags |= Notification.FLAG_AUTO_CANCEL;
		ntManager.notify(Integer.parseInt(mensaje.substring(0, 1)), nota);
	}


}
