package com.example.drawer;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class clearing extends Fragment
{	
	TextView tv;
	public clearing(){}
		@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) 
		{			
	        View rootView = inflater.inflate(R.layout.cleat, container, false);
	        tv = (TextView)rootView.findViewById (R.id.textView1);
	        tv.setText("ALL CONTACTS CLEARED GO TO ADD TO ADD THEM BACK AGAIN");   
	        return rootView;
	    }
  
}
