package pl.xArisen67.PlanTripApplication.services.dataProcessing;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class JsonReader {
    private static final Logger logger = LoggerFactory.getLogger(BufferedReader.class);
    public static String readJsonStringFromUrl(String url){
        HttpURLConnection con = null;
        int responseCode;
        StringBuffer response = new StringBuffer();

        try{
            con = getHttpURLConnection(url);

            responseCode = con.getResponseCode();
            BufferedReader in = getBufferedReaderForConnection(con);
            getDataFromBufferedReader(response, in);
        }catch(Exception e){
            logger.error("Context message", e);
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

    public static HttpURLConnection getHttpURLConnection(String url) throws IOException {
        URL obj = new URL(url);
        return (HttpURLConnection) obj.openConnection();
    }
}
