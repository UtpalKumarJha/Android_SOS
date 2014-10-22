package com.example.drawer;



import android.graphics.Typeface;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.telephony.SmsManager;

import android.widget.Toast;

public class receiveyo extends BroadcastReceiver
{

	GetLocationNetGPS qss;
	
	@Override
	public void onReceive(Context arg0, Intent arg1) 
	{
        if(arg1.getAction().equals("hellyeahh"))
        { 	
        	
        	somehow(arg0);
        }
                
	}

	
	

	private void somehow(Context arg0) 
	{
		
		DBADapter db6;
		db6 = new DBADapter(arg0);
		db6.open();
		qss = new GetLocationNetGPS(arg0);
		SmsManager sms = SmsManager.getDefault();
		
		String s="Couldn't find last recorded location call to know";
		
		 double[] gps = new double[3];
		    gps = qss.GetLoc();
		    
		    String v="g";
		    
		    if(gps[2]==0)
		    {
		    	v=s;
		    }
			if(gps[2]==1)
			{
				v="This is my last known gps location\n" +"Lattitude : "+ String.valueOf(gps[0])+"\n"+"Longitude : "+String.valueOf(gps[1]);
			}
		
		Cursor cr = db6.getAllRows();
		 if(cr.moveToFirst())
	        {
	        	do
				   { 
					    String res3 = cr.getString(2);
				
					    sms.sendTextMessage(res3, null, v, null, null);  
					    
				   }    while(cr.moveToNext());
	        }
	        else
	        {
	        	Toast.makeText(arg0, "THERE ARE NO CONTACTS TO SEND TO",Toast.LENGTH_LONG).show();
	        }
		
		 Toast.makeText(arg0,"MESSAGE SENT WITH LAST RECORDED GPS COORDINATES",Toast.LENGTH_LONG).show();
		 
		 db6.close();
		 
	}

	

}
