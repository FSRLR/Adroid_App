package com.niit.software1721.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

public class NetworkUtils {
    public static String get(String urlPath){
        HttpURLConnection connection=null;
        InputStream is=null;
        try {
            URL url=new URL(urlPath);
            connection= (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setDoInput(true);
            if (connection.getResponseCode()==HttpURLConnection.HTTP_OK){
                is=connection.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(is));
                StringBuilder response=new StringBuilder();
                String line;
                while ((line=reader.readLine())!=null){
                    response.append(line);
                }
                return response.toString();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (connection!=null){
                connection.disconnect();
            }
            if (is!=null){
                try {
                    is.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

/*    public static String post(String urlPath,Map<String,String>params){

    }*/

    private static String getParamString(Map<String,String> params){
        StringBuffer result=new StringBuffer();
        Iterator<Map.Entry<String,String>> iterator=params.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String,String> param=iterator.next();
            String key=param.getKey();
            String value=param.getValue();
            result.append(key).append('=').append(value);
            if (iterator.hasNext()){
                result.append('&');
            }
        }
        return result.toString();
    }
}
