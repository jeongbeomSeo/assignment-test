package org.example.point.service;

import org.example.point.model.Command;
import org.example.point.model.Location;
import org.example.point.model.Truck;

import java.util.List;

public class NothingSimuateServiceImpl implements SimulateService {
    @Override
    public void simulate(Integer problem, List<Location> locations, List<Truck> trucks, List<Command> commands) {
    }
}
