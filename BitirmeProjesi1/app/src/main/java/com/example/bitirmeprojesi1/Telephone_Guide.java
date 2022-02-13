package com.example.bitirmeprojesi1;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Telephone_Guide extends AppCompatActivity {
    SearchView searchView;
    List<String> persons;
    List<String> phones;
    ListView contactsList;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater=getMenuInflater ();
        inflater.inflate (R.menu.guide,menu);
        MenuItem menuItem=menu.findItem (R.id.search);
        searchView=(SearchView)menuItem.getActionView ();
        searchView.setQueryHint ("Bir şeyler..");
        int searchPlateld=searchView.getContext ().getResources ()
                .getIdentifier ("android:id/search_plate",null,null);
        View searchPlate=searchView.findViewById (searchPlateld);


        if(searchPlate!=null){
            searchPlate.setBackgroundColor (Color.DKGRAY);
            int searchTextId=searchPlate.getContext ().getResources ()
                    .getIdentifier ("android:id/search_src_text",null,null);
            TextView searchText=(TextView)searchPlate.findViewById (searchTextId);
            if(searchText!=null){
                searchText.setTextColor (Color.WHITE);
                searchText.setHintTextColor (Color.WHITE);
            }
        }
        searchView.setOnQueryTextListener (new SearchView.OnQueryTextListener () {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty (newText))
                    loadContacts("");
                else
                    loadContacts(newText.toString ());
                return false;
            }

        });
        return true;
                
        




    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telephone_guide);
        contactsList =(ListView)findViewById (R.id.listView1);
        loadContacts ("");

        contactsList.setOnItemClickListener (new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Bundle phoneData=new Bundle ();
                    phoneData.putString ("phoneNumber",phones.get (position));
                    phoneData.putString ("personName",persons.get (position));
                    Intent intentObject=new Intent ();
                    intentObject.setClass (com.example.bitimeprojesi22.Telephone_Guide.this,Conversation.class);
                    intentObject.putExtras(phoneData);
                    startActivity (intentObject);
                }catch (Exception e){
                    Toast.makeText (getApplicationContext (),e.toString (),Toast.LENGTH_LONG).show ();
                }
            }
        });
    }

    public  void loadContacts(String match){
        persons=new ArrayList<String> ();
        phones=new ArrayList<String> ();
        String selection= ContactsContract.Contacts.IN_VISIBLE_GROUP+"="
                +("1")+"";
        String sortOrder=ContactsContract.Contacts.DISPLAY_NAME+"COLLATE LOCALIZED ASC";
        Cursor contacts=getContentResolver ().query (
                ContactsContract.CommonDataKinds.Phone.CONTENT_FILTER_URI,null,
                selection,null,sortOrder
        );
        while(contacts.moveToNext ()){
            String name=contacts.getString (contacts.getColumnIndex (ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String number=contacts.getString (contacts.getColumnIndex (ContactsContract.CommonDataKinds.Phone.NUMBER));
            if (name!=null ||number!=null){
                if (match.trim ().length ()==0 && !persons.contains (name)){
                    phones.add (number);
                    persons.add (name);
                }
                else if(match.trim ().length ()!=0 && !persons.contains (name)){
                    if(name.toLowerCase ().contains (match.toLowerCase ())){
                        phones.add (number);
                        persons.add (name);
                    }
                }
                contacts.close ();
                contactsList.setAdapter (new ArrayAdapter<String>(com.example.bitimeprojesi22.Telephone_Guide.this,
                        R.layout.contactlist,R.id.name,persons));
                contactsList.setFastScrollEnabled (true);
            }
        }

    }
}
