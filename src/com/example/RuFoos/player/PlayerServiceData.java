package com.example.RuFoos.player;

import android.util.Log;
import com.example.RuFoos.domain.Player;
import com.example.RuFoos.extentions.StreamConverter;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.InputStream;

/**
 * Created by BearThor on 3.11.2014.
 */
public class PlayerServiceData implements PlayerService{

    private final String BASE_URL = "http://212.30.192.61:10000/api";
    private ObjectMapper mapper = new ObjectMapper();
    private StreamConverter converter = new StreamConverter();
    private HttpClient client = new DefaultHttpClient();
    @Override
    public int updatePlayer(Player player) {


            final String url = "/users/updateuser";
            HttpPut httpPut = new HttpPut(BASE_URL + url);
            ObjectMapper mapper = new ObjectMapper();
            HttpResponse response = null;

        try {
                String result = null;
                String jsonString = mapper.writeValueAsString(player);
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
