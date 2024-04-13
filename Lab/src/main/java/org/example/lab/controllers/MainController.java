package org.example.lab.controllers;

import org.example.lab.model.HryvniaExchangeItem;
import org.example.lab.repo.HryvniaExchangeItemRepo;
import org.example.lab.service.ExcelService;
import org.example.lab.service.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

@Controller
public class MainController {
    @Autowired
    private HryvniaExchangeItemRepo userRepo;
    private Parser parser = new Parser();

    @GetMapping("/")
    public String GetIndex(Model model) throws ParseException, IOException {
        var list = parser.Parse();
        userRepo.deleteAll();
        userRepo.saveAll(list);

        ArrayList<HryvniaExchangeItem> exchangeRates = new ArrayList<HryvniaExchangeItem>();
        exchangeRates= (ArrayList<HryvniaExchangeItem>) userRepo.findAll();
        model.addAttribute("exchangeRates", exchangeRates);
        return "index";
    }

    @GetMapping(value = "/delete/{id}")
    public String Delete(@PathVariable("id") int id, Model model){
        userRepo.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/register")
    public String GetAddUser(Model model){
        return "Register";
    }

    @PostMapping("/register")
    public String UserAdd(@RequestParam("DigitalCode") String digitalCode,
                          @RequestParam("LetterCode") String letterCode,
                          @RequestParam("Name") String name,
                          @RequestParam("OfficialCourse") String officialCourse,
                          Model model){
        userRepo.save(new HryvniaExchangeItem(0,digitalCode,letterCode,name,officialCourse));
        return "redirect:/";
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> getExcelFile() {
        try {
            byte[] createdFile = ExcelService.createExcelFile((ArrayList<HryvniaExchangeItem>) userRepo.findAll());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "exchangeRates.xlsx");
            return ResponseEntity.ok().headers(headers).body(createdFile);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
