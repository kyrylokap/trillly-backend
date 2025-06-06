package org.example.trilly.services;

import lombok.AllArgsConstructor;
import org.example.trilly.dto.relation.RelationRequestDTO;
import org.example.trilly.dto.relation.RelationResponseDTO;
import org.example.trilly.models.Relation;
import org.example.trilly.models.enums.RelationStatus;
import org.example.trilly.repositories.RelationRepository;
import org.example.trilly.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class RelationService {
    private final RelationRepository relationRepository;
    private final UserRepository userRepository;

    //TODO
    // 2.block user
    // 3.accept to followed

    public void follow(RelationRequestDTO r) {
        Relation relation = relationRepository.findByFirstUserUsernameAndSecondUserUsername(r.getFirstUsername(),r.getSecondUsername());
        if(relation != null && relation.getRelationStatus() == RelationStatus.FOLLOWED_BY){
            saveRelation(r.getFirstUsername(), r.getSecondUsername(), RelationStatus.FRIENDS, RelationStatus.FRIENDS);
            return;
        }
        saveRelation(r.getFirstUsername(), r.getSecondUsername(), RelationStatus.FOLLOWING, RelationStatus.FOLLOWED_BY);
    }

    public void unFollow(RelationRequestDTO r){
        Relation relation = relationRepository.findByFirstUserUsernameAndSecondUserUsername(r.getFirstUsername(),r.getSecondUsername());

        RelationStatus relationStatus = null ;
        if(relation.getRelationStatus() == RelationStatus.FRIENDS){
            relationStatus = RelationStatus.FOLLOWING;
        }else if(relation.getRelationStatus() == RelationStatus.FOLLOWING){
            relationStatus = RelationStatus.NONE;
        }
        saveRelation(r.getFirstUsername(), r.getSecondUsername(), RelationStatus.NONE, relationStatus);
    }

    private void saveRelation(String first, String second, RelationStatus relS1, RelationStatus relS2){
        var user1 = userRepository.findByUsername(first);
        var user2 = userRepository.findByUsername(second);

        var rel1 = relationRepository.findByFirstUserUsernameAndSecondUserUsername(first, second);
        if(rel1 == null){
            rel1 = Relation.builder().firstUser(user1)
                    .secondUser(user2).createdAt(LocalDateTime.now())
                    .build();
        }
        rel1.setRelationStatus(relS1);
        relationRepository.save(rel1);

        var rel2 = relationRepository.findByFirstUserUsernameAndSecondUserUsername(second, first);
        if(rel2 == null){
            rel2= Relation.builder().firstUser(user2)
                    .secondUser(user1).createdAt(LocalDateTime.now())
                    .build();
        }
        rel2.setRelationStatus(relS2);
        relationRepository.save(rel2);
    }

    public List<RelationResponseDTO> getUserFollowers(RelationRequestDTO request){
       return mapListToListDTO(
               relationRepository.findRelationsByUsernameAndStatuses(request.getFirstUsername(),
                getFirstTypeRelationStatus(),
                getSecondTypeRelationStatus()), request.getFirstUsername());
    }

    public List<RelationResponseDTO> getUserFollowings(RelationRequestDTO request){
        return mapListToListDTO(
                relationRepository.findRelationsByUsernameAndStatuses(request.getFirstUsername(),
                getSecondTypeRelationStatus(),
                getFirstTypeRelationStatus()), request.getFirstUsername()
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
