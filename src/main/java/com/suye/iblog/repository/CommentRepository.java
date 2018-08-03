package com.suye.iblog.repository;

import com.suye.iblog.moder.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Comment 仓库.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
 
}
