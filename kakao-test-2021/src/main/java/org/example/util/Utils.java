package org.example.util;

import lombok.extern.slf4j.Slf4j;
import org.example.api.dto.request.CommandsRequest;
import org.example.api.dto.response.LocationResponse;
import org.example.model.Commands;
import org.example.model.Location;
import org.example.model.Truck;

import java.util.*;
/*
0: 6초간 아무것도 하지 않음
1: 위로 한 칸 이동
2: 오른쪽으로 한 칸 이동
3: 아래로 한 칸 이동
4: 왼쪽으로 한 칸 이동
5: 자전거 상차
6: 자전거 하차
*/

@Slf4j
public class Utils {
    private static final int INF = Integer.MAX_VALUE;
    private static final int[][] pos = new int[][] {
            {6, 8, 12, 16, 18},
            {15 + (60 * 15), 15 + (60 * 30), 20 + (60 * 45),
             30 + (60 * 12), 30 + (60 * 24), 30 + (60 * 36), 30 + (60 * 48),
             45 + (60 * 15), 45 + (60 * 30), 45 + (60 * 45)}
    };

    public CommandsRequest query(int problem, List<Location> locations, List<Truck> trucks) {

        // 필요한 지역 변수 초기화
        List<Commands> commands = new ArrayList<>();
        for (int i = 0; i < trucks.size(); i++) {
            commands.add(new Commands(trucks.get(i).getId(), new ArrayList<>()));
        }
        int[] times = new int[trucks.size()];
        Arrays.fill(times, 60);

        // 1. Location 탐색 -> 바이크 적은 위치 체크
        List<Location> locationsHavingLackBike = new ArrayList<>();
        for (int i = 0; i < locations.size(); i++) {
            if (locations.get(i).getLocatedBikesCount() <= 1) {
                locationsHavingLackBike.add(locations.get(i));
            }
        }

        // 만약 바이크가 모두 충분하다면,
        if (locationsHavingLackBike.isEmpty()) {
            List<Location> locationsHavingManyBike = getLocationsHavingManyBike(problem, locations);

            // 만약 모든 위치의 바이크의 수도 적절하다면,
            if (locationsHavingManyBike.isEmpty()) {
                // 분산 시키기
            }

            while (!locationsHavingManyBike.isEmpty()) {
                TruckAndLocation truckAndLocation = getThisTurnTruckAndLocation(problem, locationsHavingManyBike, trucks, times);
                // 만약 모든 times가 충분하지 않다면,
                if (truckAndLocation.getLocation() == null) {
                    break;
                } else {
                    BfsItem bfsItem = bfs(problem, truckAndLocation.getTruck(), truckAndLocation.getLocation(), locations);
                    if (bfsItem != null) {
                        times[truckAndLocation.getTruck().getLocationId()] -= bfsItem.commandList.size() * 6;
                        update(problem, commands, locations, truckAndLocation.getTruck(), bfsItem.commandList);
                        log.info("Func Update Validation Check curPos == targetPos ? " + (truckAndLocation.getTruck().getLocationId() == truckAndLocation.getLocation().getId()));
                        unload(truckAndLocation.getLocation(), truckAndLocation.getTruck());
                    }
                    locationsHavingManyBike.remove(truckAndLocation.getLocation());
                }
            }
        }
    }
    private void diffusion(int problem, List<Truck> trucks, List<Commands> commands) {

        boolean[] moved = new boolean[trucks.size()];
        boolean[] posUsed = new boolean[pos[problem].length];

        while (allMovedCheck(moved)) {
            int minDist = INF;

            int locaiontId = -1;
            Truck truck = null;
            for (int i = 0; i < trucks.size(); i++) {
                if (!moved[i]) {
                    for (int j = 0; j < pos[problem].length; j++) {
                        if (posUsed[j]) continue;
                        int dist = getDist(problem, trucks.get(i).getLocationId(), pos[problem][j]);
                        if (minDist > dist) {
                            minDist = dist;
                            truck = trucks.get(i);
                            locaiontId = pos[problem][j];
                        }
                    }
                }
            }

        }
    }
    private PlaneMoveResult planeMove(int problem, Truck truck, int targetLocationId, int remainTime) {

        List<Integer> command = new ArrayList<>();

        int size = problem == 1 ? 5 : 60;
        int curPos = truck.getLocationId();
        while (remainTime >= 0) {
            if (curPos < targetLocationId) {
                if (curPos + size <= targetLocationId) {
                    curPos += size;
                    command.add(2);
                } else {
                    curPos += 1;

                }
            } else {

            }
            remainTime -= 6;
        }

    }
    private boolean allMovedCheck(boolean[] moved) {
        for (int i = 0; i < moved.length; i++) {
            if (!moved[i]) {
                return false;
            }
        }
        return true;
    }
    private void unload(Location location, Truck truck) {
        if (location.getLocatedBikesCount() == 0) {
            if (truck.getLoadedBikesCount() >= 2) {
                location.setLocatedBikesCount(2);
                truck.setLoadedBikesCount(truck.getLoadedBikesCount() - 2);
            } else {
                location.setLocatedBikesCount(truck.getLoadedBikesCount());
                truck.setLoadedBikesCount(0);
            }
        } else {
            if (truck.getLoadedBikesCount() < 1) {
                log.warn("Logic Error, Func unload 에서 truck의 보유 자전거 수 부족");
            }
            location.setLocatedBikesCount(location.getLocatedBikesCount() + 1);
            truck.setLoadedBikesCount(truck.getLoadedBikesCount() - 1);
        }
    }
    private void update(int problem, List<Commands> commands, List<Location> locations, Truck truck, List<Integer> commandList) {

        for (int cmd : commandList) {
            if (cmd == 5) {
                int curPosBikeCount = locations.get(truck.getLocationId()).getLocatedBikesCount();
                locations.get(truck.getLocationId()).setLocatedBikesCount(curPosBikeCount - 1);
                truck.setLoadedBikesCount(truck.getLoadedBikesCount() + 1);
            } else {
                int nxtIdx = getNextIdx(problem, truck.getId(), cmd);
                truck.setLocationId(nxtIdx);
            }
            commands.get(truck.getId()).getCommand().add(cmd);
        }
    }

    private TruckAndLocation getThisTurnTruckAndLocation(int problem, List<Location> locations, List<Truck> trucks, int[] times) {

        int[] distTable = new int[locations.size()];
        int[] truckIdx = new int[trucks.size()];
        Arrays.fill(distTable, INF);
        for (int i = 0; i < locations.size(); i++) {
            for (int j = 0; j < trucks.size(); j++) {
                int dist = getDist(problem, locations.get(i).getId(), trucks.get(j).getLocationId());
                int requireTime = dist * 6 + (trucks.get(j).getLoadedBikesCount() == 0 ? 6 : 0);
                if (requireTime > times[j]) continue;

                if (distTable[i] > dist) {
                    truckIdx[i] = j;
                    distTable[i] = dist;
                }
            }
        }

        Location resultLocation = null;
        Truck resultTruck = null;
        int minDist = INF;
        for (int i = 0; i < locations.size(); i++) {
            if (minDist > distTable[i]) {
                minDist = distTable[i];
                resultLocation = locations.get(i);
                resultTruck = trucks.get(truckIdx[i]);
            }
        }

        return new TruckAndLocation(resultLocation, resultTruck);
    }
    private List<Location> getLocationsHavingManyBike(int problem, List<Location> locations) {

        List<Location> locationsHavingManyBike = new ArrayList<>();

        // Problem 1: 4개 초과(초기값 초과량)
        // Problem 2: 3개 초과(초기값 초과량)
        int boundary = problem == 1 ? 4 : 3;
        for (int i = 0; i < locations.size(); i++) {
            if (locations.get(i).getLocatedBikesCount() >= boundary) {
                locationsHavingManyBike.add(locations.get(i));
            }
        }

        return locationsHavingManyBike;
    }

    private int getDist(int problem, int curPos, int targetPos) {
        int mod = problem == 1 ? 5 : 60;
        return Math.abs(curPos / mod - targetPos / mod) + Math.abs(curPos % mod - targetPos % mod);
    }

    /**
     * BFS
     * 필요한 return 요소: 최적의 경로(해당 위치에 하역이 가능한) -> 추후 역추적하면서 업데이트 필요
     * 필요한 매개 변수: 트럭의 위치, Target Location 위치, 트럭이 가지고 있는 바이크 수
     */
    private BfsItem bfs(int problem, Truck truck, Location location, List<Location> locations) {

        Queue<BfsItem> q = new ArrayDeque<>();
        int size = problem == 1 ? 5 * 5 : 60 * 60;
        boolean[] initIsVisited = new boolean[size + 1];
        initIsVisited[truck.getLocationId()] = true;
        q.add(new BfsItem(initIsVisited, truck.getLocationId(), truck.getLoadedBikesCount(), new ArrayList<>()));

        BfsItem result = null;
        while (!q.isEmpty()) {
            BfsItem curItem = q.poll();

            if (curItem.commandList.size() * 6 >= 60) continue;

            if (curItem.curPos == location.getId() && curItem.loadedBikeCount > 0) {
                result = curItem;
                break;
            }

            for (int i = 1; i <= 4; i++) {
                int nextIdx = getNextIdx(problem, curItem.curPos, i);

                if (nextIdx == -1) continue;

                if (!curItem.isVisited[nextIdx]) {
                    boolean[] nextIsVisited = Arrays.copyOf(curItem.isVisited, curItem.isVisited.length);
                    nextIsVisited[nextIdx] = true;
                    if (locations.get(nextIdx).getLocatedBikesCount() > (problem == 1 ? 4 : 3)) {
                        int nextLoadedBikeCount = curItem.loadedBikeCount + 1;
                        List<Integer> nextCommandList = new ArrayList<>(curItem.commandList);
                        nextCommandList.add(5);
                        nextCommandList.add(i);

                        q.add(new BfsItem(nextIsVisited, nextIdx, nextLoadedBikeCount, nextCommandList));
                    }

                    q.add(new BfsItem(nextIsVisited, nextIdx, curItem.loadedBikeCount, new ArrayList<>(curItem.commandList)));
                }
            }
        }

        return result;
    }
    private int getNextIdx(int problem, int idx, int direction) {

        int size = problem == 1 ? 5 : 60;

        if (idx % size == 0 && direction == 3) return -1;
        if (idx < size && direction == 4) return -1;
        if (size * (size - 1) <= idx && direction == 2) return -1;
        if (idx % size == (size - 1) && direction == 1) return -1;

        if (direction == 1) return idx + 1;
        else if (direction == 2) return idx + size;
        else if (direction == 3) return idx - 1;
        else return idx - size;
    }
}