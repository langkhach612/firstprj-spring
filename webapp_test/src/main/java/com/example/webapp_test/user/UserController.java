package com.example.webapp_test.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {
    @Autowired private UserService service;

    @GetMapping("/user")
    public String showUserList(Model model){
        List<User> listUsers = service.listALL();
        model.addAttribute("listUsers",listUsers);
        return "user";
    }
    @GetMapping("/user/new")
    public String showNewForm(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("pageTitle","Add new user");
        return "user_form";
    }

    @PostMapping("/user/save")
    public String saveUSer(User user, RedirectAttributes ra){
        service.save(user);
        ra.addFlashAttribute("message","saved user success");
        return "redirect:/user";
    }

    @GetMapping("/user/edit{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra){
        try {
            User user = service.get(id);
            model.addAttribute("user", user );
            model.addAttribute("pageTitle","edit user (id : "  + id + ")");
            return "user_form";
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message",e.getMessage());
            return "redirect:/user";
        }
    }

    @GetMapping("/user/delete{id}")
    public String deleteUSer(@PathVariable("id") Integer id, RedirectAttributes ra){
        try {
            service.delete(id);
            ra.addFlashAttribute("message","user id : " + id + "has been deleted");
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message",e.getMessage());
        }
        return "redirect:/user";
    }

}
