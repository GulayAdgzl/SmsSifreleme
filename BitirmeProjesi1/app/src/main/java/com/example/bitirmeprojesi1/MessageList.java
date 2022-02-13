package com.example.bitirmeprojesi1;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bitimeprojesi22.crypt.Crypt;

import java.util.ArrayList;
import java.util.List;

public class MessageList extends AppCompatActivity {

    SimpleCursorAdapter adapter;
    ListView messageList;
    List<String>  persons;
    List<String> phones;
    Crypt cryptObject=new Crypt();
    ContentResolver cr;
    Cursor c;
    Uri inboxURI;
    String[] columns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        messageList=(ListView)findViewById(R.id.ListView1);
        /*Mesaj listesi boş ise bu duruma dair  bir yazı  görüntülenir*/
        messageList.setEmptyView(findViewById(R.id.empty));


        /*Gelen kutusuna erişim Urisi*/
        inboxURI=Uri.parse("content://sms/");


        /*Alınacak olan bilgiler:id,mesajı gönderen ,mesaj içeriği ve okunup
        okunmadığı bilgisi
         */
        columns=new String[]{"_id","address","body","read"};

        cr=getContentResolver();

        /*Bu metot sayesinde daha önceden mesajlaşılmış kişiler listelenir*/

        messageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                try {
                    Bundle phoneData=new Bundle();
                    phoneData.putString("phoneNumber",phones.get(position));
                    phoneData.putString("personName",persons.get(position)+"");
                    Intent intentObject=new Intent();
                    intentObject.setClass(com.example.bitimeprojesi22.MessageList.this, Conversation.class);

                    intentObject.putExtras(phoneData);
                    startActivity(intentObject);
                }catch (Exception e){

                }
            }
        });

    }

    /*
    Bu metot veritabanından mesajları çekerek,şifreli mesajların ekranda
    görüntülenmesini sağlar
     */


    public void adapterLoad(){
        persons=new ArrayList<String>();
        phones=new ArrayList<String>();
        messageList.setAdapter(null);
        c=cr.query(inboxURI,columns,null,null,null);
        while(c.moveToNext()){
            try {
                String body=c.getString(c.getColumnIndexOrThrow("body"));
                String message=cryptObject.decrypt(body);

                if(message.length()!=0){
                    String senderPhone=c.getString(c.getColumnIndexOrThrow("address"));
                    String senderName=getContactName(senderPhone);
                    if(senderName.trim().length()==0 && !persons.contains(senderPhone)){
                        persons.add(senderPhone);
                        phones.add(senderPhone);
                    }
                    if(senderName.trim().length()!=0 && !persons.contains(senderName)){
                        persons.add(senderName);
                        phones.add(senderPhone);

                    }


                }
            }catch (Exception e){

            }
        }
        messageList.setAdapter(new ArrayAdapter<String>(com.example.bitimeprojesi22.MessageList.this,
                R.layout.list,R.id.name,persons));
        messageList.setFastScrollEnabled(true);
    }
    /*
    Bu metot içerisine gelen telefon numarası bilgisine sahip olan kişinin adını
    döndürür.
     */
    public String getContactName(final String phoneNumber){
        Uri uri;
        String[] projection;
        Uri mBaseUri= Contacts.Phones.CONTENT_FILTER_URL;
        projection=new String[]{Contacts.People.NAME};
        try{
            //Tekrar bak
           // Class<?> c=(Uri)c.getField ("CONTENT_FILTER_URI").get(mBaseUri);
            projection=new String[]{"display_name"};
        }catch (Exception e){

        }
        uri=Uri.withAppendedPath(mBaseUri,Uri.encode(phoneNumber));
        Cursor cursor=this.getContentResolver().query(uri,projection,null,null,null);


        String contactName="";

        if(cursor.moveToFirst()){
            contactName=cursor.getString(0);
        }
        cursor.close();
        cursor=null;

        return contactName;

    }


    @Override
    public  boolean onOptionsItemSelected(MenuItem item){
        /*
        Üst kısımda bulunan yenile,telefon rehberi ve bilgileri güncelle
        Butonlarının işlevlerinin belirtildiği metot
         */
        switch(item.getItemId()){
            case R.id.refresh:
                adapterLoad();
                break;
            case R.id.contact:
                Intent j=new Intent();
                j.setClass(com.example.bitimeprojesi22.MessageList.this, com.example.bitimeprojesi22.Telephone_Guide.class);
                startActivity(j);
                break;
            case R.id.update:
                Intent k=new Intent();
                k.setClass(com.example.bitimeprojesi22.MessageList.this,UpdateUserInfo.class);
                startActivity(k);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected  void onResume(){
        adapterLoad();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        /*
        Üst kısımdaki butonların tasarımını içeren menü
         */
        getMenuInflater().inflate(R.menu.messagelist_menu,menu);
        return true;
    }
}