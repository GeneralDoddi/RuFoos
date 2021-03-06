package com.example.RuFoos.team;

import android.util.Log;
import com.example.RuFoos.domain.Team;
import com.example.RuFoos.extentions.StreamConverter;
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
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BearThor on 3.11.2014.
 */
public class TeamServiceData implements TeamService {

    private final String BASE_URL = "http://212.30.192.61:10000/api";
    private ObjectMapper mapper = new ObjectMapper();
    private StreamConverter converter = new StreamConverter();
    private HttpClient client = new DefaultHttpClient();

    @Override
    public Team getTeamByName(String name) {
        final String url = "/teams/getteambyname";
        Team team = new Team();
        StreamConverter converter = new StreamConverter();
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(BASE_URL + url + name);

        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();

            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                String jsonResponse = converter.convertInputStreamToString(content);
                System.out.println(jsonResponse);
                team = mapper.readValue(jsonResponse, Team.class);

            }
            else {
                Log.e("Failed to get JSON object", "Error getting resource");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return team;

    }

    @Override
    public String addTeam(Team team, String token) {

        final String url = "/teams/addteam";
        HttpPost httpPost = new HttpPost(BASE_URL + url);
        ObjectMapper mapper = new ObjectMapper();
        String result;
        HttpResponse response = null;
        try {

            String jsonString = mapper.writeValueAsString(team);
            token = ",\"token\": \"" + token + "\"}";
            String regex = "\\}";
            System.out.println("DA string " + jsonString);
            jsonString = jsonString.replaceAll(regex, token);
            System.out.printf(jsonString);
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
            if (inputStream != null)
               result = converter.convertInputStreamToString(inputStream);
            else
               result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return "Registered";


    }

    @Override
    public List<Team> getAllTeams() {

            List<Team> teams = new ArrayList<Team>();
            final String url = "/teams/getallteams";

            HttpGet httpGet = new HttpGet(BASE_URL + url);

            try {
                HttpResponse response = client.execute(httpGet);
                StatusLine statusLine = response.getStatusLine();
                int statusCode = statusLine.getStatusCode();

                if (statusCode == 200) {
                    HttpEntity entity = response.getEntity();
                    InputStream content = entity.getContent();
                    String jsonResponse = converter.convertInputStreamToString(content);



                    teams = mapper.readValue(jsonResponse,
                            new TypeReference<List<Team>>() {
                            });

                } else {
                    Log.e("Failed to get JSON object", "Error getting resource");
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();


            }
            return teams;

    }
    @Override
    public ArrayList<Team> getMyTeams(String username){
        ArrayList<Team> teams = new ArrayList<Team>();
        final String url = "/users/" + username + "/teams";
        StreamConverter converter = new StreamConverter();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(BASE_URL + url);

        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();

            if (statusCode == 200 || statusCode == 201) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                String jsonResponse = converter.convertInputStreamToString(content);
                if (jsonResponse.contentEquals("null")) {
                    return null;
                }
                teams = mapper.readValue(jsonResponse, mapper.getTypeFactory().constructCollectionType(List.class, Team.class));
                System.out.println("System " + teams);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return teams;
    }
}
