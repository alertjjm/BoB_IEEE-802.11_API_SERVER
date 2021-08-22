package com.example.bobattend.Repository;

import com.example.bobattend.Entity.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<Member, Integer> {
    List<Member> findAll();

    List<Member> findAllByName(String name);

    Member findById(String id);

    Member findMemberByPersonalid(int personalid);

    List<Member> findAllByPersonalidBetween(int s, int e);

    List<Member> findAllByNameIsNot(String name);
}
