package pl.xArisen67.PlanTripApplication.services.externalData.api1;

import org.springframework.stereotype.Service;
import pl.xArisen67.PlanTripApplication.models.externalData.api1.distance.Distance;
import pl.xArisen67.PlanTripApplication.models.externalData.api1.distance.DistanceCollection;
import pl.xArisen67.PlanTripApplication.services.dataProcessing.JsonFormatter;
import pl.xArisen67.PlanTripApplication.services.dataProcessing.JsonMapper;
import pl.xArisen67.PlanTripApplication.services.dataProcessing.JsonReader;
import pl.xArisen67.PlanTripApplication.services.externalData.providers.Company1;
import pl.xArisen67.PlanTripApplication.services.externalData.interfaces.DistanceService;

import java.util.Arrays;
import java.util.Optional;

@Service
public class DistanceApi1Service implements DistanceService {
    private DistanceCollection distanceCollection;
    private String distanceDataUrl;

    //default takes Company1 data
    public DistanceApi1Service(){
        distanceDataUrl = Company1.DISTANCE_DATA_URL.toString();
        updateDistanceData();
    }

    public DistanceApi1Service(String distanceDataUrl){
        changeDistanceDataUrl(distanceDataUrl);
    }

    public void changeDistanceDataUrl(String distanceDataUrl) {
        this.distanceDataUrl = distanceDataUrl;
        updateDistanceData();
    }

    private void updateDistanceData(){
        //TODO should try catch JsonFormatter.addTypeToJsonDataInTheBeginning exception?
        String urlJsonDistanceData = JsonReader.readJsonStringFromUrl(distanceDataUrl);
        String resString = JsonFormatter.addTypeToJsonDataInTheBeginning(urlJsonDistanceData, "distances");
        distanceCollection = (DistanceCollection) JsonMapper.mapJsonToObject(resString, distanceCollection);
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
