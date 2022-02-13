package com.example.bitirmeprojesi1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
//Tekrar Bak

public class SMSReceiver extends BroadcastReceiver {
    final SmsManager sms=SmsManager.getDefault ();

    public  void onReceive(Context context, Intent intent){
        Bundle bundle=intent.getExtras ();
        SmsManager[] smsm=null;
        String sms_str="";
        /*

        if(bundle!=null){

            Object[] pdus=(Object[] )bundle.get ("pdus");
            smsm=new SmsManager[pdus.length];
            for (int i=0;i<smsm.length;i++){
                smsm[i]=new SmsMessage.createFromPdu (byte[] pdus);

                sms_str+=smsm[i].getMessageBody().toString();
            }
            try {
                Conversation.updateAdapter(sms_str,"1",smsm[0].getOriginatingAddress());

            }catch (Exception e){


            }
        }

         */
    }
}
