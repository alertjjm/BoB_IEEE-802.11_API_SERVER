package com.example.bobattend.Controller;

import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/.well-known/pki-validation")
public class tokencontroller {
    @GetMapping(value ="/{token}")
    public String token(@PathVariable("token") String token)  {
        String tk="7B38DFA523E26B51783AD79F9229278254D20B8E6D86A5AB32FB51FA168DFFCA\n" +
                "comodoca.com\n" +
                "abb854dbc2f8806";
        return tk;
    }
}
//.well-known/pki-validation/E0F24DDDB9CD29501E8BD299F3C54495.txt