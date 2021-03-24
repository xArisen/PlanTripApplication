package pl.xArisen67.PlanTripApplication.services.dataProcessing;

import org.jetbrains.annotations.NotNull;
import pl.xArisen67.PlanTripApplication.exceptions.GettingDataFromUrlException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class ExternalApiStringReader {

    public static String readStringFromConnection(HttpURLConnection connection) throws GettingDataFromUrlException{
        StringBuffer response = new StringBuffer();

        try{
            BufferedReader in = getBufferedReaderForConnection(connection);
            getDataFromBufferedReader(response, in);
        }catch(IOException e){
            throw new GettingDataFromUrlException("Error during getting string from Url connection.", e);
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
}
