package org.example.service;

import org.example.api.dto.request.CommandsRequest;
import org.example.model.Commands;
import org.example.model.Location;
import org.example.model.Truck;
import org.example.util.UtilLocation;
import org.example.util.UtilTruck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NearByOneServiceImpl implements Service {
    @Override
    public CommandsRequest simulate(List<Truck> truckList, List<Location> locationList) {

        List<Commands> commandsList = new ArrayList<>();
        for (int i = 0; i < truckList.size(); i++) {
            commandsList.add(new Commands(truckList.get(i).getId(), new ArrayList<>()));
        }

        int[] remainTimes = new int[truckList.size()];
        Arrays.fill(remainTimes, 60);

        boolean[] isVisited = new boolean[locationList.size()];
        while(true) {
            Location leastLocation = UtilLocation.leastLocation(locationList);

            // 최소 바이크 수가 2개 이상이거나 이전 방문 지역이 나올 경우 로직을 종료한다.
            if (leastLocation.getLocatedBikesCount() >= 2 || isVisited[leastLocation.getId()]) {
                break;
            }

            /**
             * 가용할 트럭 선정
             * 바이크가 많은 지역 -> 바이크가 적은 지역 이동이므로 많은 지역과 가까운 트럭으로 선정
             */
            Truck truck = UtilTruck.findNearestTruck(truckList, leastLocation.getId(), remainTimes);
            if (truck == null) {
                break;
            }

            /**
             * 트럭의 타임이 여유가 있든 없든 이동할 수 있는 만큼이라도 이동하기
             */

            // 근처 가장 많은 수를 보유한 위치로 이동
            Location targetLocation = UtilLocation.mostLocationByNearestLocation(locationList, leastLocation.getId());

            if (targetLocation == null) break;

            UtilTruck.moveTargetPoint(targetLocation.getId(), remainTimes, truck, commandsList);

            UtilTruck.loadedBike(targetLocation, remainTimes, truck, commandsList);

            UtilTruck.moveTargetPoint(leastLocation.getId(), remainTimes, truck, commandsList);

            UtilTruck.unloadBike(leastLocation, remainTimes, truck, commandsList);

            isVisited[leastLocation.getId()] = true;
        }
        return new CommandsRequest(commandsList);
    }
}
