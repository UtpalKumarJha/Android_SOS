package com.example.drawer;



import android.graphics.Typeface;
import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class listss extends Fragment
{

	DBADapter db10;
	List<String> number;
	List<String> name;
	ListView lv;
	TextView td;
	Typeface face;
	
	View rootview;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		
		rootview = inflater.inflate(R.layout.lview, container, false);
		
		db10 = new DBADapter(getActivity());
		
		lv = (ListView)rootview.findViewById(R.id.lists);
		td = (TextView)rootview.findViewById(R.id.listing);
		
		face=Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/HelveNeuExt.ttf");
		db10.open();
		
		
	
		Cursor cr23=db10.getAllRows();
		 number = new ArrayList<String>();
		 name = new ArrayList<String>();
		
		number.clear();
		name.clear();
		
		if(cr23.moveToFirst())
		{
			do
			{
				String res4 = cr23.getString(1);
				String res5 = cr23.getString(2);
				
				number.add(res5);
				name.add(res4);
				
			}while(cr23.moveToNext());
			
			rts();
			
		}
		
		
		return rootview;
		
	}


	private void rts() 
	{
				 ArrayAdapter<String> adapter=new ArrayAdapter<String>(
				 getActivity().getApplicationContext(),android.R.layout.simple_list_item_1,name)
				 {
			 @Override
		        public View getView(int position, View convertView,
		                ViewGroup parent) {
		            View view =super.getView(position, convertView, parent);

		            TextView textView=(TextView) view.findViewById(android.R.id.text1);

		            
		           textView.setTypeface(face);
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
		            return view;
				 }
				 }
				 
				 ;
		
		
		  lv.setAdapter(adapter);
		
		
		 lv.setOnItemClickListener(new OnItemClickListener() 
		 {
			 
             @Override
             public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) 
             {
               
              
              int itemPosition     = position;
              
              
              String numbers = "tel:" + number.get(itemPosition);
              Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(numbers)); 
              startActivity(callIntent);
              
             }

        }); 
		
	}


	@Override
	public void onDestroyView()
	{
		db10.close();
		super.onDestroyView();
	}
	
	
	

	
  
}
