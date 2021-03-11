package pl.xArisen67.PlanTripApplication.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.xArisen67.PlanTripApplication.exceptions.WrongResultException;
import pl.xArisen67.PlanTripApplication.services.calculation.interfaces.TimeService;
import pl.xArisen67.PlanTripApplication.services.dataProcessing.JsonMapper;

@RestController
public class TimeController {

    final
    TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping(value = "/time/walk/{source}/{destination}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getWalkTimeFromSourceToDestination(@PathVariable final String source, @PathVariable final String destination){
        int time;
        try{
            time =  timeService.getTimeInMinutesToWalkFromSourceToDestination(source, destination);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(JsonMapper.mapVariableToObject("time", time));
    }

    @GetMapping(value = "/time/bus/{source}/{destination}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBusTimeFromSourceToDestination(@PathVariable final String source, @PathVariable final String destination){
        int time;
        try{
            time =  timeService.getTimeInMinutesToBusFromSourceToDestination(source, destination);
        }catch (IllegalArgumentException e){
             return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(JsonMapper.mapVariableToObject("time", time));
    }

    @GetMapping(value = "/time/bike/{source}/{destination}/{day}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBikeTimeFromSourceToDestination(@PathVariable final String source, @PathVariable final String destination, @PathVariable final String day){
        int time;
        try{
            time =  timeService.getTimeInMinutesToBikeFromSourceToDestination(source, destination, day);
        }catch (IllegalArgumentException | WrongResultException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(JsonMapper.mapVariableToObject("time", time));
    }
}
