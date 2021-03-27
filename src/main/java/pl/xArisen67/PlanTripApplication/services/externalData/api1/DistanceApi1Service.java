package pl.xArisen67.PlanTripApplication.services.externalData.api1;

import org.springframework.stereotype.Service;
import pl.xArisen67.PlanTripApplication.exceptions.GettingDataFromUrlException;
import pl.xArisen67.PlanTripApplication.exceptions.JsonToObjectMappingException;
import pl.xArisen67.PlanTripApplication.exceptions.UpdateDataException;
import pl.xArisen67.PlanTripApplication.models.externalData.api1.distance.Distance;
import pl.xArisen67.PlanTripApplication.models.externalData.api1.distance.DistanceCollection;
import pl.xArisen67.PlanTripApplication.services.dataProcessing.ExternalApiConnector;
import pl.xArisen67.PlanTripApplication.services.dataProcessing.ExternalApiStringReader;
import pl.xArisen67.PlanTripApplication.services.dataProcessing.JsonFormatter;
import pl.xArisen67.PlanTripApplication.services.dataProcessing.JsonMapper;
import pl.xArisen67.PlanTripApplication.services.externalData.providers.Company1;
import pl.xArisen67.PlanTripApplication.services.externalData.interfaces.DistanceService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;

@Service
public class DistanceApi1Service implements DistanceService {
    private DistanceCollection distanceCollection;
    private URL distanceDataUrl;
    //private static final Logger logger = LoggerFactory.getLogger(DistanceApi1Service.class);
    //logger.error("Context message", e);

    //default takes Company1 data
    public DistanceApi1Service() throws MalformedURLException, UpdateDataException {
        changeDistanceDataUrl(Company1.DISTANCE_DATA_URL.toString());
        updateDistanceData();
    }

    public DistanceApi1Service(String distanceDataUrl) throws MalformedURLException, UpdateDataException{
        changeDistanceDataUrl(distanceDataUrl);
    }

    public void changeDistanceDataUrl(String distanceDataUrl) throws MalformedURLException, UpdateDataException {
        this.distanceDataUrl = new URL(distanceDataUrl);
        updateDistanceData();
    }

    public void updateDistanceData() throws UpdateDataException{
        try{
            String urlJsonDistanceData = ExternalApiStringReader.readStringFromConnection(
                    ExternalApiConnector.getHttpUrlConnection(distanceDataUrl)
            );
            String resString = JsonFormatter.addTypeToJsonDataInTheBeginning(urlJsonDistanceData, "distances");
            distanceCollection = (DistanceCollection) JsonMapper.mapJsonToObject(resString, distanceCollection);
        }catch (GettingDataFromUrlException | JsonToObjectMappingException | IOException e){
            throw new UpdateDataException("Updating local data, by using external API failed.", e);
        }
    }

    @Override
    public int getDistanceBetweenCities(String source, String destination){
        Optional <Distance> distance = Arrays.stream(distanceCollection.getDistances())
                .filter(anyDistance -> (anyDistance.getSource().equals(source) && anyDistance.getDestination().equals(destination)))
                .findAny();

        if(!distance.isPresent()){
            distance = Arrays.stream(distanceCollection.getDistances())
                    .filter(anyDistance -> (anyDistance.getSource().equals(destination) && anyDistance.getDestination().equals(source)))
                    .findAny();
        }

        if(distance.isPresent()){
            return distance.get().getDistance();
        }
        else{
            throw new IllegalArgumentException("Invalid city.");
        }
    }
}
