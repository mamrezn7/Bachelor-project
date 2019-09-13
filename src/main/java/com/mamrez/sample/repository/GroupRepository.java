package com.mamrez.sample.repository;

import com.mamrez.sample.domain.Group;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Group entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query("select group from Group group where group.user.login = ?#{principal.username}")
    List<Group> findByUserIsCurrentUser();

}
