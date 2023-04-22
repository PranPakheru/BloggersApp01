package com.myblog.blogapp.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class PostDto {
    private Long id;

    //using spring validation
    @NotNull
    @Size(min=2, message="Post title should have at least two character.")
    private String title;

    @NotNull
    @Size(min=10, message="Description should have at least 10 characters.")
    private String description;

    @NotNull
    @NotEmpty
    private String content;

    //@Email(message="email format is not valid") for email validation.
    //@Size(min=10, max=10, message="enter valid mobile number") for mobile number validation.


}
