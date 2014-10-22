package com.example.drawer;



import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class sos extends Fragment implements OnClickListener,LocationListener
{
	
	View rootView;

	DBADapter db4;
	String s="Press to send SOS";
	Location location;
	TextView tva,tvb,tvc,tvd,tve,tvf,tvg;
	int lat,lng,mark;
	Bitmap bitmap,circleBitmap;
	EditText ghs;
	Button im;
	Animation fadeIn,fadeOut;
	GetLocationNetGPS qs;
	@Override

	
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) 
	{
		rootView = inflater.inflate(R.layout.send, container, false);
		
		db4 = new DBADapter(getActivity());
		qs = new GetLocationNetGPS(getActivity());
		        
		ghs =(EditText)rootView.findViewById(R.id.editText1);
		im = (Button)rootView.findViewById(R.id.imageView1);
		tva = (TextView)rootView.findViewById(R.id.id2);
		tvb = (TextView)rootView.findViewById(R.id.id3);
		tvc = (TextView)rootView.findViewById(R.id.id4);
		tvd = (TextView)rootView.findViewById(R.id.id5);
		tve = (TextView)rootView.findViewById(R.id.id6);
		
		
	
		Typeface face=Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/HelveNeuExt.ttf");
		
		tva.setTypeface(face);
		tvb.setTypeface(face);
		tvc.setTypeface(face);
		tvd.setTypeface(face);
		tve.setTypeface(face);
		im.setTypeface(face);
		
		
		  ghs.setText(s);
		
		
		  fadeIn = new AlphaAnimation(0.0f , 1.0f ) ;
		  fadeIn.setDuration(2000);
		  fadeIn.setFillAfter(true);
		   
		 
		
		
		im.setOnClickListener(this);
		db4.open();    
		        
		return rootView;
		
	}

	@Override
	public void onDestroyView()
	{
		db4.close();
		super.onDestroyView();
	}

	@Override
	public void onClick(View arg0) 
	{
	   if(arg0.getId()==R.id.imageView1)
	   {
		   s = ghs.getText().toString(); 
		   sendit();
	   }
	}

	private void sendit()
	{
	    SmsManager sms = SmsManager.getDefault();

	       
	   
	    
	    String v=" ";
	    
	    double[] gps = new double[3];
	    gps = qs.GetLoc();
	    
	    
	    
	    if(gps[2]==0)
	    {
	    	v=s;
	    }
		if(gps[2]==1)
		{
			v="This is my last known gps location\n" +"Lattitude : "+ String.valueOf(gps[0])+"\n"+"Longitude : "+String.valueOf(gps[1]);
		}
		
		
		Cursor cr = db4.getAllRows();
		
		int jh=0;
		
		 if(cr.moveToFirst())
	        {
	        	
	        	do
				   { 
					    String res3 = cr.getString(2);
					    String res4 = cr.getString(1);
					    if(jh==0)
					    {
					    	tva.setText(res4);
					    	tva.startAnimation(fadeIn);
					    }
					    if(jh==1)
					    {
					    	tvb.setText(res4);
					    	tvb.startAnimation(fadeIn);
					    }
					    if(jh==2)
					    {
					    	tvc.setText(res4);
					    	tvc.startAnimation(fadeIn);
					    }
					    if(jh==3)
					    {
					    	tvd.setText(res4);
					    	tvd.startAnimation(fadeIn);
					    }
					    if(jh==4)
					    {
					    	tve.setText(res4);
					    	tve.startAnimation(fadeIn);
					    }
					    
					    jh++;
					    sms.sendTextMessage(res3, null, v, null, null);  	
					     
				   }    while(cr.moveToNext());
	        }
	        else
	        {
	        	Toast.makeText(getActivity(), "THERE ARE NO CONTACTS TO SEND TO",Toast.LENGTH_LONG).show();
	        }
	        
	}

	@Override
	public void onLocationChanged(Location arg0)
	{
		 lat = (int) (location.getLatitude());
	     lng = (int) (location.getLongitude());
	}

	@Override
	public void onProviderDisabled(String arg0)
	{
		
	}

	@Override
	public void onProviderEnabled(String arg0) 
	{
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2)
	{
		
		
	}
	
}
