package com.suye.iblog.service.impl;

import com.suye.iblog.moder.*;
import com.suye.iblog.repository.BlogRepository;
import com.suye.iblog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.transaction.Transactional;


public class BlogServiceImpl implements BlogService{


    @Autowired
    private BlogRepository blogRepository;

    /**
     * 保存博客的数据
     * @param blog
     * @return
     */
    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        //boolean isNew = (blog.getId() == null);
       // EsBlog esBlog = null;
        Blog returnBlog = blogRepository.save(blog);
//        if (isNew) {
//            esBlog = new EsBlog(returnBlog);
//        } else {
//            esBlog = esBlogService.getEsBlogByBlogId(blog.getId());
//            esBlog.update(returnBlog);
//        }
//
//        esBlogService.updateEsBlog(esBlog);
        return returnBlog;
    }

    /**
     * 根据id删除博客
     * @param id
     */
    @Transactional
    @Override
    public void removeBlog(Long id) {
        blogRepository.delete(id);
//        EsBlog esblog = esBlogService.getEsBlogByBlogId(id);
//        esBlogService.removeEsBlog(esblog.getId());
    }

    /**
     * 根据id查询博客
     * @param id
     * @return
     */
    @Override
    public Blog getBlogById(Long id) {
        return blogRepository.findOne(id);
    }

    /**
     * 根据标题与热度分页查询博客(最新)
     * @param user
     * @param title
     * @param pageable
     * @return
     */
    @Override
    public Page<Blog> listBlogsByTitleVote(User user, String title, Pageable pageable) {
        // 模糊查询
        title = "%" + title + "%";
        //Page<Blog> blogs = blogRepository.findByUserAndTitleLikeOrderByCreateTimeDesc(user, title, pageable);
        String tags = title;
        Page<Blog> blogs = blogRepository.findByTitleLikeAndUserOrTagsLikeAndUserOrderByCreateTimeDesc(title,user, tags,user, pageable);
        return blogs;
    }

    /**
     *最热
     * @param user
     * @param title
     * @param pageable
     * @return
     */
    @Override
    public Page<Blog> listBlogsByTitleVoteAndSort(User user, String title, Pageable pageable) {
        // 模糊查询
        title = "%" + title + "%";
        Page<Blog> blogs = blogRepository.findByUserAndTitleLike(user, title, pageable);
        return blogs;
    }

    /**
     * 分类查询博客
     * @param catalog
     * @param pageable
     * @return
     */
    @Override
    public Page<Blog> listBlogsByCatalog(Catalog catalog, Pageable pageable) {
        Page<Blog> blogs = blogRepository.findByCatalog(catalog, pageable);
        return blogs;
    }

    /**
     * 阅读量的增长
     * @param id
     */
    @Override
    public void readingIncrease(Long id) {
        Blog blog = blogRepository.findOne(id);
        blog.setReadSize(blog.getCommentSize()+1);
        this.saveBlog(blog);
    }

    /**
     * 创建一个评论
     * @param blogId
     * @param commentContent
     * @return
     */
    @Override
    public Blog createComment(Long blogId, String commentContent) {
        Blog originalBlog = blogRepository.findOne(blogId);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Comment comment = new Comment(user, commentContent);
        originalBlog.addComment(comment);
        return this.saveBlog(originalBlog);
    }

    /**
     * 删除一个评论
     * @param blogId
     * @param commentId
     */
    @Override
    public void removeComment(Long blogId, Long commentId) {
        Blog originalBlog = blogRepository.findOne(blogId);
        originalBlog.removeComment(commentId);
        this.saveBlog(originalBlog);
    }

    /**
     * 对某篇博客点赞
     * @param blogId
     * @return
     */
    @Override
    public Blog createVote(Long blogId) {
        Blog originalBlog = blogRepository.findOne(blogId);
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Vote vote = new Vote(user);
        boolean isExist = originalBlog.addVote(vote);
        if (isExist) {
            throw new IllegalArgumentException("该用户已经点过赞了");
        }
        return this.saveBlog(originalBlog);
    }

    /**
     * 取消对某篇博客的点赞
     * @param blogId
     * @param voteId
     */
    @Override
    public void removeVote(Long blogId, Long voteId) {
        Blog originalBlog = blogRepository.findOne(blogId);
        originalBlog.removeVote(voteId);
        this.saveBlog(originalBlog);
    }
}
