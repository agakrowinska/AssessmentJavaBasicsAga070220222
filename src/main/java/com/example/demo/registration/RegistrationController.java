package com.example.demo.registration;

import com.example.demo.appDoctor.AppDoctor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping(path = "api/v1/hospital")
@AllArgsConstructor
public class RegistrationController {

    //reference to the service
    private RegistrationService registrationService;


    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token, Model model){
        return registrationService.confirmToken(token);
    }
    @GetMapping (path = "/register")
    public String getRegisterPage(Model model){
        model.addAttribute("regRequest", new AppDoctor());
        return "register_page";
    }

    @PostMapping(path = "/register")
    public String register(@ModelAttribute AppDoctor request, Model model){
        System.out.println("registration request: "+ request.getEmail());
        String token = registrationService.register(
                new RegistrationRequest(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword()
                )
        );
        System.out.println(token);
        model.addAttribute("userName", request.getFirstName());
        return "personal_page";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute AppDoctor request, Model model){
        System.out.println("login request: "+ request.getEmail());
        //String token = registrationService.authenticate(
//        new RegistrationRequest(
//            request.getFirstName(),
//            request.getLastName(),
//            request.getEmail(),
//            request.getPassword()
//        )
//    );

        model.addAttribute("userName", request.getFirstName());

        return "personal_page";
    }


}

