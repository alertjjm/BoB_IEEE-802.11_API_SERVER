package com.example.bobattend.Repository;

import com.example.bobattend.Entity.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository extends CrudRepository<Member,Integer> {
    public List<Member> findAll();
    public List<Member> findAllByName(String name);
    public Member findById(String id);
    public Member findMemberByPersonalid(int personalid);
    public List<Member> findTop50by();
}
