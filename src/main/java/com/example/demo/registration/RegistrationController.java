package com.example.demo.registration;

import com.example.demo.appDoctor.AppDoctor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "api/v1/hospital")
@AllArgsConstructor
public class RegistrationController {

    //reference to the service
    private RegistrationService registrationService;

    //annotation post mapping so our advanced Rest client can see it
    @PostMapping
    public String register(@RequestBody RegistrationRequest request){
        return registrationService.register(request);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token){
        return registrationService.confirmToken(token);
    }
    @GetMapping (path = "register")
    public String getRegisterPage(Model model){
        model.addAttribute("regRequest", new AppDoctor());
        return "register_page";
    }
}
