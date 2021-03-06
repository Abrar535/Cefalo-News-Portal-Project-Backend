package com.cefalonewsportal.backend.repository;

import com.cefalonewsportal.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

    User findByUserName(String userName);


}
