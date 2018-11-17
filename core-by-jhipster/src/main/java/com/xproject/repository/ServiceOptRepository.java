package com.xproject.repository;

import com.xproject.domain.ServiceOpt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ServiceOpt entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceOptRepository extends JpaRepository<ServiceOpt, Long> {

    @Query(value = "select distinct service_opt from ServiceOpt service_opt left join fetch service_opt.opts",
        countQuery = "select count(distinct service_opt) from ServiceOpt service_opt")
    Page<ServiceOpt> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct service_opt from ServiceOpt service_opt left join fetch service_opt.opts")
    List<ServiceOpt> findAllWithEagerRelationships();

    @Query("select service_opt from ServiceOpt service_opt left join fetch service_opt.opts where service_opt.id =:id")
    Optional<ServiceOpt> findOneWithEagerRelationships(@Param("id") Long id);

}
