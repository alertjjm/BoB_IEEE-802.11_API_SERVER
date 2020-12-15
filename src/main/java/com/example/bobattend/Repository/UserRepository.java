package com.example.bobattend.Repository;

import com.example.bobattend.Entity.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface UserRepository extends CrudRepository<Member,Integer> {
    public List<Member> findAll();//모든 멤버 찾는 메소드
    public List<Member> findAllByName(String name);//이름으로 모든 멤버 찾는 메소드
    public Member findById(String id);//id로 멤버 찾는 메소드
    public Member findMemberByPersonalid(int personalid);//personalid로 멤버 찾는 메소드
    public List<Member> findAllByPersonalidBetween(int s, int e);//personalid 범위 지정으로 모든 멤버 찾는 메소드
    public List<Member> findAllByNameIsNot(String name);//이름이 다른 것으로 모든 멤버 찾는 메소드
}
