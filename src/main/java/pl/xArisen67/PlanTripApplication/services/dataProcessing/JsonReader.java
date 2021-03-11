package pl.xArisen67.PlanTripApplication.services.dataProcessing;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class JsonReader {
    public static String readJsonFromUrl(String url){
        StringBuffer response = new StringBuffer();

        try{
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream())
            );
            String inputLine;
            while ((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }
            in.close();

        }catch(Exception e){
            System.out.println(e);
        }

        return response.toString();
    }
}
