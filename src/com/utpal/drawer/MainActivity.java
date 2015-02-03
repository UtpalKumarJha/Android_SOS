package com.utpal.drawer;

import java.util.Locale;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.utpal.drawer.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends ActionBarActivity
{
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;

    DBADapter db2;
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        if (mDrawerToggle.onOptionsItemSelected(item)) 
        {
          return true;
        }   
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db2 = new DBADapter(this);
        db2.open();
        mTitle = mDrawerTitle = getTitle();
        mPlanetTitles = getResources().getStringArray(R.array.WOMEN);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  
                mDrawerLayout,         
                R.drawable.ic_drawer,  
                R.string.drawer_open,  
                R.string.drawer_close  
                )
        {
            public void onDrawerClosed(View view) 
            {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); 
            }

            public void onDrawerOpened(View drawerView)
            {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); 
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null)
        {
            selectItem(0);
        }
    }

    @Override
	protected void onDestroy()
    {	
		super.onDestroy();
		db2.close();
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) 
    {
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            selectItem(position);
        }
    }

    private void selectItem(int position)
    {
    	
    	Fragment fragment = null;
    	
    	if(position==0)
    	{	
    		fragment = new sos();		    		
    	}
    	if(position==1)
    	{
    		Cursor cr = db2.getAllRows();
    		if(cr.moveToFirst())
    		{
    			fragment = new Views();	
    		}
    		else
    		{
    			Toast.makeText(getApplicationContext(), "THERE IS NOTHING TO SHOW AS OF RIGHT NOW MAKE SURE TO ADD SOME CONTACTS BEFORE PROCEEDING",Toast.LENGTH_SHORT).show();
    		}
    		
    	}
    	if(position==2)
    	{
    		fragment = new add();
    	}
        if(position==3)
        {
        	db2.deleteAll();
        	fragment = new clearing();
        }
        if(position==4)
        {
        	Cursor cr3 = db2.getAllRows();
    		if(cr3.moveToFirst())
    		{
    			
    			fragment = new modifying();	
    		}
    		else
    		{
    			Toast.makeText(getApplicationContext(), "THERE IS NOTHING TO MODIFY AS OF RIGHT NOW MAKE SURE TO ADD SOME CONTACTS BEFORE PROCEEDING",Toast.LENGTH_SHORT).show();
    		}
        	
        }
        if(position==5)
        {
        	Cursor cr = db2.getAllRows();
    		if(cr.moveToFirst())
    		{
    			
    			fragment = new listss();	
    		}
    		else
    		{
    			Toast.makeText(getApplicationContext(), "THERE IS NOTHING TO CALL AS OF RIGHT NOW MAKE SURE TO ADD SOME CONTACTS BEFORE PROCEEDING",Toast.LENGTH_SHORT).show();
    		}
        	
        }
        
        
    	if(fragment!=null)
    	{
    		FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            mDrawerList.setItemChecked(position, true);
            setTitle(mPlanetTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);	
    	}
        
        
        
        
    }

    @Override
    public void setTitle(CharSequence title)
    {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public static class PlanetFragment extends Fragment
    {
        public static final String ARG_PLANET_NUMBER = "planet_number";

        public PlanetFragment()
        {
        	
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_planet, container, false);
            int i = getArguments().getInt(ARG_PLANET_NUMBER);
            String planet = getResources().getStringArray(R.array.WOMEN)[i];

            int imageId = getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()),
                            "drawable", getActivity().getPackageName());
            ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
            getActivity().setTitle(planet);
            return rootView;
        }
    }
    
    
    public static class AdFragment extends Fragment
    {
    	private AdView mAdView;
    	public AdFragment()
    	{
    		
    	}
    	@Override
    	public void onActivityCreated(Bundle bundle)
        {
    	 super.onActivityCreated(bundle);
    	// Gets the ad view defined in layout/ad_fragment.xml with ad unit ID set in
    	// values/strings.xml.
    	mAdView = (AdView) getView().findViewById(R.id.adView);
    	// Create an ad request. Check logcat output for the hashed device ID to
    	// get test ads on a physical device. e.g.
    	// "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
    	AdRequest adRequest = new AdRequest.Builder()
    	.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
    	.build();
    	// Start loading the ad in the background.
    	mAdView.loadAd(adRequest);
    	}
    	@Override
    	public View onCreateView(LayoutInflater inflater, ViewGroup container,
    	Bundle savedInstanceState)
    	{
    	  return inflater.inflate(R.layout.fragment_ad, container, false);
    	}
    	/** Called when leaving the activity */
    	@Override
    	public void onPause()
    	{
    	 if (mAdView != null)
    	 {
    	  mAdView.pause();
    	 }
    	super.onPause();
    	}
    	/** Called when returning to the activity */
    	@Override
    	public void onResume() {
    	super.onResume();
    	if (mAdView != null) {
    	mAdView.resume();
    	}
    	}
    	/** Called before the activity is destroyed */
    	@Override
    	public void onDestroy() {
    	if (mAdView != null) {
    	mAdView.destroy();
    	}
    	super.onDestroy();
    	}
    }
}