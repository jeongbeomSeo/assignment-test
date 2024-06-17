package org.example;

import org.example.api.dto.request.SimulateRequestDTO;
import org.example.model.Command;
import org.example.model.Location;
import org.example.model.Truck;

import java.util.List;

public class NothingService implements Service {
    @Override
    public SimulateRequestDTO simulate(Integer problem, List<Location> locations, List<Truck> trucks) {
        Integer size = problem == 1 ? 5 : 10;
        List<Command> commands = initCommands(size);
        return new SimulateRequestDTO(commands);
    }
}
