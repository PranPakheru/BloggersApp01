package com.myblog.blogapp.service.impl;

import com.myblog.blogapp.entity.Comment;
import com.myblog.blogapp.entity.Post;
import com.myblog.blogapp.exception.ResourceNotFoundException;
import com.myblog.blogapp.payload.CommentDto;
import com.myblog.blogapp.repository.CommentRepository;
import com.myblog.blogapp.repository.PostRepository;
import com.myblog.blogapp.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepo;
    private PostRepository postRepo;
    public CommentServiceImpl(CommentRepository commentRepo, PostRepository postRepo, ModelMapper mapper){
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
        this.mapper = mapper;
    }
    //constructor based injection.

    //using model mapper.
    private ModelMapper mapper;
//    public CommentServiceImpl(ModelMapper mapper){
//        this.mapper = mapper;
//    }
//    //for dto-entity conversion.


    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Post post = postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post", "id", postId));
        Comment comment = mapToEntity(commentDto);  //convert the dto to entity
        comment.setPost(post);                      //set post in comment
        Comment newComment = commentRepo.save(comment);     //saving the comment.
        return mapToDto(newComment);            //return the converted entity.
    }

    @Override
    public List<CommentDto> getCommentByPostId(long postId) {
        List<Comment> comments = commentRepo.findByPostId(postId);
        return comments.stream().map(c->mapToDto(c)).collect(Collectors.toList());
    }

    @Override
    public CommentDto updateComment(long postId, long id, CommentDto commentDto) {
        Post post = postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post", "id", postId));
        // to find the post if that is there or not.

        Comment comment = commentRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("comment", "id", id));
        //to find if the comment is there for the particular post or not.

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updatedComment = commentRepo.save(comment);     //saving after updating.

        return mapToDto(updatedComment);        //return after converting into dto.
    }

    @Override
    public void deleteComment(long postId, long id) {
        postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post", "postId", id));
        //for post

        commentRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("comment", "id", id));
        //for comment

        commentRepo.deleteById(id);
    }


    public Comment mapToEntity(CommentDto commentDto){      //rewriting codes for conversion.
        Comment comment = mapper.map(commentDto, Comment.class);
        //dto will become comment(entity).


        //        Comment comment =  new Comment();
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());
        return comment;
    }

    public CommentDto mapToDto(Comment comment){            //rewriting codes for conversion.
        CommentDto commentDto = mapper.map(comment, CommentDto.class);
        //comment will become dto.


        //        CommentDto commentDto = new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());
        return commentDto;
    }
}
