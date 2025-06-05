package org.example.trilly.services;

import lombok.AllArgsConstructor;
import org.example.trilly.dto.relation.RelationResponseDTO;
import org.example.trilly.models.Relation;
import org.example.trilly.models.enums.RelationStatus;
import org.example.trilly.repositories.RelationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RelationService {
    private final RelationRepository relationRepository;

    //TODO
    // 1.request to be follower
    // 2.block user
    // 3.accept to followed



    public List<RelationResponseDTO> getUserFollowers(String username){
       return mapListToListDTO(
               relationRepository.findRelationsByUsernameAndStatuses(username,
                getFirstTypeRelationStatus(),
                getSecondTypeRelationStatus()), username);
    }

    public List<RelationResponseDTO> getUserFollowings(String username){
        return mapListToListDTO(
                relationRepository.findRelationsByUsernameAndStatuses(username,
                getSecondTypeRelationStatus(),
                getFirstTypeRelationStatus()), username
        );
    }



    private List<RelationResponseDTO> mapListToListDTO(List<Relation> relations, String username){
         return relations.stream().map(relation -> mapToDTO(relation, username)).toList();
    }

    private RelationResponseDTO mapToDTO(Relation r, String username){
        return RelationResponseDTO.builder()
                .username(r.getFirstUser().getUsername().equals(username) ? r.getSecondUser().getUsername() : r.getFirstUser().getUsername())
                .status(r.getRelationStatus())
                .createdAt(r.getCreatedAt()).build();
    }


    private List<RelationStatus> getFirstTypeRelationStatus(){
        return List.of(RelationStatus.FRIENDS,RelationStatus.FOLLOWED_BY);
    }

    private List<RelationStatus> getSecondTypeRelationStatus(){
        return List.of(RelationStatus.FRIENDS,RelationStatus.FOLLOWING);
    }
}
