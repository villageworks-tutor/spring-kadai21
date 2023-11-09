package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContactController {
	@GetMapping("/contact")
	public String index() {
		// 画面遷移：遷移先はお問い合わせ画面
		return "contactForm";
	}
	
	@PostMapping("/contact")
	public String contact(
			@RequestParam(name = "genre", defaultValue = "") Integer genre,
			@RequestParam(name = "lang",  defaultValue = "") String[] langList,
			@RequestParam(name = "detail",defaultValue = "") String detail,
			@RequestParam(name = "dueDate",defaultValue = "") 
			@DateTimeFormat(pattern = "yyyy-Mm-dd") LocalDate dueDate,
			@RequestParam(name = "name",  defaultValue = "") String name,
			@RequestParam(name = "email", defaultValue = "") String email,
			Model model) {
		// リクエストパラメータのチェックとエラーメッセージの初期化
		List<String> errors = new ArrayList<String>();
		// 言語の必須入力チェック
		if (langList.length == 0) {
			errors.add("言語は必須です");
		}
		// 実施予定日
		if (dueDate != null && dueDate.isBefore(LocalDate.now())) {
			errors.add("実施予定日は翌日以降を選択してください");
		}
		// 名前の必須入力チェック
		if (name.isEmpty()) {
			errors.add("名前は必須です");
		}
		// 名前の文字数チェック
		if (name.length() > 20) {
			errors.add("名前は20字以内で入力してください");
		}
		// メールアドレスの必須入力チェック
		if (email.isEmpty()) {
			errors.add("メールアドレスは必須です");
		}
		// エラーの有無をチェック
		if (errors.size() > 0) {
			// エラーがある場合：エラーメッセージをスコープに登録
			model.addAttribute("errors", errors);
			// 画面遷移：遷移先はお問い合わせフォーム画面
			return "contactForm";
		}
		// リクエストパラメータをスコープに登録
		model.addAttribute("genre", genre);
		model.addAttribute("langList", langList);
		model.addAttribute("detail", detail);
		model.addAttribute("dueDate", dueDate);
		model.addAttribute("name", name);
		model.addAttribute("email", email);
		// 画面遷移：遷移先はお問い合わせ結果画面
		return "contactResult";
	}
}
