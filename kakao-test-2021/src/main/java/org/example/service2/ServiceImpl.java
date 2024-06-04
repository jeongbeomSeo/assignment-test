package org.example.service2;

import org.example.api.dto.request.CommandsRequest;
import org.example.model.Commands;
import org.example.model.HotPlaceInfo;
import org.example.model.Location;
import org.example.model.Truck;
import org.example.util.UtilHotPlace;
import org.example.util.UtilLocation;
import org.example.util.UtilRegist;
import org.example.util.UtilTruck;

import java.util.List;

public class ServiceImpl implements Service {
    @Override
    public CommandsRequest simulate(List<Truck> truckList, List<Location> locationList, HotPlaceInfo hotPlaceInfo, int[] times, List<Commands> commandsList) {

        if (commandsList == null) {
            commandsList = initCommandRequest(truckList);
        }

        int hotPlaceTruckCount = 0;
        if (hotPlaceInfo != null) {
            int dist = getDistance(hotPlaceInfo.getRentalLocationId(), hotPlaceInfo.getReturnLocationId());

            if (dist > 1000) {
                hotPlaceTruckCount = 2;
                for (int i = 0; i < 2; i++) {
                    Truck truck = truckList.get(i);

                    if (truck.getLoadedBikesCount() >= 2) {
                        Location location = locationList.get(hotPlaceInfo.getRentalLocationId());

                        UtilTruck.moveTargetPoint(location.getId(), times, truck, commandsList);

                        UtilTruck.unloadBike(location, times, truck, commandsList);
                    } else {
                        Location location = locationList.get(hotPlaceInfo.getReturnLocationId());

                        UtilTruck.moveTargetPoint(location.getId(), times, truck, commandsList);

                        UtilTruck.loadedBike(location, times, truck, commandsList);
                    }
                }
            } else {
                hotPlaceTruckCount = 1;
                Truck truck = truckList.get(0);
                if (truck.getLoadedBikesCount() >= 2) {
                    Location location = locationList.get(hotPlaceInfo.getRentalLocationId());

                    UtilTruck.moveTargetPoint(location.getId(), times, truck, commandsList);

                    UtilTruck.unloadBike(location, times, truck, commandsList);
                } else {
                    Location location = locationList.get(hotPlaceInfo.getReturnLocationId());

                    UtilTruck.moveTargetPoint(location.getId(), times, truck, commandsList);

                    UtilTruck.loadedBike(location, times, truck, commandsList);
                }
            }
        }

        for (int i = 0; i < 3; i++) {
            while(true) {
                Location leastLocation = UtilLocation.leastLocationRange(
                        locationList, 3600 / 3 * i + hotPlaceTruckCount, 3600 / 3 * (i + 1));

                // 최소 바이크 수가 1개 이상이면 종료
                if (leastLocation.getLocatedBikesCount() >= 1) {
                    break;
                }

                /**
                 * 가용할 트럭 선정
                 * 바이크가 많은 지역 -> 바이크가 적은 지역 이동이므로 많은 지역과 가까운 트럭으로 선정
                 */
                Truck truck = UtilTruck.findNearestTruckRange(truckList, leastLocation.getId(), times,
                        truckList.size() / 3 * i, truckList.size() / 3 * (i + 1));
                if (truck == null) {
                    break;
                }

                /**
                 * 트럭의 타임이 여유가 있든 없든 이동할 수 있는 만큼이라도 이동하기
                 */

                // 근처 가장 많은 수를 보유한 위치로 이동
                Location targetLocation = UtilLocation.mostLocationByNearestLocation(locationList, leastLocation.getId());

                if (targetLocation == null) break;

                UtilTruck.moveTargetPoint(targetLocation.getId(), times, truck, commandsList);

                UtilTruck.loadedBike(targetLocation, times, truck, commandsList);

                UtilTruck.moveTargetPoint(leastLocation.getId(), times, truck, commandsList);

                UtilTruck.unloadBike(leastLocation, times, truck, commandsList);
            }
        }

        UtilRegist.updateRegist(locationList);

        return new CommandsRequest(commandsList);
    }

    @Override
    public CommandsRequest simuateHotPlace(List<Truck> truckList, List<Location> locationList, List<HotPlaceInfo> hotPlaceInfoList, int[] times) {

        List<Location> lackBikeHotPlacesLocations = UtilHotPlace.getLackBikeHotPlaceLocations(locationList);

        List<Commands> commandsList = initCommandRequest(truckList);

        if (lackBikeHotPlacesLocations.isEmpty()) {
            return new CommandsRequest(commandsList);
        }

        for (int i = 0; i < lackBikeHotPlacesLocations.size(); i++) {
            Location location = lackBikeHotPlacesLocations.get(i);
            Truck truck = UtilTruck.findNearestTruck(truckList, location.getId(), times);

            if (truck.getLoadedBikesCount() == 0) {
                Location loadedLocation = UtilLocation.mostLocationByNearestLocation(locationList, location.getId());

                if (loadedLocation == null) {
                    // 다른 로직 생각
                    // 일단 패스
                }

                UtilTruck.moveTargetPoint(loadedLocation.getId(), times, truck, commandsList);

                UtilTruck.loadedBike(loadedLocation, times, truck, commandsList);
            }

            UtilTruck.moveTargetPoint(location.getId(), times, truck, commandsList);

            UtilTruck.unloadBike(location, times, truck, commandsList);
        }

        return new CommandsRequest(commandsList);
    }
    private static int getDistance(int id1, int id2) {
        return Math.abs(id1 / 60 - id2 / 60) + Math.abs(id1 % 60 - id2 % 60);
    }
}
