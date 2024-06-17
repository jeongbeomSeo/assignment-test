package org.example.point.service;

import org.example.point.dto.TruckMoveDTO;
import org.example.point.model.Command;
import org.example.point.model.Location;
import org.example.point.model.Point;
import org.example.point.model.Truck;
import org.example.point.register.*;

import java.util.List;
import java.util.Set;

public class HotPlaceService {

    public static boolean isHotPlace(Integer locationId) {
        HotPlaceSet currentHotPlaceSet = HotPlaceRegister.getCurrentHotPlaceSet();

        if (currentHotPlaceSet == null) return false;

        return currentHotPlaceSet.getLoadedHotPlace().locationId.equals(locationId)
                || currentHotPlaceSet.getUnloadedHotPlace().locationId.equals(locationId);
    }

    public static void postWork(Integer problem, List<Location> locations, List<Truck> trucks, List<Command> commands) {
        HotPlaceSet hotPlaceset = HotPlaceRegister.prevHotPlaceSet;

        if (hotPlaceset == null) return;

        HotPlace loadedHotPlace = hotPlaceset.getLoadedHotPlace();
        HotPlace unloadedHotPlace = hotPlaceset.getUnloadedHotPlace();
        if (locations.get(loadedHotPlace.locationId).getLocatedBikesCount() > 2) {
            Truck truck = trucks.get(0);
            TruckInfo truckInfo = TruckInfoRegister.getTruckInfos().get(truck.getId());
            Command command = commands.get(truck.getId());
            Location targetLocation = locations.get(loadedHotPlace.locationId);
            Point targetPoint = PointerService.convertIdToPoint(problem, targetLocation.getId());

            while (canMoveCheck(command)) {
                Point truckPoint = PointerService.convertIdToPoint(problem, truck.getLocationId());
                if (!truck.getLocationId().equals(targetLocation.getId())) {
                    TruckMoveDTO truckMoveDTO = TruckService.moveTargetPoint(truckPoint.row, truckPoint.col, targetPoint.row, targetPoint.col);
                    Integer nxtLocationId = PointerService.convertPointToId(problem, truckMoveDTO.getPoint());
                    truck.setLocationId(nxtLocationId);
                    truckInfo.setDirection(truckMoveDTO.getDirection());
                    command.getCommand().add(truckMoveDTO.getDirection());
                } else if (targetLocation.getLocatedBikesCount() < 3) {
                    targetLocation.setLocatedBikesCount(targetLocation.getLocatedBikesCount() - 1);
                    command.getCommand().add(5);
                    truck.setLoadedBikesCount(truck.getLoadedBikesCount() + 1);
                } else {
                    break;
                }
            }
        }

        if (locations.get(unloadedHotPlace.locationId).getLocatedBikesCount() > 2) {
            Truck truck = trucks.get(1);
            TruckInfo truckInfo = TruckInfoRegister.getTruckInfos().get(truck.getId());
            Command command = commands.get(truck.getId());
            Location targetLocation = locations.get(unloadedHotPlace.locationId);
            Point targetPoint = PointerService.convertIdToPoint(problem, targetLocation.getId());

            while (canMoveCheck(command)) {
                Point truckPoint = PointerService.convertIdToPoint(problem, truck.getLocationId());
                if (!truck.getLocationId().equals(targetLocation.getId())) {
                    TruckMoveDTO truckMoveDTO = TruckService.moveTargetPoint(truckPoint.row, truckPoint.col, targetPoint.row, targetPoint.col);
                    Integer nxtLocationId = PointerService.convertPointToId(problem, truckMoveDTO.getPoint());
                    truck.setLocationId(nxtLocationId);
                    truckInfo.setDirection(truckMoveDTO.getDirection());
                    command.getCommand().add(truckMoveDTO.getDirection());
                } else if (targetLocation.getLocatedBikesCount() < 3) {
                    targetLocation.setLocatedBikesCount(targetLocation.getLocatedBikesCount() - 1);
                    command.getCommand().add(5);
                    truck.setLoadedBikesCount(truck.getLoadedBikesCount() + 1);
                } else {
                    break;
                }
            }
        }
    }

    public static void preWork(Integer problem, List<Location> locations, List<Truck> trucks, List<Command> commands) {
        Set<HotPlaceSet> hotPlaceSets = HotPlaceRegister.getHotPlaceSets();
        for (HotPlaceSet hotPlaceSet : hotPlaceSets) {
            HotPlace loadedHotPlace = hotPlaceSet.getLoadedHotPlace();
            Location targetlocation = locations.get(loadedHotPlace.locationId);
            Point targetPoint = PointerService.convertIdToPoint(problem, targetlocation.getId());

            if (targetlocation.getLocatedBikesCount() == 0) {
                // Hot Place랑 가장 가까운 Truck 선정 후 이동시키고 자전거 하역 시키기
                Truck truck = TruckService.getNearestTruckHavingBike(problem, trucks, targetlocation.getId());

                TruckInfo truckInfo = TruckInfoRegister.getTruckInfos().get(truck.getId());
                Command command = commands.get(truck.getId());
                while (canMoveCheck(command) && !truck.getLocationId().equals(targetlocation.getId())) {
                    Point truckPoint = PointerService.convertIdToPoint(problem, truck.getLocationId());
                    Location curLocation = locations.get(PointerService.convertPointToId(problem, truckPoint));
                    if (truck.getLoadedBikesCount() < 2 && curLocation.getLocatedBikesCount() >= 1) {
                        curLocation.setLocatedBikesCount(curLocation.getLocatedBikesCount() - 1);
                        command.getCommand().add(5);
                        truck.setLoadedBikesCount(truck.getLoadedBikesCount() + 1);
                    } else {
                        TruckMoveDTO truckMoveDTO = TruckService.moveTargetPoint(truckPoint.row, truckPoint.col, targetPoint.row, targetPoint.col);
                        Integer nxtLocationId = PointerService.convertPointToId(problem, truckMoveDTO.getPoint());
                        truck.setLocationId(nxtLocationId);
                        truckInfo.setDirection(truckMoveDTO.getDirection());
                        command.getCommand().add(truckMoveDTO.getDirection());
                    }
                }

                while (canMoveCheck(command)) {
                    if (truck.getLoadedBikesCount() > 0 && targetlocation.getLocatedBikesCount() < 2) {
                        targetlocation.setLocatedBikesCount(targetlocation.getLocatedBikesCount() + 1);
                        command.getCommand().add(6);
                        truck.setLoadedBikesCount(truck.getLoadedBikesCount() - 1);
                    } else {
                        break;
                    }
                }

                // 움직인 트럭은 모든 임무를 마친 후 자신의 위치로 복귀 => isTrakcing = false;
                truckInfo.setTracking(false);
            }
        }
    }

    public static void work(Integer problem, HotPlaceSet hotPlaceSet, List<Location> locations, List<Truck> trucks, List<Command> commands) {

        for (int i = 0; i < trucks.size(); i++) {
            Truck truck = trucks.get(i);
            TruckInfo truckInfo = TruckInfoRegister.getTruckInfos().get(truck.getId());
            Command command = commands.get(truck.getId());

            /*
            if (truck.getLocationId().equals(hotPlaceSet.getLoadedHotPlace().locationId)
                || truck.getLocationId().equals(hotPlaceSet.getUnloadedHotPlace().locationId)) {
                if (truck.getLocationId().equals(hotPlaceSet.getLoadedHotPlace().locationId)) {
                    HotPlace hotPlace = hotPlaceSet.getLoadedHotPlace();
                    Location location = locations.get(hotPlace.locationId);

                    while (canMoveCheck(command) && truck.getLoadedBikesCount() > 0) {
                        truck.setLoadedBikesCount(truck.getLoadedBikesCount() - 1);
                        location.setLocatedBikesCount(location.getLocatedBikesCount() + 1);
                        command.getCommand().add(6);
                    }
                } else {
                    HotPlace hotPlace = hotPlaceSet.getUnloadedHotPlace();
                    Location location = locations.get(hotPlace.locationId);

                    while (canMoveCheck(command) && location.getLocatedBikesCount() > 0) {
                        truck.setLoadedBikesCount(truck.getLoadedBikesCount() + 1);
                        location.setLocatedBikesCount(location.getLocatedBikesCount() - 1);
                        command.getCommand().add(5);
                    }
                }
            }*/

            while (canMoveCheck(command)) {
                Point truckPoint = PointerService.convertIdToPoint(problem, truck.getLocationId());
                if (truckInfo.isLoading()) {
                    HotPlace hotPlace = hotPlaceSet.getUnloadedHotPlace();
                    Location location = locations.get(hotPlace.locationId);
                    Point point = PointerService.convertIdToPoint(problem, location.getId());
                    if (!truck.getLocationId().equals(location.getId())) {
                        TruckMoveDTO truckMoveDTO = TruckService.moveTargetPoint(truckPoint.row, truckPoint.col, point.row, point.col);
                        Integer nxtLocationId = PointerService.convertPointToId(problem, truckMoveDTO.getPoint());
                        truck.setLocationId(nxtLocationId);
                        truckInfo.setDirection(truckMoveDTO.getDirection());
                        command.getCommand().add(truckMoveDTO.getDirection());
                    } else {
                        while (canMoveCheck(command) && location.getLocatedBikesCount() > 0) {
                            truck.setLoadedBikesCount(truck.getLoadedBikesCount() + 1);
                            location.setLocatedBikesCount(location.getLocatedBikesCount() - 1);
                            command.getCommand().add(5);
                        }
                        if (location.getLocatedBikesCount() == 0) {
                            truckInfo.setLoading(false);
                        }
                    }
                } else {
                    HotPlace hotPlace = hotPlaceSet.getLoadedHotPlace();
                    Location location = locations.get(hotPlace.locationId);
                    Point point = PointerService.convertIdToPoint(problem, location.getId());

                    if (!truck.getLocationId().equals(location.getId())) {
                        TruckMoveDTO truckMoveDTO = TruckService.moveTargetPoint(truckPoint.row, truckPoint.col, point.row, point.col);
                        Integer nxtLocationId = PointerService.convertPointToId(problem, truckMoveDTO.getPoint());
                        truck.setLocationId(nxtLocationId);
                        truckInfo.setDirection(truckMoveDTO.getDirection());
                        command.getCommand().add(truckMoveDTO.getDirection());
                    } else {
                        while (canMoveCheck(command) && truck.getLoadedBikesCount() > 0) {
                            truck.setLoadedBikesCount(truck.getLoadedBikesCount() - 1);
                            location.setLocatedBikesCount(location.getLocatedBikesCount() + 1);
                            command.getCommand().add(6);
                        }
                        if (truck.getLoadedBikesCount() == 0) {
                            truckInfo.setLoading(true);
                        }
                    }
                }
            }
        }
    }
    private static boolean canMoveCheck(Command command) {
        return command.getCommand().size() < 10;
    }
}
