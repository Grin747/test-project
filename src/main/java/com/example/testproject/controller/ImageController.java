package com.example.testproject.controller;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.example.testproject.entity.Jackal;
import com.example.testproject.entity.User;
import com.example.testproject.service.UserService;
import com.example.testproject.storage.StorageFileNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;

@Controller
public class ImageController {

    private final UserService userService;
    private final static Comparator[] comparators = {
            Comparator.comparing(Jackal::getName),
            Comparator.comparing(Jackal::getDate),
            Comparator.comparing(Jackal::getSize)
    };

    @Autowired
    public ImageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/files")
    public String listUploadedFiles(Model model, Authentication authentication,
                                    @RequestParam(name = "sortBy", defaultValue = "0") int sort) {

        var user = (User) authentication.getPrincipal();

        var images = userService.getUserJackals(user.getId());
        images.sort(comparators[sort]);

        model.addAttribute("map", images);
        model.addAttribute("sort", sort);

        return "listing";
    }

    @PostMapping("/upload")
    public String handleImageUpload(@RequestParam("file") MultipartFile file, @RequestParam("imageName") String name,
                                   Authentication authentication) throws IOException {

        var user = (User) authentication.getPrincipal();
        userService.addImage(user, file, name);
        return "redirect:/files";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("delPicId") UUID id, Authentication authentication){

        var user = (User) authentication.getPrincipal();
        userService.deletePicture(id);
        return "redirect:/files";
    }

    @GetMapping("/img/{imgId}")
    public String getImage(@PathVariable UUID imgId, Authentication authentication, Model model) {

        var user = (User) authentication.getPrincipal();
        model.addAttribute("img", userService.getImage(imgId).getPicture());
        return "fullsize";
    }
}
