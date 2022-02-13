package com.example.bitirmeprojesi1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database  extends SQLiteOpenHelper {

    /*
    Bu sınıfta bir kullanıcı veritabanı oluşturulur
     */
    private static  final  String DATABASE ="user.db";
    private static  final int VERSION=1;

    public Database( Context con ) {
        super (con,DATABASE,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL ("CREATE TABLE person (id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,username TEXT,password TEXT);");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL ("DROP TABLE If EXISTS person");
        onCreate (db);
    }





    /*
    Bu metot veritabanına kayıt işlemi geröekleştirilmesini sağlar
     */
    public void addPerson(String name,String username,String password){
        SQLiteDatabase db=getWritableDatabase ();
        ContentValues datas=new ContentValues ();
        datas.put ("name",name);
        datas.put ("username",username);
        datas.put ("password",password);
        db.insertOrThrow ("person",null,datas);
    }
    /*
    Bu metot veritabanında kayıtlı kullanıcı sayısını döndürür
     */
    public int tableCount(){
        SQLiteDatabase db=getReadableDatabase ();
        Cursor cursor=db.rawQuery ("SELECT count(*) FROM person",null);
        cursor.moveToNext ();
        int result=cursor.getInt (0);
        cursor.close ();
        return result;
    }

    /*
    Bu metot kullanıcının ad ve şifre bilgilerini doğru girip girmediğini kontrol  eder
     */
    public int IsPersonExist(String username,String password){
        SQLiteDatabase db=getReadableDatabase ();
        Cursor cursor=db.rawQuery (
                "SELECT count(*) FROM person where username="+username
                +"and password="+password+"",null);
        cursor.moveToNext ();
        int count=cursor.getInt (0);
        cursor.close ();
        return count;

    }

    /*
    Update metodu
     */

    public void update(String name,String username,String password){
        SQLiteDatabase db=getWritableDatabase ();
        ContentValues cv=new ContentValues ();
        cv.put ("name",name);
        cv.put ("username",username);
        cv.put ("password",password);
        db.update ("person",cv,"name"+"=?",new String[]{name});
        db.close ();
    }


    /*
    records() metodu
     */
    public String[]  records(){
        String name="",username="",password="";
        SQLiteDatabase db=getReadableDatabase ();
        Cursor cursor=db.rawQuery ("SELECT*FROM person",null);
        while (cursor.moveToNext ()){
            name=cursor.getString ((cursor.getColumnIndex ("name")));
            username=cursor.getString ((cursor.getColumnIndex ("username")));
            password=cursor.getString ((cursor.getColumnIndex ("password")));
        }
        return new String[] {name,username,password};
    }











}
