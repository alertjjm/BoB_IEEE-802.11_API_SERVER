package com.example.bobattend.Service;

import com.example.bobattend.Dto.MemberDto;
import com.example.bobattend.Entity.Member;
import com.example.bobattend.Entity.Role;
import com.example.bobattend.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {
    private UserRepository memberRepository;

    // 회원가입
    @Transactional
    public int signUp(MemberDto memberDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));

        // password를 암호화 한 뒤 dp에 저장

        return memberRepository.save(memberDto.toEntity()).getPersonalid();
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        // 로그인을 하기 위해 가입된 user정보를 조회하는 메서드
        Member member = memberRepository.findById(id);
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("admin".equals(id)) {
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        }

        // 아이디, 비밀번호, 권한리스트를 매개변수로 User를 만들어 반환해준다.
        return new User(member.getId(), member.getPassword(), authorities);
    }
}
