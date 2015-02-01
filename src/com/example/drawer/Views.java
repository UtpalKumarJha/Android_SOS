package com.example.drawer;



import android.app.Fragment;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Views extends Fragment
{
	View rootView;
	DBADapter db5;
	
	TextView tt1,tt2,tt3,tt4,tt5,tt6,tt7,tt8,tt9,tt10,tt11,tt12;
	
	public Views(){}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) 
	{
		rootView = inflater.inflate(R.layout.viwe, container, false);
		
		
		tt11=(TextView)rootView.findViewById(R.id.te2);
		tt12=(TextView)rootView.findViewById(R.id.te3);
		tt1 = (TextView)rootView.findViewById(R.id.te4);
		tt2 = (TextView)rootView.findViewById(R.id.te6);
		tt3 = (TextView)rootView.findViewById(R.id.te8);
		tt4 = (TextView)rootView.findViewById(R.id.te10);
		tt5 = (TextView)rootView.findViewById(R.id.te12);
		
		tt6 = (TextView)rootView.findViewById(R.id.te5);
		tt7 = (TextView)rootView.findViewById(R.id.te7);
		tt8 = (TextView)rootView.findViewById(R.id.te9);
		tt9 = (TextView)rootView.findViewById(R.id.te11);
		tt10 = (TextView)rootView.findViewById(R.id.te13);
		
		Typeface face=Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/HelveNeuExt.ttf");
		
		tt1.setTypeface(face);
		tt2.setTypeface(face);
		tt3.setTypeface(face);
		tt4.setTypeface(face);
		tt5.setTypeface(face);
		tt6.setTypeface(face);
		tt7.setTypeface(face);
		tt8.setTypeface(face);
		tt9.setTypeface(face);
		tt10.setTypeface(face);
		tt11.setTypeface(face);
		tt12.setTypeface(face);
		
		
		db5 = new DBADapter(getActivity());
		db5.open();
		
		  
		Cursor cr = db5.getAllRows();
		

        if(cr.moveToFirst())
        {
        	int ty=0;
        	do
			   { 
				    String res3 = cr.getString(2);
				    String res4 = cr.getString(1);
				    
				    if(ty==0)
				    {
				    	tt1.setText("    "+res3);
				    	tt6.setText(res4);
				    	
				    }
				    if(ty==1)
				    {
				    	tt2.setText("    "+res3);
				    	tt7.setText(res4);
				    	
				    }
				    if(ty==2)
				    {
				    	tt3.setText("    "+res3);
				    	tt8.setText(res4);
				    	
				    }
				    if(ty==3)
				    {
				    	tt4.setText("    "+res3);
				    	tt9.setText(res4);
				    	
				    }
				    if(ty==4)
				    {
				    	tt5.setText("    "+res3);
				    	tt10.setText(res4);
				    	
				    }
				    
				    
				    
				    ty++;
				    
			   }    while(cr.moveToNext());
        }
		
		return rootView;
	}

	@Override
	public void onDestroyView() 
	{
		db5.close();
		super.onDestroyView();
	}

}
