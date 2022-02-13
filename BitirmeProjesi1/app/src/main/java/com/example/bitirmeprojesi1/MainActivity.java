package com.example.bitirmeprojesi1;

//import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView Link_SignUp;
    Button Btn_SignIn;
    EditText etUserName,etPass;
    private Database userDatabase;
    Crypt cryptObject=new Crypt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        userDatabase=new Database(this);
        etUserName=(EditText)findViewById (R.id.etUserName);
        etPass=(EditText)findViewById (R.id.etPass);
        Link_SignUp=(TextView)findViewById (R.id.signUp);

        /*SignUp linkine tıklandığında*/
        Link_SignUp.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent i=new Intent (MainActivity.this,SignUp.class);
                startActivity (i);
            }
        });

        Btn_SignIn=(Button)findViewById (R.id.btnsignin);

        /*Btn_SignIn butonuna tıklandığında*/
        Btn_SignIn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                /*Kullanıcı girişi işleminde IsPersonExist() metodu ile
                kullanıcı bilgilerinin doğruluğu kontrol  edilir
                 */
                try{
                    if(etPass.getText ().toString ().trim ().length ()==0 ||
                            etUserName.getText ().toString ().trim ().length ()==0)
                        Toast.makeText (getBaseContext (),R.string.empty,Toast.LENGTH_LONG).show ();
                    else{
                        String passwordHash=cryptObject.md5Coder(etPass.getText ().
                                toString ().trim());
                        int person=userDatabase.IsPersonExist(etUserName.getText ()
                                .toString ().trim (),passwordHash);

                        if(person==1){
                            Intent i=new Intent (MainActivity.this,MessageList.class);
                            startActivity (i);
                        }else{
                            Toast.makeText (getApplicationContext (),R.string.
                                    usernameOrPasswordInCorrect,Toast.LENGTH_SHORT).show ();
                        }
                    }
                }catch (Exception e){

                }
            }
        });

    }
}