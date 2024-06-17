package org.example;

import org.example.api.dto.request.SimulateRequestDTO;
import org.example.model.Command;
import org.example.model.Location;
import org.example.model.Truck;

import java.util.ArrayList;
import java.util.List;

public interface Service {
    public SimulateRequestDTO simulate(Integer problem, List<Location> locations, List<Truck> trucks);

    default List<Command> initCommands(Integer size) {

        List<Command> commands = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            commands.add(new Command(i, new ArrayList<>()));
        }

        return commands;
    }
}
