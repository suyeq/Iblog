package com.suye.iblog.service.impl;

import com.suye.iblog.moder.Comment;
import com.suye.iblog.repository.CommentRepository;
import com.suye.iblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Comment 服务.
 */
@Component
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Override
	@Transactional
	public void removeComment(Long id) {
		commentRepository.delete(id);
	}


	@Override
	public Comment getCommentById(Long id) {
		return commentRepository.findOne(id);
	}

}
