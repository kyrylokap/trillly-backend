package org.example.trilly.services;

import lombok.AllArgsConstructor;
import org.example.trilly.dto.position.PositionDTO;
import org.example.trilly.models.Position;
import org.example.trilly.models.User;
import org.example.trilly.repositories.PositionRepository;
import org.example.trilly.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PositionService{
    private final PositionRepository positionRepository;
    private final UserRepository userRepository;

    public void addPositionToUser(String username, Double longitude, Double latitude){
        if(checkIfExistsLastFive(username, longitude, latitude)){
            var user = userRepository.findByUsername(username);
            var position = Position.builder()
                    .longitude(longitude).latitude(latitude)
                    .user(user).dateTime(LocalDateTime.now()).build();

            positionRepository.save(position);
        }
    }

    private boolean checkIfExistsLastFive(String username, Double longitude, Double latitude){
        return positionRepository.findTop5ByUserUsernameOrderByDateTimeDesc(username).stream().noneMatch(position ->
                Objects.equals(position.getLongitude(), longitude) &&
                        Objects.equals(position.getLatitude(), latitude)
        );
    }

    public List<PositionDTO> posesToday(String username){
        var user = userRepository.findByUsername(username);
        return positionRepository.getAllByDateTimeAfterAndUser(LocalDate.now().atStartOfDay(), user).stream().map(this::mapToDTO).toList();
    }
    public PositionDTO mapToDTO(Position pos){
        return PositionDTO.builder()
                .longitude(pos.getLongitude()).latitude(pos.getLatitude())
                .time(pos.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy")))
                .build();
    }
    public PositionDTO getLastUserPos(String username){
        var pos = positionRepository.findFirstByUserUsernameOrderByDateTimeDesc(username);
        return pos == null ? null :mapToDTO(pos);
    }
}
