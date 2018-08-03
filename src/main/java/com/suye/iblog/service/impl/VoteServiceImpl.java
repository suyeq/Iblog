package com.suye.iblog.service.impl;


import com.suye.iblog.moder.Vote;
import com.suye.iblog.repository.VoteRepository;
import com.suye.iblog.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Vote 服务.
 */
@Component
public class VoteServiceImpl implements VoteService {

	@Autowired
	private VoteRepository voteRepository;

	@Override
	@Transactional
	public void removeVote(Long id) {
		voteRepository.delete(id);
	}


	@Override
	public Vote getVoteById(Long id) {
		return voteRepository.findOne(id);
	}

}
