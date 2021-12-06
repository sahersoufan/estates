package com.springproject.estates.api;

import com.springproject.estates.domain.EstateModel;
import com.springproject.estates.domain.Parameters;
import com.springproject.estates.services.ParametersServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
public class ParametersController {
    @Autowired
    ParametersServices parametersServices;
    @GetMapping(value = "/parameter")
    public String viewEstatePage(Model model){

        model.addAttribute("parameters",parametersServices.getAllParamter());
        return "Parameter/parameter";
    }

    @RequestMapping(value = "/add/parameter", method= RequestMethod.POST)
    public String AddParameter(@ModelAttribute Parameters parameters,  @RequestParam("value0") Long value) {

        parameters.setvalue(value);
       parametersServices.SaveParameter(parameters);
        return "redirect:/parameter";
    }

    @GetMapping(value = "/delete/parameter/{id}")
    public String DeleteParameter(@PathVariable("id") Long id){
        parametersServices.DeleteParameter(id);
        return "redirect:/parameter";
    }

    @GetMapping(value = "/edit/parameter/{id}")
    public String EditParameter(@PathVariable("id") Long id,Model model){
        model.addAttribute("parameter", parametersServices.FindParameter(id));
        return "Parameter/EditParameter";
    }
    @RequestMapping(value = "/update/parameter/{id}", method= RequestMethod.POST)
    public String UpdateEstate(@PathVariable("id") Long id,@ModelAttribute Parameters parameters, @RequestParam("value") Long value) {

        Parameters parametersModel=parametersServices.FindParameter(id);
        parametersModel.setvalue(value);
        parametersModel.setName(parameters.getName());

        parametersServices.SaveParameter(parametersModel);
        return "redirect:/parameter";
    }



}
