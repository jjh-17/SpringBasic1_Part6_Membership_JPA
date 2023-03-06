package spring_basic.part6_membership_JPA.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import spring_basic.part6_membership_JPA.domain.Member;
import spring_basic.part6_membership_JPA.repository.MemberRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//실제 DB와 연결하여 테스트 진행
//테스트 전 DB를 비울 것
@SpringBootTest //spring test
@Transactional //테스트 후 시작 전으로 DB를 롤백
class MemberServiceIntegrationTest {
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository; //MemberRepository를 @Autowired하면 SpringConfig에서 spring 연결

    @Test
    //@Commit //DB 반영
    public void 회원가입() throws Exception {
        //Given
        Member member = new Member();
        member.setName("jjh");

        //When
        Long saveId = memberService.join(member);

        //Then
        Member findMember = memberRepository.findById(saveId).get();
        assertEquals(member.getName(), findMember.getName());
    }

    @Test
    public void 중복회원예외() throws Exception {
        //Given
        Member member1 = new Member();
        Member member2 = new Member();
        member1.setName("spring");
        member2.setName("spring");

        //When
        memberService.join(member1);

        //Then
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));//예외가 발생해야 한다.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }
}
