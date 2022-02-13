package com.example.bitirmeprojesi1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bitimeprojesi22.crypt.Crypt;

public class UpdateUserInfo extends AppCompatActivity {

    Database userDatabase;
    Crypt cryptObject=new Crypt();
    EditText etPass,etUserName,etName,etPassConfrim,etOldPass;
    Button update;
    String[] records;

    @Override
    public void onBackPressed(){
        super.onBackPressed ();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_info);
        etPass=(EditText)findViewById (R.id.etPass);
        etOldPass=(EditText)findViewById (R.id.etOldPass);
        etPassConfrim=(EditText)findViewById (R.id.etPassConfirm);
        etUserName=(EditText)findViewById (R.id.etUserName);
        etName=(EditText)findViewById (R.id.etName);
        update=(Button)findViewById (R.id.btnUpdate);

        userDatabase=new Database (this);
        /*
        Kayıtlı kullanıcıyı getirir.
         */
        records=userDatabase.records ();
        etName.setText (records[0]);

        update.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if(etPass.getText ().toString ().trim ().length ()==0
                        ||etUserName.getText ().toString ().trim ().length ()==0
                        ||etName.getText ().toString ().trim ().length ()==0
                        ||etPassConfrim.getText ().toString ().trim ().length ()==0
                        ||etOldPass.getText ().toString ().trim ().length ()==0)
                    Toast.makeText (getBaseContext (),R.string.empty,Toast.LENGTH_LONG).show ();
                else if(!cryptObject.md5Coder(etOldPass.getText ().toString ()).equals(records[2]))
                    Toast.makeText (getBaseContext (),R.string.checkPassword,
                            Toast.LENGTH_LONG).show ();
                else if(!(etPass.getText ().toString ().equals (etPassConfrim.getText ().toString ())))
                    Toast.makeText (getBaseContext (),
                            R.string.passwordConfirmProblem,Toast.LENGTH_LONG).show ();


                else{
                    try{
                        String passwordHash=cryptObject.md5Coder(etPass.getText ().toString ().trim ());
                        /*
                        update metodu ile kullanıcı mevcut bilgilerini günceller.
                         */
                        userDatabase.update (etName.getText ().toString ().trim (),etUserName.getText ().toString (),passwordHash);
                        Toast.makeText (getBaseContext (),
                                R.string.updateInformation,Toast.LENGTH_LONG).show ();
                        onBackPressed ();


                    }catch (Exception e){
                        Toast.makeText (getApplicationContext (),R.string.tryAgain,Toast.LENGTH_LONG).show ();
                    }
                }

            }
        });



    }
}