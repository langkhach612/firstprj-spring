package com.example.webapp_test.user;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    public long countById(Integer id);
}
