package com.xproject.repository;

import com.xproject.domain.UserX;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UserX entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserXRepository extends JpaRepository<UserX, Long> {

}
