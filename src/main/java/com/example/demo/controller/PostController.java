package com.example.demo.controller;

import com.example.demo.entity.Post;
import com.example.demo.repository.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {

    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 投稿一覧ページ
    @GetMapping("/post/list")
    public String postList(Model model) {
        model.addAttribute("posts", postRepository.findAll());
        return "post/list"; // templates/posts.html を返す
    }

    // 投稿詳細ページ
    @GetMapping("/post/detail/{id}")
    public String postShow(@PathVariable Integer id,Model model) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("投稿が見つかりません"));
        model.addAttribute("post", post);
        return "post/detail";
    }



    // 投稿フォームページ
    @GetMapping("/post/form")
    public String formPage() {
        return "post/form"; // templates/form.html を返す
    }

    // 投稿送信処理
    @PostMapping("/post/form")
    public String submitForm(@RequestParam String title, @RequestParam String content) {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        postRepository.save(post);
        return "redirect:/post/list";
    }
}
