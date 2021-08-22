package com.example.bobattend.Repository;

import com.example.bobattend.Entity.PureMember;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PureMemberRepository extends CrudRepository<PureMember, Integer> {
    PureMember findPureMemberByPersonalid(int personalid);
}
