package com.springproject.estates.api;




import com.springproject.estates.domain.EstateModel;
import com.springproject.estates.services.EstateServices;
import com.springproject.estates.services.ParametersServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class EstatesController {

    @Autowired
    private EstateServices estateServices;
    @Autowired
    private ParametersServices parametersServices;



    @GetMapping(value = "/estate")
    public String viewEstatePage(Model model){

        model.addAttribute("estates",estateServices.getAllEstate());
        model.addAttribute("numbershares",parametersServices.findbyname("number_shares").getvalue());

        return "Estate/estate";
    }

    @RequestMapping(value = "/add/estate", method= RequestMethod.POST)
    public String AddEstate(@ModelAttribute EstateModel estates, @RequestParam("number_shares") Long numbershares) {

        estates.setNumberShares(numbershares);
        estates.setSale(false);
        estateServices.SaveEstate(estates);
        return "redirect:/estate";
    }

    @GetMapping(value = "/delete/estate/{id}")
    public String DeletEstate(@PathVariable("id") Long id){
        estateServices.DeletEstate(id);
        return "redirect:/estate";
    }

    @GetMapping(value = "/edit/estate/{id}")
    public String EditEstate(@PathVariable("id") Long id,Model model){
        model.addAttribute("estate", estateServices.FindEstate(id));
        return "Estate/EditEstate";
    }

    @RequestMapping(value = "/update/estate/{id}", method= RequestMethod.POST)
    public String UpdateEstate(@PathVariable("id") Long id,@ModelAttribute EstateModel estates, @RequestParam("number_shares") Long numbershares) {

        EstateModel estateModel=estateServices.FindEstate(id);
        estateModel.setNumberShares(numbershares);
        estateModel.setName(estates.getName());
        estateModel.setPrice(estates.getPrice());
        estateServices.SaveEstate(estateModel);
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

}
