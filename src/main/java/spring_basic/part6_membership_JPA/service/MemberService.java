package spring_basic.part6_membership_JPA.service;

import org.springframework.transaction.annotation.Transactional;
import spring_basic.part6_membership_JPA.domain.Member;
import spring_basic.part6_membership_JPA.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Transactional //JPA 사용 시 반드시 추가해야 함
public class MemberService {
    private final MemberRepository memberRepository;

    //store과 sequence가 static(메모리에 선언됨) 이기에 다른 인스턴스더라도 같은 값을 공유가능
    //두 변수가 static이 아니게되면 오류가 발생한다. ==> 같은 인스턴스를 사용하도록 변경 필요
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository; //외부에서 값을 받는다
    }

    //ctrl + shift + alt + t ==> 리펙토링 가능
    //회원 가입
    public Long join(Member member){
        checkDuplicateName(member);     //중복 이름 확인
        memberRepository.save(member);

        return member.getId();

    }

    private void checkDuplicateName(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    //전체 회원 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    //회원 조회
    public Optional<Member> findOne(Long id){
        return memberRepository.findById(id);
    }
}
