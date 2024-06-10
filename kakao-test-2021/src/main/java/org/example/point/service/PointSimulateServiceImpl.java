package org.example.point.service;

import org.example.point.dto.TruckMoveDTO;
import org.example.point.model.Command;
import org.example.point.model.Location;
import org.example.point.model.Point;
import org.example.point.model.Truck;
import org.example.point.register.TruckInfo;
import org.example.point.register.TruckInfoRegister;

import java.util.Arrays;
import java.util.List;

public class PointSimulateServiceImpl implements SimulateService{
    @Override
    public void simulate(Integer problem, List<Location> locations, List<Truck> trucks, List<Command> commands) {

        for (int i = 0; i < trucks.size(); i++) {
            Truck truck = trucks.get(i);

            TruckInfo truckInfo = TruckInfoRegister.getTruckInfos().get(truck.getId());
            Command command = commands.get(truck.getId());

            while (canMoveCheck(command)) {
                if (!checkLowerBikeBoundary(problem, locations, truckInfo.getUpperRow(), truckInfo.getLowerRow(), truckInfo.getLeftCol(), truckInfo.getRightCol())) {
                    // 정해진 범위의 모든 구역에 충분한 자전거 수가 대기 중이면 중단
                    break;
                }

                Location location = locations.get(truck.getLocationId());
                // 지정된 위치로 복귀 (lowerRow, leftCol)
                if (!truckInfo.isTracking()) {
                    Point point = PointerService.convertIdToPoint(problem, truck.getLocationId());
                    if (point.row != truckInfo.getLowerRow() || point.col != truckInfo.getLeftCol()) {
                        // 지정된 위치로 아직 이동하지 못했을 경우
                        TruckMoveDTO truckMoveDTO = TruckService.moveTargetPoint(point.row, point.col, truckInfo.getLowerRow(), truckInfo.getLeftCol());
                        Integer locationId = PointerService.convertPointToId(problem, truckMoveDTO.getPoint());
                        truck.setLocationId(locationId);
                        truckInfo.setDirection(truckMoveDTO.getDirection());
                        command.getCommand().add(truckMoveDTO.getDirection());
                    } else {
                        // 지정된 위치로 복귀한 경우
                        // isVisited 배열 초기화
                        boolean[][] isVisted = truckInfo.getIsVisited();
                        for (int row = 0; row < isVisted.length; row++) {
                            Arrays.fill(isVisted[row], false);
                        }
                        isVisted[truckInfo.getLowerRow()][truckInfo.getLeftCol()] = true;
                        truckInfo.setTracking(true);
                        truckInfo.setDirection(1);
                    }
                } else {
                    boolean isLoading = false;
                    // HotPlace 장소인 경우 적재 하역 X && 해당 위치의 bike 갯수 확인
                    if (!HotPlaceService.isHotPlace(location.getId()) &&
                            checkBikeCount(problem, location.getLocatedBikesCount())) {
                        // bike 수가 부족한 경우 || bike 수가 적은 경우
                        if (checkBelowBikeCount(problem, location.getLocatedBikesCount())) {
                            // bike 수가 부족한 경우 && 트럭에 여유분이 있는 경우
                            if (truck.getLoadedBikesCount() > 0) {
                                location.setLocatedBikesCount(location.getLocatedBikesCount() + 1);
                                command.getCommand().add(6);
                                truck.setLoadedBikesCount(truck.getLoadedBikesCount() - 1);
                                isLoading = true;
                            }
                            // bike 수가 부족하지만 트럭에 여우분이 없다면 isLoading을 flase로 설정하여 트럭 이동
                        } else {
                            // bike 수가 많은 경우
                            location.setLocatedBikesCount(location.getLocatedBikesCount() - 1);
                            command.getCommand().add(5);
                            truck.setLoadedBikesCount(truck.getLoadedBikesCount() + 1);
                            isLoading = true;
                        }
                    }

                    if (!isLoading) {
                        // 트럭이 움직여야 하는 경우
                        boolean[][] isVisted = truckInfo.getIsVisited();
                        Point point = PointerService.convertIdToPoint(problem, truck.getLocationId());
                        TruckMoveDTO truckMoveDTO = TruckService.move(isVisted, point.row, point.col,
                                truckInfo.getDirection(), truckInfo.getUpperRow(), truckInfo.getLowerRow(), truckInfo.getLeftCol(), truckInfo.getRightCol());

                        Integer nxtLocationId = PointerService.convertPointToId(problem, truckMoveDTO.getPoint());
                        truck.setLocationId(nxtLocationId);
                        truckInfo.setDirection(truckMoveDTO.getDirection());
                        isVisted[truckMoveDTO.getPoint().row][truckMoveDTO.getPoint().col] = true;
                        if (isAllVisited(isVisted, truckInfo.getUpperRow(), truckInfo.getLowerRow(), truckInfo.getLeftCol(), truckInfo.getRightCol())) {
                            truckInfo.setTracking(false);
                        }

                        command.getCommand().add(truckMoveDTO.getDirection());
                    }
                }

            }
        }
    }
    private boolean checkBelowBikeCount(Integer problem, Integer locatedBike) {
        if (problem == 1) return locatedBike < 2;
        else return locatedBike < 2;
    }
    private boolean checkBikeCount(Integer problem, Integer locatedBike) {
        if (problem == 1) return locatedBike < 2 || locatedBike > 3;
        else return locatedBike < 2 || locatedBike > 3;
    }
    private boolean isAllVisited(boolean[][] isVisted, int upperRow, int lowerRow, int leftCol, int rightCol) {

        for (int i = lowerRow; i <= upperRow; i++) {
            for (int j = leftCol; j <= rightCol; j++) {
                if (!isVisted[i][j]) return false;
            }
        }

        return true;
    }

    private boolean checkLowerBikeBoundary(Integer problem, List<Location> locations, int upperRow, int lowerRow, int leftCol, int rightCol) {

        for (int i = lowerRow; i <= upperRow; i++) {
            for (int j = leftCol; j <= rightCol; j++) {
                Integer locationId = PointerService.convertPointToId(problem, i, j);
                if (problem == 1) {
                    if (locations.get(locationId).getLocatedBikesCount() < 2) return true;
                } else {
                    if (locations.get(locationId).getLocatedBikesCount() < 1) return true;
                }
            }
        }
        return false;
    }

    private boolean canMoveCheck(Command command) {
        return command.getCommand().size() < 10;
    }
}
