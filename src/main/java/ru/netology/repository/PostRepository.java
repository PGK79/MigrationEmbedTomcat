package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepository {
    private final Map<Long, Post> allPosts = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong(0);

    public List<Post> all() {
        return new ArrayList<>(allPosts.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(allPosts.get(id));
    }

    public Post save(Post post) {
        long idInList = counter.getAndIncrement();

        if (post.getId() == 0) {
            post.setId(idInList + 1);
            allPosts.put(idInList + 1, post);
        } else {
            allPosts.put(post.getId(), post);
        }
        return post;
    }

    public void removeById(long id) {
        allPosts.remove(id);
    }
}