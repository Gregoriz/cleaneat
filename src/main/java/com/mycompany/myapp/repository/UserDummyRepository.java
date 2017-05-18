package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.UserDummy;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UserDummy entity.
 */
@SuppressWarnings("unused")
public interface UserDummyRepository extends JpaRepository<UserDummy,Long> {

}
