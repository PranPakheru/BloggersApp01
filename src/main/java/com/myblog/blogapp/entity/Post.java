package com.myblog.blogapp.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="posts", uniqueConstraints = {@UniqueConstraint(columnNames={"title", "description"})})
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String content;

    //creating below for join operation.
    @OneToMany(mappedBy="post", cascade=CascadeType.ALL, orphanRemoval=true)
    Set<Comment> comment = new HashSet();


}
