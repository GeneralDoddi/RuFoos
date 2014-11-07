package com.example.RuFoos.match;

import android.util.Log;
import com.example.RuFoos.domain.*;
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
import java.util.List;

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
    public QuickMatch quickMatchSignUp(User user, String token) {
        final String url = "/pickupmatch/signup";
        HttpPost httpPost = new HttpPost(BASE_URL + url);
        ObjectMapper mapper = new ObjectMapper();
        String result = null;
        HttpResponse response = null;
        QuickMatch quickMatch = new QuickMatch();

        try {

            String jsonString = mapper.writeValueAsString(user);
            token = ",\"token\": \"" + token + "\"}";
            String regex = "\\}";
            System.out.println("DA string " + jsonString);
            jsonString = jsonString.replaceAll(regex, token);

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
        final String url = "/pickupmatch/getpickupmatch/";
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
        System.out.println("MatchServicedata quickmatch result: " + quickMatch.getId() + " " + quickMatch.getPlayers() + " " + quickMatch.getVersion() + " " + quickMatch.isFull());
        return quickMatch;
    }

    @Override
    public QuickMatch leaveQuickMatch(String token){
        final String url = "/pickupmatch/removesignup";
        HttpPost httpPost = new HttpPost(BASE_URL + url);
        ObjectMapper mapper = new ObjectMapper();
        String result = "";
        HttpResponse response = null;
        QuickMatch quickMatch = new QuickMatch();

        try {
            String jsonString = "{\"token\": \"" + token + "\"}";
            //String jsonString = mapper.writeValueAsString(user);
            //String regex = "\\}";
            //jsonString = jsonString.replaceAll(regex, token);

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
                    System.out.println("qui: " + quickMatch.getId() + " " + quickMatch.getVersion() + " " + quickMatch.isFull() + " " + quickMatch.getPlayers());
                }
                else if(response.getStatusLine().getStatusCode() == 503) {
                    System.out.println("got 503");
                    quickMatch = null;
                }
                else {
                    System.out.println("Got something else");
                }
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

    @Override
    public TeamMatch registerTeamMatch(TeamMatch teamMatch, String token){
        System.out.println("ENTERED");
        final String url = "/pickupmatch/registerteammatch";
        HttpPost httpPost = new HttpPost(BASE_URL + url);
        ObjectMapper mapper = new ObjectMapper();
        String result = null;
        HttpResponse response = null;

        String jsonString = null;
        try {
            token = ",\"token\": \"" + token + "\"}";
            jsonString = mapper.writeValueAsString(teamMatch);
            String regex = "\\}";
            jsonString = jsonString.replaceAll(regex, token);
            System.out.println(jsonString);
            StringEntity se = new StringEntity(jsonString);
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            response = client.execute(httpPost);

            System.out.println(response);

            InputStream inputStream = response.getEntity().getContent();

            // 10. convert inputstream to string
            if (inputStream != null) {
                result = converter.convertInputStreamToString(inputStream);
                //System.out.println("result: " + result);
                if(response.getStatusLine().getStatusCode() == 201) {
                    //System.out.println("got 201");
                    teamMatch = mapper.readValue(result, TeamMatch.class);
                    System.out.println("qui: " + teamMatch.getId() + " Winners: " + teamMatch.getWinnerteam() + " LosersL " + teamMatch.getLoserteam() + " UnderTheTable? " + teamMatch.isUnderTable());
                }
                else if(response.getStatusLine().getStatusCode() == 503) {
                    System.out.println("got 503");
                    teamMatch = null;
                }
                else {
                    System.out.println("Got something else");
                }
            }
            else
                result = "Did not work!";
            //System.out.println("result " + result);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return teamMatch;
    }

    @Override
    public ExhibitionMatch registerExhibitionMatch(ExhibitionMatch exhibitionMatch, String token, String pickupId){
        final String url = "/pickupmatch/registerquickmatch";
        HttpPost httpPost = new HttpPost(BASE_URL + url);
        ObjectMapper mapper = new ObjectMapper();
        String result = null;
        HttpResponse response = null;

        String jsonString = null;
        try {
            if(pickupId != null) {
                token = ",\"token\": \"" + token + "\", \"pickupId\": \"" + pickupId + "\"}";
            }
            else {
                token = ",\"token\": \"" + token + "\"}";
            }

            jsonString = mapper.writeValueAsString(exhibitionMatch);
            String regex = "\\}";
            jsonString = jsonString.replaceAll(regex, token);
            System.out.println(jsonString);
            StringEntity se = new StringEntity(jsonString);
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            response = client.execute(httpPost);

            InputStream inputStream = response.getEntity().getContent();

            // 10. convert inputstream to string
            if (inputStream != null) {
                result = converter.convertInputStreamToString(inputStream);
                //System.out.println("result: " + result);
                if(response.getStatusLine().getStatusCode() == 201) {
                    //System.out.println("got 201");
                    exhibitionMatch = mapper.readValue(result, ExhibitionMatch.class);
                    System.out.println("qui: " + exhibitionMatch.getId() + " Winners: " + exhibitionMatch.getWinners() + " Losers: " + exhibitionMatch.getLosers()+ " UnderTheTable? " + exhibitionMatch.isUnderTable());
                }
                else if(response.getStatusLine().getStatusCode() == 503) {
                    System.out.println("got 503");
                    exhibitionMatch = null;
                }
                else {
                    System.out.println("Got something else");
                }
            }
            else
                result = "Did not work!";
            //System.out.println("result " + result);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return exhibitionMatch;
    }

    @Override
    public QuickMatch confirmPickup(String token) {
        final String url = "/pickupmatch/confirmpickup";
        HttpPost httpPost = new HttpPost(BASE_URL + url);
        ObjectMapper mapper = new ObjectMapper();
        String result = null;
        HttpResponse response = null;
        QuickMatch quickMatch = new QuickMatch();

        String jsonString = null;
        try {
            token = "{\"token\": \"" + token + "\"}";
            System.out.println(token);
            StringEntity se = new StringEntity(token);
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            response = client.execute(httpPost);

            System.out.println(response);

            // 9. receive response as inputStream
            InputStream inputStream = response.getEntity().getContent();

            // 10. convert inputstream to string
            if (inputStream != null) {
                result = converter.convertInputStreamToString(inputStream);
                //System.out.println("result: " + result);
                if(response.getStatusLine().getStatusCode() == 201) {
                    System.out.println("got 201");
                    quickMatch = mapper.readValue(result, QuickMatch.class);
                    System.out.println("qui: " + quickMatch.getId() + " " + quickMatch.getVersion() + " " + quickMatch.isFull() + " " + quickMatch.getPlayers());
                }
                else if(response.getStatusLine().getStatusCode() == 503) {
                    System.out.println("got 503");
                    quickMatch = null;
                }
                else {
                    System.out.println("Got something else");
                }
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
