package org.example.service;

import org.example.model.Command;
import org.example.dto.MoveResultDTO;
import org.example.model.Location;
import org.example.model.Truck;

import java.util.ArrayList;
import java.util.List;

public class TruckService {

    public static Truck getTruckWhenHavingManyTimesAfterMoving(Integer problem, List<Truck> trucks, Integer locationId, List<Command> commands) {

        Truck result = null;
        Integer max = Integer.MIN_VALUE;
        for (int i = 0; i < trucks.size(); i++) {
            Truck curTruck = trucks.get(i);
            Integer remainCount = 10 - commands.get(curTruck.getId()).getCommand().size();
            Integer dist = Utils.getDist(problem, curTruck.getLocationId(), locationId);

            if (max < remainCount - dist) {
                max = remainCount - dist;
                result = curTruck;
            }
        }

        return result;
    }

    public static Truck getCanMoveTruckWhenHavingManyTimesAfterMoving(Integer problem, List<Truck> trucks, Integer locationId, List<Command> commands) {

        Truck result = null;
        Integer max = 1;
        for (int i = 0; i < trucks.size(); i++) {
            Truck curTruck = trucks.get(i);
            Integer remainCount = 10 - commands.get(curTruck.getId()).getCommand().size();
            Integer dist = Utils.getDist(problem, curTruck.getLocationId(), locationId);

            if (max < remainCount - dist) {
                max = remainCount - dist;
                result = curTruck;
            }
        }

        return result;
    }

    public static Truck getCanMoveTruckWhenHavingEnoughBike(Integer problem, List<Truck> trucks, Location location, List<Command> commands) {
        Truck result = null;
        List<Truck> activeTrucks = new ArrayList<>(trucks);

        while (activeTrucks.size() != 0) {
            Truck truck = TruckService.getCanMoveTruckWhenHavingManyTimesAfterMoving(problem, activeTrucks, location.getId(), commands);
            // 가용 가능한 트럭 자체가 없다.(이동 불가)
            if (truck == null) break;

            if (truck.getLoadedBikesCount() + location.getLocatedBikesCount() >= 2) {
                result = truck;
                break;
            } else {
                // 불가능하면 해당 트럭을 제외
                activeTrucks.remove(truck);
            }
        }

        return result;
    }

    // 이동시 업데이트 해야되는 요소: Truck의 Location Id, 해당 트럭의 Command
    public static MoveResultDTO move(Integer problem, Truck truck, Integer targetLocationId, Command command) {

        if (command.getCommand().size() > 10) return null;

        if (problem == 1) {
            if (truck.getLocationId() / 5 < targetLocationId / 5) {
                // 오른쪽
                return new MoveResultDTO(truck.getLocationId() + 5, 2);
            } else if (truck.getLocationId() / 5 > targetLocationId / 5) {
                // 왼쪽
                return new MoveResultDTO(truck.getLocationId() - 5, 4);
            } else if (truck.getLocationId() < targetLocationId) {
                // 위쪽
                return new MoveResultDTO(truck.getLocationId() + 1, 1);
            } else {
                // 아래쪽
                return new MoveResultDTO(truck.getLocationId() - 1, 3);
            }
        } else {
            if (truck.getLocationId() / 60 < targetLocationId / 60) {
                // 오른쪽
                return new MoveResultDTO(truck.getLocationId() + 60, 2);
            } else if (truck.getLocationId() / 60 > targetLocationId / 60) {
                // 왼쪽
                return new MoveResultDTO(truck.getLocationId() - 60, 4);
            } else if (truck.getLocationId() < targetLocationId) {
                // 위쪽
                return new MoveResultDTO(truck.getLocationId() + 1, 1);
            } else {
                // 아래쪽
                return new MoveResultDTO(truck.getLocationId() - 1, 3);
            }
        }
    }
}
