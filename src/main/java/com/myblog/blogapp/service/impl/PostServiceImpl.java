package com.myblog.blogapp.service.impl;

import com.myblog.blogapp.entity.*;
import com.myblog.blogapp.exception.ResourceNotFoundException;
import com.myblog.blogapp.payload.PostDto;
import com.myblog.blogapp.payload.PostResponse;
import com.myblog.blogapp.repository.PostRepository;
import com.myblog.blogapp.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepo;                //works as @Autowired
    public PostServiceImpl(PostRepository postRepo, ModelMapper mapper){
        this.postRepo = postRepo;
        this.mapper = mapper;
    }

    //for mapper library
    private ModelMapper mapper;
//    public PostServiceImpl(ModelMapper mapper){
//        this.mapper = mapper;
//    }
    //now going to rewrite the Dto-Entity conversion codes.


//    @Override                                          //first way.
//    public PostDto createPost(PostDto postDto) {
//        Post post = new Post();
//
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
//
//        Post postEntity = postRepo.save(post);
//
//        PostDto dto = new PostDto();
//
//        dto.setId(postEntity.getId());
//        dto.setTitle(postEntity.getTitle());
//        dto.setDescription(postEntity.getDescription());
//        dto.setContent(postEntity.getContent());
//
//        return dto;
//    }


    @Override
    public PostDto createPost(PostDto postDto) {
        Post post1 = mapToEntity(postDto);           //convert dto to entity.
        Post post = postRepo.save(post1);

        PostDto dto = mapToDto(post);               //convert entity to dto.

        return dto;
    }

    public Post mapToEntity(PostDto postDto) {      //convert dto to etity.     rewriting with model mapper.
        Post post = mapper.map(postDto, Post.class);
        //dto will become Post(entity)

        //        Post post = new Post();
//
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }
    public PostDto mapToDto(Post post){             //convert entity to dto.         rewriting with model mapper.
        PostDto dto = mapper.map(post, PostDto.class);
        //post will become dto.

        //        PostDto dto = new PostDto();
//
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setDescription(post.getDescription());
//        dto.setContent(post.getContent());
        return dto;
    }


    @Override   //continuously adding parameters.
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {    //earlier return type was List<PostDto>
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        //equalsIgnore ignores upper and lower cases.
        //ASC.name() is like if condition, true condition execute left side of :, or right side of :

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);  //by() converts to String, in next time Sort.by(sortBy) is sort variable only.

        Page<Post> all = postRepo.findAll(pageable);
        List<Post> content = all.getContent();
        List<PostDto> postDtos = content.stream().map(x->mapToDto(x)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();

        postResponse.setContent(postDtos);     //initially i was giving postDtos back to, hence postDtos inside.
        postResponse.setPageNo(all.getNumber());
        postResponse.setPageSize(all.getSize());
        postResponse.setTotalElement(all.getTotalElements());
        postResponse.setTotalPages(all.getTotalPages());;
        postResponse.setLast(all.isLast());

        //it can be written like this as well...
        //return posts.stream().map(post->mapToDto(post)).collect(Collectors.toList());
        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        PostDto dto = mapToDto(post);
        return dto;
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post newPost = postRepo.save(post);
        return mapToDto(newPost);

    }

    @Override
    public void deletePost(long id) {
        Post post = postRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
        postRepo.deleteById(id);
    }


}
