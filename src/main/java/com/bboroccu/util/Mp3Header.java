package com.bboroccu.util;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
/**
 * Created by bboroccu on 15. 10. 13..
 */
public class Mp3Header {
    byte Song1[] = new byte[128]; //노래 제목
    byte Song[] = new byte[31]; //노래 제목
    byte Gasu[] = new byte[31]; //가수 이름
    byte Albm[] = new byte[31]; //수록된 앨범 이름
    byte Year[] = new byte[05]; //발표된 해 - 앨범의 버젼
    byte Note[] = new byte[29]; //노래에 대한 설명
    public Mp3Header(String filePath)
    {
        try {
            parsing(filePath);
        } catch (Exception ex)
        {

        }
    }
    public void parsing(String filePath) throws Exception
    {
        File f = new File(filePath);
        RandomAccessFile rf = new RandomAccessFile(f , "r");
        rf.seek(rf.length()-128);
        rf.read( Song1 ,0 ,3 );
        rf.read( Song ,0 ,30 );
        rf.read( Gasu ,0 ,30 );
        rf.read( Albm ,0 ,30 );
        rf.read( Year ,0 ,4 );
        rf.read( Note ,0 ,28 );
        System.out.println("노래제목--"+ LocalString(Song));
        System.out.println("노래제목2--" + LocalString(Song1));
        System.out.println("가 수--"+ LocalString(Gasu));
        System.out.println("앨 범--"+ LocalString(Albm));
        System.out.println("년 도--"+ LocalString(Year));
        System.out.println("노 트--"+ LocalString(Note));
    }
    public String getSong()
    {
        return LocalString(Song);
    }
    public String getSong1()
    {
        return LocalString(Song1);
    }
    public String getGasu()
    {
        return LocalString(Gasu);
    }
    public String getAlbum()
    {
        return LocalString(Albm);
    }
    public String getYear()
    {
        return LocalString(Year);
    }
    public String getComment()
    {
        return LocalString(Note);
    }
    public static String LocalString( byte[] b)
    {
        try {
            CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
            try {
                CharBuffer r = decoder.decode( ByteBuffer.wrap( b));
                return r.toString();
            } catch (CharacterCodingException e) {
                return new String( b, "EUC-KR");
            }
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        return "";
    }
    public static String asc2ksc(String str)
    {
        if(str == null)
            return null;
        String result = null;
        byte rawBytes[] = null;
        try
        {
            rawBytes = str.getBytes("8859_1");
            result = new String(rawBytes, "UTF-8");
        }
        catch(UnsupportedEncodingException e)
        {
            System.err.println(e.toString());
        }
        return result;
    }

    public static String ksc2asc(String str)
    {
        if(str == null)
            return null;
        String result = null;
        byte rawBytes[] = null;
        try
        {
            rawBytes = str.getBytes("KSC5601");
            result = new String(rawBytes, "8859_1");
        }
        catch(UnsupportedEncodingException e)
        {
            System.err.println(e.toString());
        }
        return result;
    }
}
