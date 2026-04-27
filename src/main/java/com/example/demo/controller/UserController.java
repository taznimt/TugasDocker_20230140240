package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String loginPage(Model model) {
        model.addAttribute("kataHariIni", "Hari ini adalah hari yang indah untuk memulai sesuatu yang baru ✨");
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            RedirectAttributes redirectAttributes
    ) {
        Optional<User> user = userRepository.findByUsernameAndPassword(username, password);

        if (user.isPresent()) {
            return "redirect:/home";
        }

        redirectAttributes.addFlashAttribute("error", "Username atau password salah yaa 💔");
        return "redirect:/";
    }

    @GetMapping("/home")
    public String home(Model model) {
        List<User> dataMahasiswa = userRepository.findByUsernameIsNull();

        model.addAttribute("title", "Website Tasnim 🌸");
        model.addAttribute("subtitle", "Halaman utama yang lucu dan manis");
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
        userRepository.save(user);

        redirectAttributes.addFlashAttribute("success", "Data berhasil disimpan ke database ✨");
        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("success", "Berhasil logout yaa 👋");
        return "redirect:/";
    }
}