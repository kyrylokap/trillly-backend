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

@Service
@AllArgsConstructor
public class PositionService {
    private final PositionRepository positionRepository;
    private final UserRepository userRepository;

    public String addPositionToUser(String username, Double longitude, Double latitude){
        var user = userRepository.findByUsername(username);
        var position = Position.builder()
                .longitude(longitude).latitude(latitude)
                .user(user).dateTime(LocalDateTime.now()).build();
        positionRepository.save(position);
        return "Added ";
    }

    public List<PositionDTO> posesToday(String username){
        var user = userRepository.findByUsername(username);
        return positionRepository.getAllByDateTimeAfterAndUser(LocalDate.now().atStartOfDay(), user).stream().map(this::mapToDTO).toList();
    }
    public PositionDTO mapToDTO(Position pos){
        return PositionDTO.builder()
                .longitude(pos.getLongitude()).latitude(pos.getLatitude())
                .time(pos.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy")))
                .build();
    }
}
