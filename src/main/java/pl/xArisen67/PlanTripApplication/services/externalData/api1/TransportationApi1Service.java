package pl.xArisen67.PlanTripApplication.services.externalData.api1;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.xArisen67.PlanTripApplication.exceptions.GettingDataFromUrlException;
import pl.xArisen67.PlanTripApplication.exceptions.JsonToObjectMappingException;
import pl.xArisen67.PlanTripApplication.models.externalData.api1.transportation.Timetable;
import pl.xArisen67.PlanTripApplication.models.externalData.api1.transportation.Transportation;
import pl.xArisen67.PlanTripApplication.models.externalData.api1.transportation.Travel;
import pl.xArisen67.PlanTripApplication.services.dataProcessing.ExternalApiConnector;
import pl.xArisen67.PlanTripApplication.services.dataProcessing.ExternalApiStringReader;
import pl.xArisen67.PlanTripApplication.services.dataProcessing.JsonFormatter;
import pl.xArisen67.PlanTripApplication.services.dataProcessing.JsonMapper;
import pl.xArisen67.PlanTripApplication.services.externalData.providers.Company1;
import pl.xArisen67.PlanTripApplication.services.externalData.interfaces.TransportationService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.*;

@Service
public class TransportationApi1Service implements TransportationService {
    private Transportation transportation;
    private URL transportationDataUrl;

    //default takes Company1 data
    public TransportationApi1Service() {
        try {
            transportationDataUrl = ExternalApiConnector.createUrlFromString(Company1.TRANSPORTATION_DATA_URL.toString());
        }catch (IllegalArgumentException e){
            //TODO in every Ap1Service
        }
        updateTransportationData();
    }

    public TransportationApi1Service(String transportationDataUrl){
        changeTransportationDataUrl(transportationDataUrl);
    }

    @Override
    public void changeTransportationDataUrl(String transportationDataUrl){
        this.transportationDataUrl = ExternalApiConnector.createUrlFromString(transportationDataUrl);
        updateTransportationData();
    }

    @Scheduled(fixedDelay = 1000 * 1 * 5) //Refresh time every 5 minutes
    private void updateTransportationData(){
        String urlJsonTransportationData = "";
        try{
            urlJsonTransportationData = ExternalApiStringReader.readStringFromConnection(
                ExternalApiConnector.getHttpUrlConnection(transportationDataUrl)
            );
        }catch (GettingDataFromUrlException e){
            //TODO in every Ap1Service
        }catch (IOException e){
            //TODO in every Ap1Service
        }
        String resString = JsonFormatter.addTypeToJsonDataInTheBeginning(urlJsonTransportationData, "transportation");
        try{
            transportation = (Transportation) JsonMapper.mapJsonToObject(resString, transportation);
        }catch (JsonToObjectMappingException e){
            //TODO in every Ap1Service
        }
    }

    @Override
    public LocalTime[] getDepartureAndDestinationTime(String source, String destination){
        Timetable timetable = getTimetable(source, destination);
        LocalTime[] departureAndDestinationTime = getDepartureAndDestinationTimeFromTimetable(timetable);

        return  departureAndDestinationTime;
    }

    @Override
    public LocalTime[] getDepartureAndDestinationTimeFromTimetable(Timetable timetable){
        LocalTime[] departureAndDestinationTime = new LocalTime[2];
        Travel travel = timetable.getTravels()[0];
        departureAndDestinationTime[0]  =  LocalTime.parse(travel.getDepartureTime());
        departureAndDestinationTime[1]  =  LocalTime.parse(travel.getDestinationTime());

        return departureAndDestinationTime;
    }

    @Override
    public Timetable getTimetable(String source, String destination){
        Timetable timetable = Arrays.stream(transportation.getTimetable())
                .filter(anyTimetable -> (anyTimetable.getSource().equals(source) && anyTimetable.getDestination().equals(destination)))
                .findAny().orElseThrow(() -> new IllegalArgumentException("Invalid city."));

        return timetable;
    }
}
