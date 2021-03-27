package pl.xArisen67.PlanTripApplication.services.externalData.api1;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.xArisen67.PlanTripApplication.exceptions.GettingDataFromUrlException;
import pl.xArisen67.PlanTripApplication.exceptions.JsonToObjectMappingException;
import pl.xArisen67.PlanTripApplication.exceptions.UpdateDataException;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalTime;
import java.util.*;

@Service
public class TransportationApi1Service implements TransportationService {
    private Transportation transportation;
    private URL transportationDataUrl;

    //default takes Company1 data
    public TransportationApi1Service() throws MalformedURLException, UpdateDataException {
        changeTransportationDataUrl(Company1.TRANSPORTATION_DATA_URL.toString());
        updateTransportationData();
    }

    public TransportationApi1Service(String transportationDataUrl) throws MalformedURLException, UpdateDataException{
        changeTransportationDataUrl(transportationDataUrl);
    }

    @Override
    public void changeTransportationDataUrl(String transportationDataUrl) throws MalformedURLException, UpdateDataException{
        this.transportationDataUrl = new URL(transportationDataUrl);
        updateTransportationData();
    }

    @Scheduled(fixedDelay = 1000 * 60 * 5) //Refresh time every 5 minutes
    private void updateTransportationData() throws UpdateDataException{
        try {
            String urlJsonTransportationData = ExternalApiStringReader.readStringFromConnection(
                    ExternalApiConnector.getHttpUrlConnection(transportationDataUrl)
            );
            String resString = JsonFormatter.addTypeToJsonDataInTheBeginning(urlJsonTransportationData, "transportation");
            transportation = (Transportation) JsonMapper.mapJsonToObject(resString, transportation);
        }catch (GettingDataFromUrlException | JsonToObjectMappingException | IOException e){
            throw new UpdateDataException("Updating local data, by using external API failed.", e);
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
