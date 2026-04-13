package com.example.demo.controller;

import com.example.demo.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private final List<User> dataMahasiswa = new ArrayList<>();

    private final String USERNAME = "admin";
    private final String PASSWORD = "20230140240";

    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            RedirectAttributes redirectAttributes
    ) {
        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            return "redirect:/home";
        }

        redirectAttributes.addFlashAttribute("error", "Username atau password salah.");
        return "redirect:/";
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("title", "Website Tasnim - 20230140240");
        model.addAttribute("subtitle", "Tugas Deploy Pertemuan 6");
        model.addAttribute("dataMahasiswa", dataMahasiswa);
        return "home";
    }

    @GetMapping("/form")
    public String formPage(Model model) {
        model.addAttribute("title", "Form Input Mahasiswa");
        model.addAttribute("user", new User());
        return "form";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        dataMahasiswa.add(user);
        redirectAttributes.addFlashAttribute("success", "Data berhasil ditambahkan.");
        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("success", "Berhasil logout.");
        return "redirect:/";
    }
}
