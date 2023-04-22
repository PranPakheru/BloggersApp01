package com.myblog.blogapp.controller;

import com.myblog.blogapp.payload.CommentDto;
import com.myblog.blogapp.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private CommentService commentService;
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }                           //constructor based injection.

    //creating comment, http://localhost:8080/api/posts/{postId}/comments
    //@Valid for spring validation.
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Object> createComment(@Valid @PathVariable("postId") long postId, @RequestBody CommentDto commentDto, BindingResult bindingResult){

       if(bindingResult.hasErrors()){
           return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.CONFLICT);
       }

        CommentDto dto = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    //instead of CommentDto i am putting Object class, so that CommentDto can be converted to object for the response.


    //reading the comment,  http://localhost:8080/api/posts/{postId}/comments/{id}
    @GetMapping("posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable("postId") long postId){
        List<CommentDto> dto = commentService.getCommentByPostId(postId);
        return dto;
    }

    //updating the comment,  http://localhost:8080/api/posts/{postId}/comments/{id}
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable("postId") long postId, @PathVariable("id") long id, @RequestBody CommentDto commentDto){
        //we take three parameters in this postId, commentId  and json object to update.

        CommentDto dto = commentService.updateComment(postId, id, commentDto);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //deleting the comment, http://localhost:8080/api/posts/{postId}/comments/{id}
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable("postId") long postId, @PathVariable("id") long id){
        commentService.deleteComment(postId, id);
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
    }
}
