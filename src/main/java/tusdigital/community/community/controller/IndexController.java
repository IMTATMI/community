package tusdigital.community.community.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class IndexController {

    @RequestMapping({"/","/index"})
    public String index(){
        return "index";
    }

    @RequestMapping("/test")
    public String test(){
        return "";
    }

    //根据id删除
    @RequestMapping("/test/{id}")
    public String delete(@PathVariable("id") Integer id){
       return "";
    }

}
