package com.mamrez.sample.repository;

import com.mamrez.sample.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Page entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PageRepository extends JpaRepository<Page, Long> {

    @Query("select page from Page page where page.user.login = ?#{principal.username}")
    List<Page> findByUserIsCurrentUser();

}
