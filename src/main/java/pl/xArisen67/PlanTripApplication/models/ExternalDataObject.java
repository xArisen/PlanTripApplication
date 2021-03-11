package pl.xArisen67.PlanTripApplication.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import pl.xArisen67.PlanTripApplication.models.distance.DistanceCollection;
import pl.xArisen67.PlanTripApplication.models.transportation.Transportation;
import pl.xArisen67.PlanTripApplication.models.weather.Week;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Week.class, name = "week"),
        @JsonSubTypes.Type(value = Transportation.class, name = "transportation"),
        @JsonSubTypes.Type(value = DistanceCollection.class, name = "distances") })
public abstract class ExternalDataObject {

}
