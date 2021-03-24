package pl.xArisen67.PlanTripApplication.services.dataProcessing;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ExternalApiConnector {
    public static HttpURLConnection getHttpUrlConnection(URL url) throws IOException{
        try {
            return (HttpURLConnection) url.openConnection();
        }catch (IOException e){
            throw new IOException("Error during creating connection.", e);
        }
    }

    public static URL createUrlFromString(String url){
        try {
            return new URL(url);
        }catch (MalformedURLException e){
            throw new IllegalArgumentException("Url cannot be created from passed string.", e);
        }
    }
}
