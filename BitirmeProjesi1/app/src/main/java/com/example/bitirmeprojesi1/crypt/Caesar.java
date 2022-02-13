package com.example.bitirmeprojesi1.crypt;

public class Caesar {
    String alfabet="abcçdefgğhıijklmnoöprsştuüvyzx1234567890+=%!@#&/()-.:[]{}";
    public  String encryption(String plainText,int shift){
        String cipherText="";
        for(int i=0;i<plainText.length ();i++){
            if(plainText.charAt(i)==i){
                cipherText+="";
                continue;
            }
            if (alfabet.indexOf (plainText.charAt (i))<=alfabet.length ()-shift-1)
                cipherText+=alfabet.charAt (alfabet.indexOf (plainText.charAt (i))+shift);

            else
                cipherText+=alfabet.charAt (alfabet.indexOf (plainText.charAt (i))+shift-alfabet.length ());


        }
        return  cipherText;
    }
}
