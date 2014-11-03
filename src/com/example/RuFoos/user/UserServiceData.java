package com.example.RuFoos.user;

import android.util.Log;
import com.example.RuFoos.domain.User;
import com.example.RuFoos.extentions.StreamConverter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * Created by BearThor on 3.11.2014.
 */
public class UserServiceData implements UserService {
    private final String BASE_URL = "http://212.30.192.61:10000/api";
    @Override
    public int addUser(User user) {
        return 0;
    }

    @Override
    public User getUserByUsername(String username) {
            User user = new User();
            user.setUsername("derp");

            final String url = "/users/getuserbyname/";
            StreamConverter converter = new StreamConverter();
            StringBuilder builder = new StringBuilder();
            HttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(BASE_URL+url+username);
            try{
                HttpResponse response = client.execute(httpGet);
                StatusLine statusLine = response.getStatusLine();
                int statusCode = statusLine.getStatusCode();
                if(statusCode == 200){
                    HttpEntity entity = response.getEntity();
                    InputStream content = entity.getContent();
                    String jsonResponse = converter.convertInputStreamToString(content);
                    System.out.println(jsonResponse);
                    }

            else {
                    Log.e("Failed to get JSON object","Error getting resource");
                }
            }catch(ClientProtocolException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
        return user;
    }

    public User getUserByUserName2(String username)
    {
        try {
            final String url = BASE_URL + "/users/getuserbyname/" + username;
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            User user = restTemplate.getForObject(url, User.class);
            return user;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}

