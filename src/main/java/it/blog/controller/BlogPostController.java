package it.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.blog.dao.BlogPostRepository;
import it.blog.model.BlogPost;

@RestController
@RequestMapping("/api/posts")
public class BlogPostController {
    @Autowired
    private BlogPostRepository blogPostRepository;

    @GetMapping
    public List<BlogPost> getPublicPosts() {
        return blogPostRepository.findByStatus("published");
    }

    @PostMapping
    public BlogPost createPost(@RequestBody BlogPost post) {
        // Set default values
        post.setStatus("draft");
        post.setAuthorName("Brian Fox");
        return blogPostRepository.save(post);
    }

    @PutMapping("/{postId}/publish")
    public BlogPost publishPost(@PathVariable Long postId) {
        BlogPost post = blogPostRepository.findById(postId).orElse(null);
        if (post != null) {
            post.setStatus("published");
            return blogPostRepository.save(post);
        }
        return null; // Handle error gracefully
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId) {
        blogPostRepository.deleteById(postId);
    }

    @GetMapping("/filter")
    public List<BlogPost> filterByHashtags(@RequestParam List<String> hashtags) {
        return blogPostRepository.findByStatusAndHashtagsIn("published", hashtags);
    }
}
