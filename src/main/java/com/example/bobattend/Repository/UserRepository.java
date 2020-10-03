package com.example.bobattend.Repository;

import com.example.bobattend.Entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository extends CrudRepository<User,Integer> {
    public List<User> findAll();
    public List<User> findAllByName(String name);
    public User findById(String id);
}
