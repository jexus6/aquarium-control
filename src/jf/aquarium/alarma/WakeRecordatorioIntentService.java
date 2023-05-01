package jf.aquarium.alarma;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;



public abstract class WakeRecordatorioIntentService extends IntentService {
	
	public static final String NOMBRE_LOCK = "jf.aquarium.alarma";
	private static PowerManager.WakeLock lock = null;
	
	abstract void doTareaRecordatorio(Intent i);
	
	public WakeRecordatorioIntentService(String name) {
		super(name);
	}
	
	public static void tomarLock (Context context){
		getLock(context).acquire();
	}
	
	synchronized private static PowerManager.WakeLock getLock(Context context){
		if (lock == null){
			PowerManager pwManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
			lock = pwManager.newWakeLock(pwManager.PARTIAL_WAKE_LOCK, NOMBRE_LOCK);
			lock.setReferenceCounted(true);
		}
		return lock;
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		try{
			doTareaRecordatorio(intent);
		}finally{
			getLock(this).release();
			
		}
	}

}
