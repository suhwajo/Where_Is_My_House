package com.ssafy.region.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.ssafy.region.model.service.RegionService;
import com.ssafy.region.model.service.RegionServiceImpl;

import lombok.AllArgsConstructor;

import com.ssafy.region.RegionProperty;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/rest/region")
@CrossOrigin("*")
public class RegionController{
	
	@Autowired
    private RegionService regionService;
	
	@Autowired
    private RegionProperty regionProperty;
    private Gson gson;
    
    private RegionController() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }


    @GetMapping("/list")
    private String list(@RequestParam("regionType") String regionType,@RequestParam("sortType") String sortType,
    		@RequestParam("sortMethod") String sortMethod,@RequestParam("keyword") String keyword, @RequestParam("page") String page,
    		HttpServletRequest req) throws SQLException {

 //       String regionType = req.getParameter("regionType");
//        String sortType = req.getParameter("sortType");
//        String sortMethod = req.getParameter("sortMethod");
//        String keyword = req.getParameter("keyword");
//        String page = req.getParameter("page");


        JsonObject json = new JsonObject();

        if(regionType.equals("1"))
            json.add("list", gson.toJsonTree(regionProperty.getSidoList(Integer.parseInt(sortType), sortMethod, keyword, Integer.parseInt(page))));
        else
            json.add("list",gson.toJsonTree(regionProperty.getGunguList(Integer.parseInt(sortType), sortMethod, keyword, Integer.parseInt(page))));

        return gson.toJson(json);
    }
}