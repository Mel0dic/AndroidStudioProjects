package com.example.bengr.vigineretranslater;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bengr on 30/01/2018.
 */

public class viginereTranslate {

    public String encrypt(String toEncrypt, String encryptionKey){

        String lowerCaseEncryptionKey = encryptionKey.toLowerCase();
        char[] lowerCaseArray = lowerCaseEncryptionKey.toCharArray();
        List<Integer> key = new ArrayList<>();

        for(char letter : lowerCaseArray){
            key.add(((int) letter - 'a'));
        }

        char[] toEncryptArray = toEncrypt.toCharArray();

        int counts = 0;

        List<Character> encryptedArray = new ArrayList<>();

        for(int i = 0; i < toEncrypt.length(); i++){
            if(Character.isLowerCase(toEncryptArray[i])){
                encryptedArray.add(((char) ((((toEncryptArray[i] - 97) + key.get(counts)) % 26) + 97)));
                counts++;
            }else if(Character.isUpperCase(toEncryptArray[i])){
                encryptedArray.add(((char) ((((toEncryptArray[i] - 65) + key.get(counts)) % 26 ) + 65)));
                counts++;
            }else{
                encryptedArray.add(toEncryptArray[i]);
            }
            if(counts == (encryptionKey.length())){
                counts = 0;
            }
        }

        String returnString = "";

        for(char letter : encryptedArray){
            returnString += letter;
        }

        return returnString;
    }

    public String decrypt(String toEncrypt, String encryptionKey){

        String lowerCaseEncryptionKey = encryptionKey.toLowerCase();
        char[] lowerCaseArray = lowerCaseEncryptionKey.toCharArray();
        List<Integer> key = new ArrayList<>();

        for(char letter : lowerCaseArray){
            key.add(((int) letter - 'a'));
        }

        char[] toEncryptArray = toEncrypt.toCharArray();

        int counts = 0;

        List<Character> encryptedArray = new ArrayList<>();

        for(int i = 0; i < toEncrypt.length(); i++){
            if(Character.isLowerCase(toEncryptArray[i])){
                int DKey = (toEncryptArray[i] - key.get(counts));
                encryptedArray.add((char)((DKey < 0) ? (DKey + 26) : DKey));
                counts++;
            }else if(Character.isUpperCase(toEncryptArray[i])){
                int DKey = (toEncryptArray[i] - key.get(counts));
                encryptedArray.add((char)((DKey < 0) ? (DKey + 26) : DKey));
                counts++;
            }else{
                encryptedArray.add(toEncryptArray[i]);
            }
            if(counts == (encryptionKey.length())){
                counts = 0;
            }
        }

        String returnString = "";

        for(char letter : encryptedArray){
            returnString += letter;
        }

        return returnString;
    }
}
