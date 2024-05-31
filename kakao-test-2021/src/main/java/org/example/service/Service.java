package org.example.service;

import org.example.api.dto.request.CommandsRequest;
import org.example.model.Location;
import org.example.model.Truck;

import java.util.List;

public interface Service {
    public CommandsRequest simulate(List<Truck> truckList, List<Location> locationList);
}
