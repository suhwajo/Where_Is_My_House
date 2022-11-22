package com.ssafy.member.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.ssafy.member.model.dto.MemberDto;
import com.ssafy.member.model.service.MemberService;
import com.ssafy.member.model.service.MemberServiceImpl;
import com.ssafy.util.ParameterCheck;

import lombok.AllArgsConstructor;
@RestController
@RequestMapping("/rest/user")
@CrossOrigin("*")
@AllArgsConstructor
public class MemberController {

    private MemberService memberService;

    @GetMapping("/getUser")
    public ResponseEntity<?> getUser(@RequestParam("userId") String userId){
    	try {
			MemberDto member = memberService.getMember(userId);
			if(member!=null) {
				return new ResponseEntity<MemberDto>(member, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (SQLException e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody Map<String, String> param){
        boolean check = false;

    	try {
    		MemberDto member = new MemberDto();
    		member.setPwd(param.get("pwd"));
    		member.setName(param.get("name"));
    		member.setEmail(param.get("email"));
    		member.setPhoneNum(param.get("phone"));
    		member.setId(param.get("id"));
    		memberService.modifyMember(member);
            check = true;

    	} catch(Exception e) {
    		return new ResponseEntity<Void> (HttpStatus.INTERNAL_SERVER_ERROR);
    	}
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("check", check);
		return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    private ResponseEntity<?> deleteUser(@RequestParam("userId") String userId) {

        boolean check = false;

        try {

            memberService.userDelete(userId);
            check = true;

        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("check", check);
		return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
     }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> param, HttpServletRequest req, HttpServletResponse resp){
    	try {
    		String id = param.get("userId");
    		String pwd = param.get("userPwd");
    		String saveid = param.get("saveId");
    		MemberDto memberDto = memberService.loginMember(id, pwd);
    		
    		if(memberDto!=null) {
    			if(saveid.equals("true")) {
                    Cookie cookie = new Cookie("saveId", id);
                    cookie.setMaxAge(60 * 60 * 24 * 2);
                    cookie.setPath(req.getContextPath());
                    resp.addCookie(cookie);
    			} else {
                    Cookie[] cookies = req.getCookies();
                    if (cookies != null) {
                        for (Cookie cookie : cookies) {
                            if (cookie.getName().equals("saveId")) {
                                cookie.setMaxAge(0);
                                cookie.setPath(req.getContextPath());
                                resp.addCookie(cookie);
                                break;
                            }
                        }
                    }
                }
                HttpSession session = req.getSession();
                session.setAttribute("userInfo", memberDto);
                
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("userInfo", memberDto);
    			return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
    		} else {
    			return new ResponseEntity<Void>(HttpStatus.OK);
    		}
    	} catch(Exception e) {
    		e.printStackTrace();
    		return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    }

    @GetMapping("/saveId")
    private ResponseEntity<?> getSaveId(HttpServletRequest req) {
        String saveId = "";

        Cookie[] cookies = req.getCookies();

        for (Cookie cookie : cookies) {
            if ("saveId".equals(cookie.getName())) {
                saveId = cookie.getValue();
                break;
            }
        }

        Map<String, String> map = new HashMap<String, String>();
        map.put("saveId", saveId);
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
    }

    @GetMapping("/isLogin")
    public ResponseEntity<?> isLogin(HttpServletRequest req){
        HttpSession session = req.getSession();
        
        MemberDto memberDto = (MemberDto) session.getAttribute("userInfo");

        if(memberDto==null) {
        	return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<MemberDto>(memberDto, HttpStatus.OK);
    }

    @GetMapping("/idCheck")
    private ResponseEntity<?> idCheck(HttpServletRequest req) {
        String id = req.getParameter("userId");
        int cnt = 1;
        try {
            cnt = memberService.idCheck(id);
        } catch (SQLException e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("cnt", cnt);
		return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);

    }

    @PostMapping("/join")
    private ResponseEntity<?> join(@RequestBody Map<String, String> param ,HttpServletRequest req) {

        boolean check = false;
        try {
        	
            MemberDto memberDto = new MemberDto();
            memberDto.setId(param.get("id"));
            memberDto.setPwd(param.get("pwd"));
            memberDto.setEmail(param.get("email"));
            memberDto.setName(param.get("name"));
            memberDto.setPhoneNum(param.get("phone"));

            memberService.join(memberDto);

            check = true;
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("check", check);
		return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
    }

    @GetMapping("/findPw")
    private ResponseEntity<?> findPw(@RequestParam("id") String id, @RequestParam("name") String name, @RequestParam("phone") String phone) {

        String pwd = "";
        try {
            pwd = memberService.findMember(name, id, phone);
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pwd", pwd);
		return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
    }

    @GetMapping("/logout")
    private ResponseEntity<?> logout(HttpServletRequest req) {

        HttpSession session = req.getSession();
        session.removeAttribute("userInfo");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("check", true);
		return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
    }

    @GetMapping("/list")
    private ResponseEntity<?> list() throws SQLException {

        List<MemberDto> sendMembers = memberService.getMembers();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", sendMembers);
        
        return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
    }
}
