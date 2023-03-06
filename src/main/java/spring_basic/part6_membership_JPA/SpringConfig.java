package spring_basic.part6_membership_JPA;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring_basic.part6_membership_JPA.repository.JpaMemberRepository;
import spring_basic.part6_membership_JPA.repository.MemberRepository;
import spring_basic.part6_membership_JPA.service.MemberService;

@Configuration
public class SpringConfig {

    //변수
    private final EntityManager entityManager;

    //생성자
    @Autowired
    public SpringConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new JpaMemberRepository(entityManager);
    }

}
