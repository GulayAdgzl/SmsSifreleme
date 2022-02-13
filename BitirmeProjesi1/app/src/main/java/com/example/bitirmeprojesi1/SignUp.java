package com.example.bitirmeprojesi1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bitimeprojesi22.crypt.Crypt;

public class SignUp extends AppCompatActivity {
    Button btnSignUp;
    EditText etPass,etUserName,etName,etPassConfirm;
    Database userDatabase;
    Crypt cryptObject=new Crypt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_sign_up);
        btnSignUp=(Button)findViewById (R.id.btnsignUp);
        etPass=(EditText)findViewById (R.id.etPass);
        etPassConfirm=(EditText)findViewById (R.id.etPassConfirm);
        etUserName=(EditText)findViewById (R.id.etUserName);
        etName=(EditText)findViewById (R.id.etName);
        userDatabase=new Database(this);

        /*Sign Up butonuna tıklandığında

         */
        btnSignUp.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                if (etPass.getText ().toString ().trim ().length () == 0
                        || etUserName.getText ().toString ().trim ().length () == 0
                        || etName.getText ().toString ().trim ().length () == 0
                        || etPassConfirm.getText ().toString ().length () == 0)
                    Toast.makeText (getBaseContext (), R.string.empty, Toast.LENGTH_LONG).show ();
                else if (!(etPass.getText ().toString ().equals (etPassConfirm.getText ().toString ())))
                    Toast.makeText (getBaseContext (),
                            R.string.passwordConfirmProblem, Toast.LENGTH_LONG).show ();
                else {
                    try {
                        /*
                        tableCount() metodu ile kayıtlı kullanıcı sayısı elde edilir
                        Bu sayı 1 den büyük olamaz
                         */
                        int count = userDatabase.tableCount ();
                        if (count != 0) {
                            /*
                            Programı kullanacak tek bir kullanıcı olması gerektğinden eğer bu sayı
                            sıfırdan byükse ekranda uyarı mesajı görüntüleyebilir.
                             */
                            Toast.makeText (getBaseContext (),
                                    R.string.reRegister, Toast.LENGTH_LONG).show ();
                        } else {
                            /*
                            Eğer kayıtlı kullanıcı yoksa addPerson() metodu
                            kullanarak veritabanına kayt işlemi gerçekleştirilir.
                             */
                            String passwordHash = cryptObject.md5Coder (etPass.getText ().toString ().trim ());
                            userDatabase.addPerson (etName.getText ().toString ().trim (), etUserName.getText ().toString (), passwordHash);
                            etName.setText ("");
                            etPass.setText ("");
                            etPassConfirm.setText ("");
                            Toast.makeText (getBaseContext (),
                                    R.string.UserRegisrationMessage, Toast.LENGTH_LONG).show ();
                            onBackPressed ();

                        }
                    } catch (Exception e) {

                    }
                }




            }
        });
    }
}