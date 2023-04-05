package com.jtech.jhs.ui;

//import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jtech.jhs.api.APIController;

@Controller
public class PortalController {

    private static final Logger log = LoggerFactory.getLogger(PortalController.class);

    @Autowired
    private APIController api;

    /*    @GetMapping("/")
 public String main(Model model) {
        return home(model);
    }
*/

        @GetMapping("/")
 public ModelAndView main(Model model) {
        return search("");
    }


    @GetMapping("/home.do")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/search.do")
    public ModelAndView search(@RequestParam String q) {
        log.debug("/search " + q);
        ModelAndView mav = new ModelAndView("search");
        mav.addObject("jfiles", api.getSearch(q));
        return mav;
    }
    
}
