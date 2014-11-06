package com.example.RuFoos.match;

import android.util.Log;
import com.example.RuFoos.domain.Match;
import com.example.RuFoos.domain.QuickMatch;
import com.example.RuFoos.domain.User;
import com.example.RuFoos.extentions.StreamConverter;
import com.example.RuFoos.match.MatchService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by BearThor on 3.11.2014.
 */
public class MatchServiceData implements MatchService {

    private final String BASE_URL = "http://212.30.192.61:10000/api";
    private ObjectMapper mapper = new ObjectMapper();
    private StreamConverter converter = new StreamConverter();
    private HttpClient client = new DefaultHttpClient();


    @Override
    public int addMatch(Match match) {
        return 0;
    }

    @Override
    public QuickMatch quickMatchSignUp(User user) {
        final String url = "/pickupmatch/signup";
        HttpPost httpPost = new HttpPost(BASE_URL + url);
        ObjectMapper mapper = new ObjectMapper();
        String result = null;
        HttpResponse response = null;
        QuickMatch quickMatch = new QuickMatch();

        try {

            String jsonString = mapper.writeValueAsString(user);
            StringEntity se = new StringEntity(jsonString);
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            response = client.execute(httpPost);

            // 9. receive response as inputStream
            InputStream inputStream = response.getEntity().getContent();

            // 10. convert inputstream to string
            if (inputStream != null) {
                result = converter.convertInputStreamToString(inputStream);
                if(response.getStatusLine().getStatusCode() == 201) {
                    quickMatch = mapper.readValue(result, QuickMatch.class);
                }
                else if(response.getStatusLine().getStatusCode() == 503) {
                    quickMatch = null;
                }

                //System.out.println("qui: " + quickMatch.getId() + " " + quickMatch.getVersion() + " " + quickMatch.isFull() + " " + quickMatch.getPlayers()[1]);
            }
            else
                result = "Did not work!";
            System.out.println("result: " + result);

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        // 11. return result
        return quickMatch;
    }


    @Override
    public QuickMatch getQuickMatchById(String id){
        QuickMatch quickMatch = new QuickMatch();
        final String url = "/pickupmatch/getpickupmath/";
        StreamConverter converter = new StreamConverter();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(BASE_URL + url + id);

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
                quickMatch = mapper.readValue(jsonResponse, QuickMatch.class);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return quickMatch;
    }

    @Override
    public QuickMatch leaveQuickMatch(User user){
        final String url = "/pickupmatch/removesignup";
        HttpPost httpPost = new HttpPost(BASE_URL + url);
        ObjectMapper mapper = new ObjectMapper();
        String result = "";
        HttpResponse response = null;
        QuickMatch quickMatch = new QuickMatch();

        try {
            String jsonString = mapper.writeValueAsString(user);
            StringEntity se = new StringEntity(jsonString);
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            response = client.execute(httpPost);

            // 9. receive response as inputStream
            InputStream inputStream = response.getEntity().getContent();

            // 10. convert inputstream to string
            if (inputStream != null) {
                result = converter.convertInputStreamToString(inputStream);
                //System.out.println("result: " + result);
                if(response.getStatusLine().getStatusCode() == 201) {
                    //System.out.println("got 201");
                    quickMatch = mapper.readValue(result, QuickMatch.class);
                }
                else if(response.getStatusLine().getStatusCode() == 503) {
                    System.out.println("got 503");
                    quickMatch = null;
                }
                else {
                    System.out.println("Got something else");
                }

                System.out.println("qui: " + quickMatch.getId() + " " + quickMatch.getVersion() + " " + quickMatch.isFull() + " " + quickMatch.getPlayers());
            }
            else
                result = "Did not work!";
            //System.out.println("result " + result);

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        // 11. return result
        //System.out.println("status " + response.getStatusLine().getStatusCode());
        return quickMatch;
    }

}
