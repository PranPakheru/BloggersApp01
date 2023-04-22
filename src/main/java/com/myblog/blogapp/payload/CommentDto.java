package com.myblog.blogapp.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CommentDto {
    private long id;

    @NotNull
    @Size(min=10, message="body should have at least 10 characters.")
    private String body;

    @Email(message="enter valid email address.")
    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    @NotEmpty
    @Size(min=2, message="name should have at least 2 characters.")
    private String name;

}
