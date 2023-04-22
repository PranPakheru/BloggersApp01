package com.myblog.blogapp.repository;

import com.myblog.blogapp.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(long postId);        //custom method in repository.

    //List<Comment> findByPostEmail(string email);  same can be done for name.
    //Comment findByMobile(long mobile);        this gives comment object.

    //we only need to write the incomplete methods, spring boot does the rest.
}
