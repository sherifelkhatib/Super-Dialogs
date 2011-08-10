package sherif.android.core;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class DialogService extends Service {

	mainTask poller = new mainTask();
	Context mContext = this;
	private final IBinder mBinder = new LocalBinder();

	@Override
	public IBinder onBind(Intent arg0) {
		Log.v("DialogService", "onBind()");
		return mBinder;
	}

	@Override
	public boolean onUnbind(Intent arg0) {
		Log.v("DialogService", "onUnbind()");
		return true;
	}

	public class LocalBinder extends Binder {
		DialogService getService() {
			return DialogService.this;
		}
	}

	public static DialogService instance = null;

	public static boolean isInstanceCreated() {
		return instance != null;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Log.v("DialogService", "onStart()");
		startService();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		// showNotification();
		Log.v("DialogService", "onCreate()");
	}

	@Override
	public void onDestroy() {
		instance = null;
		Log.v("DialogService", "onDestroy()");
	}
	//remember on dismiss finish()

	private class mainTask extends Thread {
		public void run() {
			try {
				synchronized(this){
					wait(5000);//WAIT 1 MINUTE THEN SEND NOTIFICATION
					//THIS IS ALL JUST FOR TESTING
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.v("DialogService","mainTask: SENDING NOTIFICATION");
			//This function should be called from the onBind
			SuperDialog.createDialog(SuperDialog.DIALOG_ERROR, mContext);
		}
	}

	public void startService() {
		poller.start();
	}
}
