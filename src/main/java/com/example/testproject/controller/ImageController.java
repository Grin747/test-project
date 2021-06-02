package com.example.testproject.controller;

import java.io.IOException;
import java.util.UUID;

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

	@Autowired
	public ImageController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/")
	public String index(Model model) {
		return "index";
	}

	@GetMapping("/files")
	public String listUploadedFiles(Model model, Authentication authentication) {

		var user = (User) authentication.getPrincipal();
		return "listing";
	}

	@PostMapping("/upload")
	public String handleFileUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes, Authentication authentication) throws IOException {

		var user = (User) authentication.getPrincipal();
		userService.addImage(user, file);
		return "redirect:/files";
	}

	@GetMapping("/img/{imgId}")
	public String getImage(@PathVariable UUID imgId, Authentication authentication){

		var user = (User) authentication.getPrincipal();
		return null;
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

}
