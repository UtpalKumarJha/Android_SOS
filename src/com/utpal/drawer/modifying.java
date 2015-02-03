package com.utpal.drawer;



import android.graphics.Typeface;
import java.util.ArrayList;
import java.util.List;

import com.utpal.drawer.R;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class modifying extends Fragment 
{
	
	View rootview;

	private static final int PICK_CONTACT_REQUEST = 1;
	List<String> names;
	List<String> numbers;
	DBADapter db11;
	ListView lv;
	TextView tds;
	Typeface face;
	
	long ids;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		rootview = inflater.inflate(R.layout.modify,container,false);
		
		lv = (ListView)rootview.findViewById(R.id.li);
		tds = (TextView)rootview.findViewById(R.id.texts);
		db11 = new DBADapter(getActivity());
		
		db11.open();
		
		names = new  ArrayList<String>();
		numbers= new ArrayList<String>();
		face=Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/HelveNeuExt.ttf");
		jeez();
		
		return rootview;
	}
	
      
	private void jeez() 
	{
		
		names.clear();
		numbers.clear();
		
		Cursor cr99 = db11.getAllRows();
		
		if(cr99.moveToFirst())
		{
			do
			{
				String res2 = cr99.getString(1);
				String res3 = cr99.getString(2);
				
				names.add(res2);
				numbers.add(res3);
				
			}while(cr99.moveToNext());
			
			replace();
			
		}
		
		
	}


	private void replace()
	{

		 ArrayAdapter<String> adapter=new ArrayAdapter<String>(
		 getActivity().getApplicationContext(),android.R.layout.simple_list_item_1,names)
		 {
	 @Override
       public View getView(int position, View convertView,
               ViewGroup parent) {
           View view =super.getView(position, convertView, parent);

           TextView textView=(TextView) view.findViewById(android.R.id.text1);
           
           textView.setTypeface(face);
           textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
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
    	ids=position;
       go();
    }


}); 
	}
	
	
	
	private void go()
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

	          
	            int column = cursor.getColumnIndex(Phone.NUMBER);
	            
	            phone = cursor.getString(column);
	            
               
                cursor = getActivity().getContentResolver().query(contactUri, null, null, null, null);
                if (cursor.moveToFirst())
                {
                    name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                }
                
                Cursor cr1 = db11.getAllRows();
                
                int mark=0;
                
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
                	Cursor cr8 = db11.getAllRows();
                    
                    String s = " ";
                    
                    if(cr8.moveToFirst())
                    {
                    	  s = cr8.getString(0);                	
                    }
                    
                    long tr = Long.valueOf(s);
                    
                    db11.updateRow(tr+ids,name, phone);
                    jeez();    	
                }
                else
                {
                	Toast.makeText(getActivity(),"THE CONTACT ALREADY EXISTS",Toast.LENGTH_SHORT).show();
                }
                
                    
	        }
         }
	}    
	        	
	
	@Override
	public void onDestroyView() 
	{
		db11.close();
		super.onDestroyView();
	}

}
