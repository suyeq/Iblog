package com.suye.iblog.repository;

import com.suye.iblog.moder.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Vote 仓库.
 */
@Repository
public interface VoteRepository extends JpaRepository<Vote, Long>{
 
}
