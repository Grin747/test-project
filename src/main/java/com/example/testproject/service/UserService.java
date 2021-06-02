package com.example.testproject.service;

import com.example.testproject.entity.Jackal;
import com.example.testproject.entity.Picture;
import com.example.testproject.entity.User;
import com.example.testproject.repo.JackalRepo;
import com.example.testproject.repo.PicRepo;
import com.example.testproject.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    JackalRepo jackalRepo;
    @Autowired
    PicRepo picRepo;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final String[] allowedTypes = {"image/jpeg", "image/jpg"};

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepo.findByUsername(s);
    }

    public boolean saveUser(User user) {
        User userFromDB = userRepo.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return true;
    }

    public boolean deleteUser(UUID userId) {
        if (userRepo.findById(userId).isPresent()) {
            userRepo.deleteById(userId);
            return true;
        }
        return false;
    }

    public Stream<Jackal> getUserJackals(UUID userId){
        return jackalRepo.findAllByUserId(userId);
    }

    public boolean addImage(User user, MultipartFile file) throws IOException {

        var type = file.getContentType();
        if(!Arrays.asList(allowedTypes).contains(type)) return false;

        var encoder = Base64.getMimeEncoder();
        var jackalImg = scale(file.getBytes(), 100, 100);

        var pic = new Picture();
        pic.setPicture(encoder.encodeToString(file.getBytes()));
        pic.setType(type);

        var jackal = new Jackal();
        jackal.setSize(file.getSize());
        jackal.setName(file.getName());
        jackal.setUser(user);
        jackal.setJackal(encoder.encodeToString(jackalImg));

        pic.setJackal(jackal);
        jackal.setPic(pic);

        jackalRepo.save(jackal);
        picRepo.save(pic);

        return true;
    }

    public Picture getImage(UUID jackalId){
        return picRepo.findByJackalJackalId(jackalId);
    }

    private byte[] scale(byte[] fileData, int width, int height) {
        ByteArrayInputStream in = new ByteArrayInputStream(fileData);
        try {
            BufferedImage img = ImageIO.read(in);
            if(height == 0) {
                height = (width * img.getHeight())/ img.getWidth();
            }
            if(width == 0) {
                width = (height * img.getWidth())/ img.getHeight();
            }
            Image scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage imageBuff = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            imageBuff.getGraphics().drawImage(scaledImage, 0, 0, new Color(0,0,0), null);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            ImageIO.write(imageBuff, "jpg", buffer);

            return buffer.toByteArray();
        } catch (IOException e) {
            return null;
        }
    }
}
