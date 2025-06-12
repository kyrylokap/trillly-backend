package org.example.trilly.repositories;

import org.example.trilly.models.Relation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.example.trilly.models.enums.RelationStatus;
import java.util.List;

public interface RelationRepository extends JpaRepository<Relation, Long> {

    @Query("""
        SELECT r FROM Relation r where 
        r.firstUser.username = :username 
        AND r.relationStatus IN :relations
    """)

    /**
     * Finds relations for the given username, depending on their role in the relation.
     *
     * @param username the username of the user
     * @param relFirstUser statuses where the user is the firstUser (e.g., follower)
     * @param relSecondUser statuses where the user is the secondUser (e.g., followed)
     * @return list of matching Relation entities
     *
     * Example:
     * - To get followers: pass [FOLLOWED, FRIEND] as relFirstUser and [FRIEND, FOLLOWING] as relSecondUser
     * - To get followings: reverse the above
     */
    List<Relation> findRelationsByUsernameAndStatuses(
            @Param("username") String username,
            @Param("relations") List<RelationStatus> relations
    );

    boolean existsByFirstUserUsernameAndSecondUserUsername(String first, String second);

    Relation findByFirstUserUsernameAndSecondUserUsername(String first, String second);

    @Query("""
    SELECT COUNT(r) > 0 FROM Relation r 
    WHERE r.firstUser.username = :username 
      AND r.secondUser.username = :another 
      AND r.relationStatus IN :relations
    """)
    boolean isFollowedOn(@Param("username") String username,
                         @Param("another") String another,
                         @Param("relations") List<RelationStatus> relations);

}
