package com.yakos.map.repository;



import com.yakos.map.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String userName);
    boolean existsByUsername(String username);
    boolean existsByMail(String mail);

}