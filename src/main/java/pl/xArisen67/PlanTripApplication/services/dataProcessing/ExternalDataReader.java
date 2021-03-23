package pl.xArisen67.PlanTripApplication.services.dataProcessing;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import pl.xArisen67.PlanTripApplication.exceptions.GettingDataFromUrlException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class ExternalDataReader {

    public static String readStringFromUrl(String url) throws GettingDataFromUrlException{
        HttpURLConnection con = null;
        int responseCode;
        StringBuffer response = new StringBuffer();

        try{
            con = getHttpUrlConnection(url);
            responseCode = con.getResponseCode();

            BufferedReader in = getBufferedReaderForConnection(con);
            getDataFromBufferedReader(response, in);
        }catch(IOException e){
            throw new GettingDataFromUrlException("Error during getting data from Url connection.", e);
        }finally {
            if (con != null){
                con.disconnect();
            }
        }
        return response.toString();
    }

    private static void getDataFromBufferedReader(StringBuffer response, BufferedReader in) throws IOException {
        String inputLine;
        while ((inputLine = in.readLine()) != null){
            response.append(inputLine);
        }
        in.close();
    }

    @NotNull
    private static BufferedReader getBufferedReaderForConnection(HttpURLConnection con) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream())
        );
        return in;
    }

    public static HttpURLConnection getHttpUrlConnection(String url) throws IOException {
        URL obj = new URL(url);
        return (HttpURLConnection) obj.openConnection();
    }
}
