package org.example.service1;

import org.example.api.dto.request.CommandsRequest;
import org.example.model.Commands;
import org.example.model.Location;
import org.example.model.Truck;

import java.util.ArrayList;
import java.util.List;

public interface Service {
    public CommandsRequest simulate(List<Truck> truckList, List<Location> locationList);

    default List<Commands> initCommandRequest(List<Truck> truckList) {
        List<Commands> commands = new ArrayList<>();
        for (int i = 0; i < truckList.size(); i++) {
            commands.add(new Commands(truckList.get(i).getId(), new ArrayList<>()));
        }

        return commands;
    }
}
