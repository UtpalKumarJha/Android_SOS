package com.example.drawer;

import java.util.List;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

public class GetLocationNetGPS
{
	Context mContext;

	public GetLocationNetGPS(Context mContext)
	{
	    this.mContext = mContext;
	}

	public double[] GetLoc()
	{
	    LocationManager lm = (LocationManager)  mContext.getSystemService(Context.LOCATION_SERVICE);  
	    List<String> providers = lm.getProviders(true);

	    
	    Location l = null;

	    for (int i=providers.size()-1; i>=0; i--) 
	    {
	            l = lm.getLastKnownLocation(providers.get(i));
	            if (l != null) break;
	    }

	    double[] gps = new double[3];
	    if (l != null) 
	    {
	            gps[0] = l.getLatitude();
	            gps[1] = l.getLongitude();
	            gps[2]=1;
	    }
	    if(l==null)
	    {
	    	gps[2]=0;;
	    }
	    
	    return gps;

	}
}