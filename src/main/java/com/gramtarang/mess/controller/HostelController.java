package com.gramtarang.mess.controller;

import com.gramtarang.mess.entity.Hostel;
import com.gramtarang.mess.service.HostelService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/hostel")
public class HostelController {
    private HostelService hostelService;

    public HostelController(HostelService hostelService) {
        this.hostelService = hostelService;
    }

    @PostMapping("/getAll")
    public @ResponseBody
    List<Hostel> getAllHostels(HttpServletRequest request)
    {
        return hostelService.getAll();
    }

    @PostMapping("/add")
    public @ResponseBody
    Hostel addHostel(@RequestBody Hostel hostel, HttpServletRequest request)
    {
        return hostelService.add(hostel);
    }

    @PostMapping("/delete")
    public @ResponseBody
    String deleteHostel(@RequestParam(value = "hostel_id") Integer hostel_id, HttpServletRequest request)
    {
        hostelService.delete(hostel_id);
        return "DELETE SUCCESSFULLY";
    }

}
