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
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class RelationService {
    private final RelationRepository relationRepository;
    private final UserRepository userRepository;



    public void block(String username, String profileUsername){
        saveRelation(username, profileUsername, RelationStatus.BLOCKED, RelationStatus.NONE);
    }

    public void unblock(String username,  String profileUsername){
        saveRelation(username, profileUsername, RelationStatus.NONE, RelationStatus.NONE);
    }

    public void follow(RelationRequestDTO r) {
        Relation relation = relationRepository.findByFirstUserUsernameAndSecondUserUsername(r.getFirstUsername(),r.getSecondUsername());
        if(relation != null && relation.getRelationStatus() == RelationStatus.FOLLOWED){
            saveRelation(r.getFirstUsername(), r.getSecondUsername(), RelationStatus.FRIEND, RelationStatus.FRIEND);
            return;
        }
        saveRelation(r.getFirstUsername(), r.getSecondUsername(), RelationStatus.FOLLOWING, RelationStatus.FOLLOWED);
    }

    public void unfollow(RelationRequestDTO r){
        Relation relation = relationRepository.findByFirstUserUsernameAndSecondUserUsername(r.getFirstUsername(),r.getSecondUsername());

        RelationStatus relationStatus = null ;
        if(relation.getRelationStatus() == RelationStatus.FRIEND){
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

    public List<RelationResponseDTO> getUserFollowers(String username){
       return mapListToListDTO(
               relationRepository.findRelationsByUsernameAndStatuses(username,
                getFirstTypeRelationStatus()),username);
    }

    public List<RelationResponseDTO> getUserFollowings(String username){
        return mapListToListDTO(
                relationRepository.findRelationsByUsernameAndStatuses(username,
                getSecondTypeRelationStatus()),username
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
        return List.of(RelationStatus.FRIEND,RelationStatus.FOLLOWED);
    }

    private List<RelationStatus> getSecondTypeRelationStatus(){
        return List.of(RelationStatus.FRIEND,RelationStatus.FOLLOWING);
    }

    public boolean checkFollow(String firstUsername, String secondUsername){
        return relationRepository.isFollowedOn(firstUsername, secondUsername, List.of(RelationStatus.FOLLOWING, RelationStatus.FRIEND));
    }

    public boolean isBlocked( String username, String profileUsername){
        return relationRepository.isBlocked(username, profileUsername, RelationStatus.BLOCKED);
    }
}
