package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller  // このクラスがWebリクエストを受け取るコントローラーであることを示す
public class AuthController {

    // ユーザー情報をDBに保存するためのリポジトリ
    private final UserRepository userRepository;

    // パスワードを安全に暗号化するためのエンコーダー
    private final BCryptPasswordEncoder passwordEncoder;

    // コンストラクタでリポジトリを受け取り、エンコーダーを初期化
    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder(); // ハッシュ化用のオブジェクト
    }

    // GETリクエスト「/signup」を受けた時、サインアップ画面を表示
    @GetMapping("/signup")
    public String signupForm() {
        return "user/signup"; // signup.html（テンプレート）を表示
    }

    // POSTリクエスト「/signup」を受けた時、ユーザー登録を処理
    @PostMapping("/signup")
    public String signup(@RequestParam String name, @RequestParam String password) {
        // 新しいユーザーオブジェクトを作成
        User user = new User();
        user.setName(name); // フォームから送られた名前をセット

        // パスワードをハッシュ化してからセット（セキュリティ対策）
        user.setPassword(passwordEncoder.encode(password));

        // DBにユーザーを保存
        userRepository.save(user);

        // 登録後、ログインページ(Spring Securityがデフォルトで用意してるページ)へリダイレクト
        return "redirect:/login";
    }
}
