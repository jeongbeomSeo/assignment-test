package org.example.service1;

import org.example.api.dto.request.CommandsRequest;
import org.example.model.Commands;
import org.example.model.Location;
import org.example.model.Truck;

import java.util.ArrayList;
import java.util.List;

public class NothingServiceImpl implements Service {
    @Override
    public CommandsRequest simulate(List<Truck> truckList, List<Location> locationList) {
        List<Commands> commandList = initCommandRequest(truckList);

        return new CommandsRequest(commandList);
    }
}
