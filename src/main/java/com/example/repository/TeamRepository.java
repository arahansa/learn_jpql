package com.example.repository;

import com.example.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jarvis on 15. 12. 31..
 */
public interface TeamRepository extends JpaRepository<Team, Long> {
}
