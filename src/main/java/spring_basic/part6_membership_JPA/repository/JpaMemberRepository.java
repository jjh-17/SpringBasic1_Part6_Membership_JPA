package spring_basic.part6_membership_JPA.repository;

import jakarta.persistence.EntityManager;
import spring_basic.part6_membership_JPA.domain.Member;

import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    //JPA 관리 변수
    private final EntityManager entityManager;

    //생성자
    public JpaMemberRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    //메서드
    @Override
    public Member save(Member member) {
        entityManager.persist(member);

        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = entityManager.find(Member.class, id); //find(조회 클래스, 식별자)

        return Optional.ofNullable(member); //null을 반환할 수도 있음
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = entityManager.createQuery(
                        "select m from Member m where m.name = :name",  //sql
                        Member.class)   //조회 클래스
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();

    }

    @Override
    public List<Member> findAll() {
        return entityManager.createQuery(
                "select m from Member m",   //jpa query ==> 객체 자체를 select
                Member.class                       //엔티티 클래스
        ).getResultList();
    }
}
