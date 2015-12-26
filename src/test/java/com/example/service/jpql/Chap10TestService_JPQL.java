package com.example.service.jpql;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Member;
import com.example.repository.MemberRepository;

@Service
@Transactional
public class Chap10TestService_JPQL {

	@Autowired MemberRepository memberRepository;
	@PersistenceContext EntityManager em;
	
	public Member saveMember(Member member){
		return memberRepository.save(member);
	}
	
	public void saveMemberVoid(Member member){
		em.persist(member);
	}
	public void saveObject(Object object){
		em.persist(object);
	}
	
	public List<Member> getMembersWithJPQL(){
		String jpql="select m from Member as m where m.username='kim'";
		List<Member> resultList = em.createQuery(jpql, Member.class).getResultList();
		return resultList;
	}
	
	public List<Member> getMembersWithCriteria(){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Member> query = cb.createQuery(Member.class);
		
		//루트 클래스 ( 조회를 시작할 클래스 )
		Root<Member> m = query.from(Member.class);
		CriteriaQuery<Member> cq = query.select(m).where(cb.equal(m.get("username"), "kim"));
		List<Member> resultList = em.createQuery(cq).getResultList();
		return resultList;
	}
	
	public List<Member> getMembersWithTypeQuery(){
		TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);
		return query.getResultList();
	}
	public List getUnclearType(){
		javax.persistence.Query query = em.createQuery("select m.username, m.age from Member m");
		return query.getResultList();
	}
	
	public List<Member> getMembersWithTypeQueryParameterBinding(String username){
		TypedQuery<Member> query = em.createQuery("select m from Member m where m.username=:username", Member.class);
		query.setParameter("username", username);
		return query.getResultList();
	}
	
	public List<Member> getMembersWithTypeQueryLocationBinding(String username){
		TypedQuery<Member> query = em.createQuery("select m from Member m where m.username=?1", Member.class);
		query.setParameter(1, username);
		return query.getResultList();
	}
	
	public Object[] getGatheringInfo(){
		Query query = em.createQuery("select count(m), sum(m.age), avg(m.age), max(m.age), min(m.age) from Member m");
		return (Object[]) query.getSingleResult();
	}
	
	public List<Object[]> getGatheringInfoWithGroupBy(){
		String jpql = "select t.name, count(m), sum(m.age), avg(m.age), max(m.age), min(m.age) "
				+ "from Member m LEFT JOIN m.team t "
				+ "GROUP BY t.name HAVING AVG(m.age) >= 10";
		Query query = em.createQuery(jpql);
		return query.getResultList();
	}
	
	
	
}
