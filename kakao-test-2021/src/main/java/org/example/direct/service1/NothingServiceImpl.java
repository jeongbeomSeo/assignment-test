package org.example.direct.service1;

import org.example.direct.api.dto.request.CommandsRequest;
import org.example.direct.model.Commands;
import org.example.direct.model.Location;
import org.example.direct.model.Truck;

import java.util.List;

public class NothingServiceImpl implements Service {
    @Override
    public CommandsRequest simulate(List<Truck> truckList, List<Location> locationList) {
        List<Commands> commandList = initCommandRequest(truckList);

        return new CommandsRequest(commandList);
    }
}
