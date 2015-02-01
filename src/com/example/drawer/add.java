package com.example.drawer;

import android.graphics.Typeface;
import android.app.Activity;
import android.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

public class add extends Fragment implements OnClickListener
{
	int i;
	Button ib;
	DBADapter db3,db4;
	public Context context;
	View rootView,rootsView;	
	TextView im1,im2,im3,im4,im5,ig,id;
	TextView tv1,tv2,tv3,tv4,tv5;
	Animation fadeIn,fadeOut;
	
	int ty=0;
	
	
	public add(){}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) 
	{
		i=0;
 
        rootView = inflater.inflate(R.layout.viewing, container, false);
        
        
        ib = (Button)rootView.findViewById(R.id.imageButton1);
        
        ig=  (TextView)rootView.findViewById(R.id.t2);
        id=  (TextView)rootView.findViewById(R.id.t3);
        im1 = (TextView)rootView.findViewById(R.id.i1);
        im2 = (TextView)rootView.findViewById(R.id.i2);
        im3 = (TextView)rootView.findViewById(R.id.i3);
        im4 = (TextView)rootView.findViewById(R.id.i4);
        im5 = (TextView)rootView.findViewById(R.id.i5);
        
        tv1 = (TextView)rootView.findViewById(R.id.t4);
        tv2 = (TextView)rootView.findViewById(R.id.t5);
        tv3 = (TextView)rootView.findViewById(R.id.t6);
        tv4 = (TextView)rootView.findViewById(R.id.t7);
        tv5 = (TextView)rootView.findViewById(R.id.t8);
        
        
        Typeface face=Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/HelveNeuExt.ttf");
        
        ig.setTypeface(face);
        id.setTypeface(face);
        tv1.setTypeface(face);
        tv2.setTypeface(face);
        tv3.setTypeface(face);
        tv4.setTypeface(face);
        tv5.setTypeface(face);
        im1.setTypeface(face);
        im2.setTypeface(face);
        im3.setTypeface(face);
        im4.setTypeface(face);
        im5.setTypeface(face);
        
          fadeIn = new AlphaAnimation(0.0f , 1.0f ) ;
		  fadeIn.setDuration(2000);
		  fadeIn.setFillAfter(true);
		
        
        
        ib.setOnClickListener(this);
    
        db3 = new DBADapter(getActivity());
        db4 = new DBADapter(getActivity());
        
        
        db3.open();
        db4.open();
        Cursor cr = db3.getAllRows();
        
        if(cr.moveToFirst())
        {
        	ty=0;
        	do
			   { 
				    String res3 = cr.getString(1);
				    String res4 = cr.getString(2);
				    if(ty==0)
				    {
				    	tv1.setText(res3);
				    	im1.setText(res4);
				    }
				    if(ty==1)
				    {
				    	tv2.setText(res3);	
				    	im2.setText(res4);
				    }
				    
				    if(ty==2)
				    {
				    	tv3.setText(res3);	
				    	im3.setText(res4);
				    }
				    
				    if(ty==3)
				    {
				    	tv4.setText(res3);
				    	im4.setText(res4);
				    }
				    
				    if(ty==4)
				    {
				    	tv5.setText(res3);
				    	im5.setText(res4);
				    }
				    
				    
				    ty++;
				    
			   }    while(cr.moveToNext());
        }
        else
        {
        	tv1.setText("");
        	tv2.setText("");
        	tv3.setText("");
        	tv4.setText("");
        	tv5.setText("");
        	im1.setText("");
        	im2.setText("");
        	im3.setText("");
        	im4.setText("");
        	im5.setText("");
        	
        }
     
        
        i=ty;
        String h = String.valueOf(5-i);
        ib.setText(h);
        
       
        
        return rootView;
    }

	private static final int PICK_CONTACT_REQUEST = 1;
	
	@Override
	public void onClick(View arg0) 
	{
	   if(arg0.getId()==R.id.imageButton1)
	   {
		   if(i<5)
		   {
			   String g = String.valueOf(4-i);
			   ib.setText(g);
			   yo();   
		   }
		   else
		   {
			  Toast t = Toast.makeText(getActivity(), "sorry no more addition",Toast.LENGTH_LONG);
			  t.show();
		   }
	   }
	}

	
	
	public void yo() 
	{
		Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
	    pickContactIntent.setType(Phone.CONTENT_TYPE); 
	    startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
	}

	
	String name="";
	String phone="";
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		
	    if (requestCode == PICK_CONTACT_REQUEST)
         {
	    	
	        if (resultCode == Activity.RESULT_OK)
	        {
	        	Uri contactUri = data.getData();
		          
	            String[] projection = {Phone.NUMBER};

	            Cursor cursor = getActivity().getContentResolver()
	                    .query(contactUri, projection, null, null, null);
	            cursor.moveToFirst();

	            int mark=0;
	            int column = cursor.getColumnIndex(Phone.NUMBER);
	            
	            phone = cursor.getString(column);
	            
               
                cursor = getActivity().getContentResolver().query(contactUri, null, null, null, null);
                if (cursor.moveToFirst())
                {
                    name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                }
                
                
                Cursor cr1 = db4.getAllRows();
                
                
                if(cr1.moveToFirst())
                {
                	do
                	{
                		String temp = cr1.getString(1);
                	    
                		        		
                		if(temp.equals(name))
                		{
                			mark=1;
                		}
                		
                	}while(cr1.moveToNext());
                }
                
                
                
                
                if(mark==0)
                {
                	i++;
                		
                	if(i==1)
                    {
                    	tv1.setText(name);
                    	im1.setText(phone);
                    	
                    }
                    if(i==2)
                    {
                    	tv2.setText(name);
                    	im2.setText(phone);
                    	
                    }
                    if(i==3)
                    {
                    	tv3.setText(name);
                    	im3.setText(phone);
                    	
                    }
                    if(i==4)
                    {
                    	tv4.setText(name);
                    	im4.setText(phone);
                    	
                    }
                    if(i==5)
                    {
                    	 tv5.setText(name);
                    	 im5.setText(phone);
                    	 
                    }
                   	
                    db4.insertRow(name,phone);
                    
                    
                }
                else
                {
                	Toast.makeText(getActivity(), "SORRY THE CONTACT ALREADY  EXISTS",Toast.LENGTH_LONG).show();
                }
                
                }    
	        }
	        
	     }    
        	    	    
	
	

	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
		db3.close();
		db4.close();
	}
	
}
