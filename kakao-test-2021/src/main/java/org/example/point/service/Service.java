package org.example.point.service;

import lombok.extern.slf4j.Slf4j;
import org.example.point.api.dto.SimulateRequest;
import org.example.point.dto.TruckMoveDTO;
import org.example.point.model.Command;
import org.example.point.model.Location;
import org.example.point.model.Point;
import org.example.point.model.Truck;
import org.example.point.register.HotPlaceRegister;
import org.example.point.register.HotPlaceSet;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Service {

    private SimulateService simulateService;

    public Service(SimulateService service) {
        simulateService = service;
    }

    public SimulateRequest createSimulateRequest(Integer problem, Integer time, List<Location> locations, List<Truck> trucks) {

        List<Command> commands = initCommandList(trucks.size());

        if (problem == 2) {
            // Hot Place Logic
            if (time % 240 >= 230) {
                HotPlaceRegister.prevHotPlaceSet = HotPlaceRegister.getCurrentHotPlaceSet();
                HotPlaceService.preWork(problem, locations, trucks, commands);
            }
            if (time % 240 <= 15) {
                // 이전 Hot Place 후 처리
                List<Truck> hotPlaceTrucks = new ArrayList<>();
                for (int i = 0; i < 2; i++) hotPlaceTrucks.add(trucks.get(i));
                HotPlaceService.postWork(problem, locations, hotPlaceTrucks, commands);
            }
            // Hot Place 선별
            HotPlaceSet hotPlaceSet = HotPlaceRegister.getCurrentHotPlaceSet();

            if (hotPlaceSet != null) {
                log.info(hotPlaceSet.toString());
                List<Truck> hotPlaceTrucks = new ArrayList<>();
                for (int i = 0; i < 2; i++) hotPlaceTrucks.add(trucks.get(i));
                HotPlaceService.work(problem, hotPlaceSet, locations, hotPlaceTrucks, commands);
            }
        }

        simulateService.simulate(problem, locations, trucks, commands);

        return new SimulateRequest(commands);
    }

    private List<Command> initCommandList(int truckSize) {

        List<Command> commands = new ArrayList<>();
        for (int i = 0; i < truckSize; i++) {
            commands.add(new Command(i, new ArrayList<>()));
        }

        return commands;
    }
}
