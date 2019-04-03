package am.itspace.springsocial.controller;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    private Facebook facebook;
    private ConnectionRepository connectionRepository;

    public MainController(Facebook facebook, ConnectionRepository connectionRepository) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
    }

    @GetMapping("/feed")
    public String feed(Model model) {
        if (getModel(model)) {
            return "redirect:/connect/facebook";
        }
        PagedList<Post> userFeed = facebook.feedOperations().getFeed();
        model.addAttribute("userFeed", userFeed);
        return "feed";
    }

    @GetMapping("/friends")
    public String friends(Model model) {
        if (getModel(model)) {
            return "redirect:/connect/facebook";
        }
        List<User> friends = facebook.friendOperations().getFriendProfiles();
        model.addAttribute("friends", friends);
        return "friends";
    }

    private boolean getModel(Model model) {
        if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return true;
        }
        User userProfile = facebook.userOperations().getUserProfile();
        model.addAttribute("userProfile", userProfile);
        return false;
    }

}
