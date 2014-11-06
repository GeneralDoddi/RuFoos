package com.example.RuFoos.user;

import android.util.Log;
import com.example.RuFoos.domain.QuickMatch;
import com.example.RuFoos.domain.User;
import com.example.RuFoos.extentions.StreamConverter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BearThor on 3.11.2014.
 */
public class UserServiceData implements UserService {
    private final String BASE_URL = "http://212.30.192.61:10000/api";
    private ObjectMapper mapper = new ObjectMapper();
    private StreamConverter converter = new StreamConverter();
    private HttpClient client = new DefaultHttpClient();

    @Override
    public String addUser(User user) {

        final String url = "/users/register";
        HttpPost httpPost = new HttpPost(BASE_URL + url);
        ObjectMapper mapper = new ObjectMapper();
        String result = null;
        HttpResponse response = null;
        String jsonString = null;
        try {

            jsonString = mapper.writeValueAsString(user);

            StringEntity se = new StringEntity(jsonString);
            httpPost.setEntity(se);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            response = client.execute(httpPost);
            InputStream inputStream = response.getEntity().getContent();

            if (inputStream != null) {
                String json = null;
                json = converter.convertInputStreamToString(inputStream);
                JsonNode actualObj = mapper.readTree(json);
                result = String.valueOf(actualObj.get("response").asText());
                System.out.println(json);
                return result;
            }

        } catch (Exception e) {
            e.printStackTrace();
        };

        return result;
    }

    @Override
    public User getUserByUsername(String username) {
        User user = new User();
        final String url = "/users/getuserbyname/";
        StreamConverter converter = new StreamConverter();

        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(BASE_URL + url + username);

        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();

            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                String jsonResponse = converter.convertInputStreamToString(content);
                if (jsonResponse.contentEquals("null")) {
                    return null;
                }
                user = mapper.readValue(jsonResponse, User.class);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        final String url = "/users/getallusers";

        HttpGet httpGet = new HttpGet(BASE_URL + url);

        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();

            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                String jsonResponse = converter.convertInputStreamToString(content);

                users = mapper.readValue(jsonResponse,
                        new TypeReference<List<User>>() {
                        });

            } else {
                Log.e("Failed to get JSON object", "Error getting resource");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public int updateUser(User user) {

        final String url = "/users/updateuser";
        HttpPut httpPut = new HttpPut(BASE_URL + url);
        ObjectMapper mapper = new ObjectMapper();

        HttpResponse response = null;
        try {
            String result = null;
            String jsonString = mapper.writeValueAsString(user);
            StringEntity se = new StringEntity(jsonString);
            httpPut.setEntity(se);
            httpPut.setHeader("Accept", "application/json");
            httpPut.setHeader("Content-type", "application/json");
            response = client.execute(httpPut);
            InputStream inputStream = response.getEntity().getContent();
            if (inputStream != null)
                result = converter.convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return response.getStatusLine().getStatusCode();

    }
}

