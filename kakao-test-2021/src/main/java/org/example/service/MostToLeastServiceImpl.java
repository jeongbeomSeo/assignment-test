package org.example.service;

import org.example.api.dto.request.CommandsRequest;
import org.example.model.Commands;
import org.example.model.Location;
import org.example.model.Truck;
import org.example.util.UtilLocation;
import org.example.util.UtilTruck;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MostToLeastServiceImpl implements Service {
    @Override
    public CommandsRequest simulate(List<Truck> truckList, List<Location> locationList) {

        List<Commands> commandsList = new ArrayList<>();
        for (int i = 0; i < truckList.size(); i++) {
            commandsList.add(new Commands(truckList.get(i).getId(), new ArrayList<>()));
        }

        int[] remainTimes = new int[truckList.size()];
        Arrays.fill(remainTimes, 60);

        while (true) {
            Location leastLocation = UtilLocation.leastLocation(locationList);
            Location mostLocation = UtilLocation.mostLocation(locationList);

            // 최소 바이크 수가 2개 이상이거나 최소 바이크 수와 최대 바이크 수가 같거나 1개 더 많다면 움직일 필요가 없다.
            if (leastLocation.getLocatedBikesCount() >= 2 || leastLocation.getLocatedBikesCount() + 1 >= mostLocation.getLocatedBikesCount()) {
                break;
            }

            /**
             * 가용할 트럭 선정
             * 바이크가 많은 지역 -> 바이크가 적은 지역 이동이므로 많은 지역과 가까운 트럭으로 선정
             */
            Truck truck = UtilTruck.findNearestTruck(truckList, mostLocation.getId(), remainTimes);
            if (truck == null) {
                break;
            }

            /**
             * 트럭의 타임이 여유가 있든 없든 이동할 수 있는 만큼이라도 이동하기
             */
            int targetPos = mostLocation.getId();

            truckMove(targetPos, remainTimes, truck, commandsList);

            int initBikeCount = mostLocation.getLocatedBikesCount();
            while (remainTimes[truck.getId()] != 0) {
                if (truck.getLoadedBikesCount() >= 5 || mostLocation.getLocatedBikesCount() - 1 < initBikeCount / 2) break;

                truck.setLoadedBikesCount(truck.getLoadedBikesCount() + 1);
                mostLocation.setLocatedBikesCount(mostLocation.getLocatedBikesCount() - 1);
                commandsList.get(truck.getId()).getCommand().add(5);

                remainTimes[truck.getId()] -= 6;
            }

            targetPos = leastLocation.getId();
            truckMove(targetPos, remainTimes, truck, commandsList);

            while (remainTimes[truck.getId()] != 0) {
                if (truck.getLoadedBikesCount() == 0 || leastLocation.getLocatedBikesCount() >= 3) break;

                truck.setLoadedBikesCount(truck.getLoadedBikesCount() - 1);
                leastLocation.setLocatedBikesCount(leastLocation.getLocatedBikesCount() + 1);
                commandsList.get(truck.getId()).getCommand().add(6);

                remainTimes[truck.getId()] -= 6;
            }
        }

        return new CommandsRequest(commandsList);
    }
    private void truckMove(int targetPos, int[] remainTimes, Truck truck, List<Commands> commandsList) {
        while (remainTimes[truck.getId()] != 0) {
            int curPos = truck.getLocationId();
            /**
             * ele1: 방향(direction)
             * ele2: 도착한 위치의 location ID
             */
            int[] moveResult = UtilTruck.moveTruck(curPos, targetPos);
            commandsList.get(truck.getId()).getCommand().add(moveResult[0]);
            truck.setLocationId(moveResult[1]);

            remainTimes[truck.getId()] -= 6;

            // 목표 지점에 도착을 하였다면 멈추기
            if (moveResult[1] == targetPos) break;
        }
    }
}
