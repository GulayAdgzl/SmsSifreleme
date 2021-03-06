package com.example.bitirmeprojesi1.phonebook;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bitimeprojesi22.R;

import java.util.ArrayList;
import java.util.List;

public class DiscussArrayAdapter extends ArrayAdapter<OneComment> {
    private TextView messages;
    private List<OneComment>oneCommentList=new ArrayList<OneComment> ();
    private LinearLayout wrapper;


    @Override
    public  void unregisterDataSetObserver(DataSetObserver observer){
        if (observer !=null){
            super.unregisterDataSetObserver (observer);
        }
    }

    @Override
    public void add(OneComment object){
        oneCommentList.add (object);
        super.add (object);
    }

    public DiscussArrayAdapter(Context context,int textViewResourceld){
        super(context,textViewResourceld);
    }
    public int getCount(){
        return this.oneCommentList.size ();
    }
    public  OneComment getItem(int index){
        return this.oneCommentList.get (index);

    }


    public View getView(int position, View convertView, ViewGroup parent){
        View row=convertView;
        if(row==null){
            LayoutInflater inflater=(LayoutInflater)this.getContext ()
                    .getSystemService (Context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate (R.layout.listitem_discuss,parent,false);
        }
        wrapper=(LinearLayout) row.findViewById (R.id.wrapper);
        OneComment comment=getItem (position);
        messages=(TextView)row.findViewById (R.id.comment);
        messages.setText (comment.comment);
        messages.setBackgroundResource (comment.left ? R.drawable.bubbles_yellow:R.layout.bubble_green);
        wrapper.setGravity (comment.left ? Gravity.LEFT:Gravity.RIGHT);

        return row;

    }
    public Bitmap decodeToBitmap(byte[] decodedByte){
        return BitmapFactory.decodeByteArray (decodedByte,0,decodedByte.length);
    }

}
