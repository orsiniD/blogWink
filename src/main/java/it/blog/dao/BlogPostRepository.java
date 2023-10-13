package it.blog.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.blog.model.BlogPost;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
	List<BlogPost> findByStatus(String string);
}
