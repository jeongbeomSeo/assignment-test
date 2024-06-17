package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.api.dto.request.SimulateRequestDTO;
import org.example.dto.MoveResultDTO;
import org.example.model.Command;
import org.example.model.Location;
import org.example.model.Truck;
import org.example.service.LocationService;
import org.example.service.TruckService;

import java.util.List;

@Slf4j
public class ServiceSimulate_1 implements Service {
    @Override
    public SimulateRequestDTO simulate(Integer problem, List<Location> locations, List<Truck> trucks) {

        Integer size = problem == 1 ? 5 : 10;
        List<Command> commands = initCommands(size);

        boolean[] isVisited = new boolean[locations.size()];

        while (trucks.size() > 0) {
            Location lowestLocation = LocationService.getLocationHavingLowestBike(locations);
            Location mostLocation = LocationService.getLocationHavingMostBike(locations);

            if (lowestLocation == null || mostLocation == null) {
                log.info("Location Service의 getLocation 로직 에러");
                log.info("Return lowestLocation: {}", lowestLocation);
                log.info("Return mostLocation: {}", mostLocation);
                break;
            }

            // 충분할 경우
            if (lowestLocation.getLocatedBikesCount() >= 2
                    || mostLocation.getLocatedBikesCount() - lowestLocation.getLocatedBikesCount() <= 1) break;

            // 방문했던 경우
            if (isVisited[lowestLocation.getId()] || isVisited[mostLocation.getId()]) break;

            // 먼저 가지고 있는 바이크 수로 적은 수 위치의 바이크 수를 2개 이상으로 설정할 수 있는 경우
            Truck activeTruck = TruckService.getCanMoveTruckWhenHavingEnoughBike(problem, trucks, lowestLocation, commands);

            if (activeTruck != null) {
                Command command = commands.get(activeTruck.getId());

                while (true) {
                    MoveResultDTO moveResultDTO = TruckService.move(problem, activeTruck, lowestLocation.getId(), command);

                    activeTruck.setLocationId(moveResultDTO.getNxtLocationId());
                    command.getCommand().add(moveResultDTO.getCommand());

                    if (activeTruck.getLocationId().equals(lowestLocation.getId()) || command.getCommand().size() == 10) break;
                }

                while (command.getCommand().size() < 10
                        && activeTruck.getLoadedBikesCount() > 0
                        && lowestLocation.getLocatedBikesCount() < 2) {

                    activeTruck.setLoadedBikesCount(activeTruck.getLoadedBikesCount() - 1);
                    lowestLocation.setLocatedBikesCount(lowestLocation.getLocatedBikesCount() + 1);
                    command.getCommand().add(6);
                }

                if (command.getCommand().size() >= 9) {
                    trucks.remove(activeTruck);
                }
                isVisited[lowestLocation.getId()] = true;
                continue;
            }

            // 현재는 음수이더라도 이동하도록 설정
            Truck truck = TruckService.getTruckWhenHavingManyTimesAfterMoving(problem, trucks, mostLocation.getId(), commands);

            if (commands.get(truck.getId()).getCommand().size() == 10) {
                break;
            }

            if (truck == null) {
                log.info("Truck Service의 getTruck 로직 에러");
                break;
            }

            Command command = commands.get(truck.getId());

            // 바이크 수가 많은 Location으로 이동
            while (command.getCommand().size() < 10) {
                MoveResultDTO moveResultDTO = TruckService.move(problem, truck, mostLocation.getId(), command);

                command.getCommand().add(moveResultDTO.getCommand());
                truck.setLocationId(moveResultDTO.getNxtLocationId());

                if (truck.getLocationId().equals(mostLocation.getId())) break;
            }

            // 적재
            while (command.getCommand().size() < 10
                    && mostLocation.getLocatedBikesCount() > 2) {
                mostLocation.setLocatedBikesCount(mostLocation.getLocatedBikesCount() - 1);
                truck.setLoadedBikesCount(truck.getLoadedBikesCount() + 1);
                command.getCommand().add(5);
                isVisited[mostLocation.getId()] = true;
            }

            if (mostLocation.getLocatedBikesCount() <= 2) {
                isVisited[mostLocation.getId()] = true;
            }

            // 이동
            while (command.getCommand().size() < 10) {
                MoveResultDTO moveResultDTO = TruckService.move(problem, truck, lowestLocation.getId(), command);

                command.getCommand().add(moveResultDTO.getCommand());
                truck.setLocationId(moveResultDTO.getNxtLocationId());

                if (truck.getLocationId().equals(lowestLocation.getId())) break;
            }

            // 하역
            while (command.getCommand().size() < 10
                        && truck.getLoadedBikesCount() > 0
                        && lowestLocation.getLocatedBikesCount() < 2) {
                truck.setLoadedBikesCount(truck.getLoadedBikesCount() - 1);
                lowestLocation.setLocatedBikesCount(lowestLocation.getLocatedBikesCount() + 1);
                command.getCommand().add(6);
            }

            if (command.getCommand().size() >= 9) {
                trucks.remove(truck);
            }

            if (lowestLocation.getLocatedBikesCount() >= 2) {
                isVisited[lowestLocation.getId()] = true;
            }
        }

        return new SimulateRequestDTO(commands);
    }
}
