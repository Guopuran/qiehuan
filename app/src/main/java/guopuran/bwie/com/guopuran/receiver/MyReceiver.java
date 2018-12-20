package guopuran.bwie.com.guopuran.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import guopuran.bwie.com.guopuran.LoginActivity;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("cn.jpush.android.intent.NOTIFICATION_OPENED")){
            Intent intent1=new Intent(context,LoginActivity.class);
            intent1.putExtra("pid","65");
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent1);
        }
    }
}
