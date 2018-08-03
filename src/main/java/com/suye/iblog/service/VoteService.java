package com.suye.iblog.service;


import com.suye.iblog.moder.Vote;
import org.springframework.stereotype.Service;

/**
 * Vote 服务接口.
 */
@Service
public interface VoteService {


	/**
	 * 根据id获取 Vote
	 * @param id
	 * @return
	 */
	Vote getVoteById(Long id);


	/**
	 * 删除Vote
	 * @param id
	 * @return
	 */
	void removeVote(Long id);
}
