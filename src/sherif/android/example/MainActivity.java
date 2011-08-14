package sherif.android.example;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import sherif.android.core.R;

public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.startService(new Intent(this,DialogService.class));
        setContentView(R.layout.main);
        ((Button) findViewById(R.id.createB)).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				doBindService();
			}
        	
        });
    }
    boolean mIsBound = false;
    private DialogService mBoundService;
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            mBoundService = ((DialogService.LocalBinder)service).getService();
            mBoundService.createDialogIn(10000);
            doUnbindService();
        }

        public void onServiceDisconnected(ComponentName className) {
            mBoundService = null;
        }
    };
    void doBindService() {
    	if(!mIsBound){
	        bindService(new Intent(MainActivity.this, 
	                DialogService.class), mConnection, Context.BIND_AUTO_CREATE);
	        mIsBound = true;
    	}
    }
    void doUnbindService() {
        if (mIsBound) {
            unbindService(mConnection);
            mIsBound = false;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        doUnbindService();
    }
}