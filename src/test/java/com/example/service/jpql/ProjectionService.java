package com.example.service.jpql;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Member;
import com.example.domain.Team;
import com.example.domain.dto.UserDTO;
import com.example.repository.MemberRepository;

@Service
@Transactional
public class ProjectionService {

	@Autowired MemberRepository memberRepository;
	@PersistenceContext EntityManager em;
	
	public List<Member> getMembersWithJPQLProjection(){
		String jpql="select m from Member as m where m.username='kim'";
		List<Member> resultList = em.createQuery(jpql, Member.class).getResultList();
		return resultList;
	}
	
	public List<Team> getTeamsWithJPQLProjection(){
		String jpql = "select m.team from Member as m";
		List<Team> resultList = em.createQuery(jpql, Team.class).getResultList();
		return resultList;
	}
	
	public List<Object[]> getProjectionEntities(){
		Query query = em.createQuery("SELECT o.member, o.product, o.orderAmount FROM Order o");
		List<Object[]> resultList  = query.getResultList();
		return resultList;
	}
	public List<UserDTO> getProjectionEntitiesWithNewKeyword(){
		TypedQuery<UserDTO> query = em.createQuery("SELECT new com.example.domain.dto.UserDTO(m.username, m.age) FROM Member m", UserDTO.class);
		List<UserDTO> resultList = query.getResultList();
		return resultList;
	}
	
	
}
