package com.springproject.estates.api;




import com.springproject.estates.domain.EstateModel;
import com.springproject.estates.domain.Tracing;
import com.springproject.estates.domain.User;
import com.springproject.estates.security.ExpiredToken;
import com.springproject.estates.services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import java.sql.Date;

import static java.util.Arrays.stream;

@Controller @Slf4j
public class EstatesController {

    @Autowired
    private EstateServices estateServices;
    @Autowired
    private ParametersServices parametersServices;
    @Autowired
    private TracingServices  tracingServices;
    @Autowired
    private UserService userService;



    @GetMapping(value = "/estate")
    public String viewEstatePage(Model model){


        model.addAttribute("estates",estateServices.getAllEstate());
        if(parametersServices.findbyname("number_shares")!=null)
        model.addAttribute("numbershares",parametersServices.findbyname("number_shares").getvalue());

        return "Estate/estate";
    }

    @RequestMapping(value = "/add/estate", method= RequestMethod.POST)
    public String AddEstate(@ModelAttribute EstateModel estates, @RequestParam("number_shares") Long numbershares, HttpServletRequest request,@ModelAttribute Tracing tracing) {


        if( userService.getUser(request)!= null){

            //add tracing
            tracing.setUser_add(userService.getUser(request) );
            tracing.setAdd_date(new Date(System.currentTimeMillis()));
            tracingServices.save(tracing);

            //add estate
            estates.setNumberShares(numbershares);
            estates.setSale(false);
            estates.setTracing(tracing);
            estateServices.SaveEstate(estates);

            log.info("saving new estate {} to the database : {}",estates.getName(),tracing.getUser_add().getUsername() );
            log.info("saving new tracing {} to the database :{}",tracing,tracing.getUser_add().getUsername());

        }
        return "redirect:/estate";
    }

    @GetMapping(value = "/delete/estate/{id}")
    public String DeletEstate(@PathVariable("id") Long id,HttpServletRequest request){

        User user=userService.getUser(request);
        if(user!=null) {
            log.info("delete estate {} :{}", estateServices.FindEstate(id).getName(), user.getUsername());
            estateServices.DeletEstate(id);
        }
        return "redirect:/estate";
    }

    @GetMapping(value = "/edit/estate/{id}")
    public String EditEstate(@PathVariable("id") Long id,Model model){
        model.addAttribute("estate", estateServices.FindEstate(id));
        return "Estate/EditEstate";
    }

    @RequestMapping(value = "/update/estate/{id}", method= RequestMethod.POST)
    public String UpdateEstate(@PathVariable("id") Long id,@ModelAttribute EstateModel estates, @RequestParam("number_shares") Long numbershares,HttpServletRequest request) {



        if( userService.getUser(request)!= null) {

            EstateModel estateModel=estateServices.FindEstate(id);
            Tracing tracingedit=estateModel.getTracing();
            log.info("update  tracing {} to the database",tracingedit);


            //edit tracing
            tracingedit.setUser_edit(userService.getUser(request));
            tracingedit.setEdit_date(new Date(System.currentTimeMillis()));
            tracingServices.save(tracingedit);


            //edit estate
            estateModel.setNumberShares(numbershares);
            estateModel.setName(estates.getName());
            estateModel.setPrice(estates.getPrice());
            estateServices.SaveEstate(estateModel);

            log.info("update  estate {} to the database :{}",estates.getName(),tracingedit.getUser_add().getUsername() );
        }

        return "redirect:/estate";
    }


    @GetMapping(value = "/sale/{message}/{alert_type}")
    public String viewSalesPage(Model model,@PathVariable("message") String message,@PathVariable("alert_type") String alert_type){
        model.addAttribute("estate",estateServices.EstateIsntSale());
        model.addAttribute("message",message);
        model.addAttribute("alert_type",alert_type);
        return "Estate/SaleEstate";
    }

    @RequestMapping(value = "/edit/sale/{id}", method= RequestMethod.GET)
    public String SalesEstate(@PathVariable("id") Long id,Model model){
        long SellingPrice=estateServices.FindEstate(id).getPrice()*parametersServices.findbyname("profit_ratio").getvalue();
        model.addAttribute("SellingPrice",SellingPrice);
        model.addAttribute("estate",estateServices.FindEstate(id));
        return "Estate/Sale";
    }

    @RequestMapping(value = "/update/estate/sell/{id}/{version}", method= RequestMethod.POST)
    public String UpdateSale(@PathVariable("id") Long id,@PathVariable("version") Long version, HttpServletRequest request, @RequestParam("sell_price") Long sellprice) {

        String alert_type=null ,message = null;

        EstateModel estateModel=estateServices.FindEstate(id);
        if(version == estateModel.getVersion()){
            estateModel.setSellingPrice(sellprice);
            estateModel.setBuyerName(request.getParameter("buyer_name"));
            estateModel.setSale(true);
            estateServices.SaveEstate(estateModel);
        }else {
             message="Sorry , The estate has been sold ";
             alert_type="error";
        }




        return "redirect:/sale/"+message+"/"+alert_type;
    }

    @GetMapping(value = "/show/estate/{id}")
    public String showestate(Model model ,@PathVariable("id") Long id){

        EstateModel estateModel=estateServices.FindEstate(id);
        Tracing tracingedit=estateModel.getTracing();

        model.addAttribute("estate",estateModel);
        model.addAttribute("tracing",tracingedit);
        model.addAttribute("user_add",tracingedit.getUser_add().getUsername());
        if(tracingedit.getUser_edit()!=null)
        model.addAttribute("user_edit",tracingedit.getUser_edit().getUsername());


        return "Estate/show";
    }

}
