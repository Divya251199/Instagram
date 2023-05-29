package com.geekster.InstagramClone.service;

import com.geekster.InstagramClone.dto.PostOutput;
import com.geekster.InstagramClone.model.Post;
import com.geekster.InstagramClone.model.User;
import com.geekster.InstagramClone.repository.IPostRepo;
import com.geekster.InstagramClone.repository.ITokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class PostService {

    @Autowired
    IPostRepo postRepo;

    @Autowired
    LikeService likeService;

    @Autowired
    ITokenRepo tokenRepo;
    public void addPost(Post post) {
        postRepo.save(post);
    }

    public List<PostOutput> getAllPosts(String token) {
        User user = tokenRepo.findFirstByToken(token).getUser();
        List<Post> postList = postRepo.findByUser(user);
        List<PostOutput> list = new ArrayList<>();
        PostOutput newPost = new PostOutput();
        for ( Post post : postList) {
            newPost.setPostData(post.getPostData());
            newPost.setPostCaption(post.getPostCaption());
            newPost.setLocation(post.getLocation());
            newPost.setCreatedDate(post.getCreatedDate());
            newPost.setInstagramName(user.getInstagramName());
            list.add(newPost);
        }
        return list;


    }

    public long getLikes(Long postId) {

         return likeService.getLikes(postId);
    }

}
