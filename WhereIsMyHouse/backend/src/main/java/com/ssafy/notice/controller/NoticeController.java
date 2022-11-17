package com.ssafy.notice.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.member.model.dto.MemberDto;
import com.ssafy.member.model.service.MemberService;
import com.ssafy.notice.model.dto.NoticeDto;
import com.ssafy.notice.model.service.NoticeService;

@RestController
@RequestMapping("/rest/notice")
@CrossOrigin("*")
public class NoticeController {

	@Autowired
    private NoticeService noticeService;
	
	@Autowired
	private MemberService memberService;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
	

	@GetMapping("/list")
    private ResponseEntity<?> list(@RequestParam(value="page",required=false)Integer page, @RequestParam(value="keyword",required=false)String keyword) throws SQLException {
    	if(page==null) {
    		page=1;
    	}
    	
        Map<String,Object> map = new HashMap<String,Object>();
    	List<NoticeDto> list = noticeService.list(page, keyword);

        map.put("list", list);

        List<Integer> pages = noticeService.pages(page, keyword);
        map.put("page", pages);

        return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
    }

    
    @PostMapping("/write")
    private ResponseEntity<?> write(@RequestBody Map<String,String> param, HttpServletRequest req){
        boolean check = false;
        Map<String,Object> map = new HashMap<String,Object>();

        String userId = param.get("memberId");
        
        MemberDto memberDto=null;
		try {
			memberDto = (MemberDto) memberService.getMember(userId);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
        if (memberDto != null) {

            try {

                String title = param.get("title");
                String content = param.get("content");

                NoticeDto noticeDto = new NoticeDto();
                noticeDto.setAuthor(memberDto.getId());
                noticeDto.setTitle(title);
                noticeDto.setContent(content);
                
                noticeService.write(noticeDto);
                check = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        map.put("check", check);

        return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
    }

    @GetMapping("/detail")
    private ResponseEntity<?> detail(@RequestParam("no")String no, HttpServletRequest req) throws SQLException {
        NoticeDto noticeDto = noticeService.detail(no);
        noticeService.hit(no);
        Map<String,Object> map = new HashMap<String,Object>();
        
        HttpSession session = req.getSession();
        MemberDto memberDto = (MemberDto) session.getAttribute("userInfo");

        boolean checkUser = false;
        if(memberDto != null && memberDto.getId().equals(noticeDto.getAuthor())) {
            checkUser = true;
        }

        map.put("checkUser",checkUser);
        map.put("notice", noticeDto);

        return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
    }

    @PostMapping("/modify")
    private ResponseEntity<?> modify(@RequestBody Map<String,String> param, HttpServletRequest req) {
        boolean check = false;
        Map<String,Object> map = new HashMap<String,Object>();
        
        String userId = param.get("memberId");
        
        
        MemberDto memberDto=null;
		try {
			memberDto = (MemberDto) memberService.getMember(userId);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
        if (memberDto != null) {
            try {

                String no = param.get("no");
                String title = param.get("title");
                String content = param.get("content");

                NoticeDto noticeDto = new NoticeDto();
                noticeDto.setNo(no);
                noticeDto.setTitle(title);
                noticeDto.setContent(content);

                noticeService.modify(noticeDto); //공지글 수정
                check = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        map.put("check", check);

        return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
    }
    
    @DeleteMapping("/delete")
    private ResponseEntity<?> delete(@RequestParam("no")String no) {
        boolean check = false;
        Map<String,Object> map = new HashMap<String,Object>();
        
        try {
            noticeService.delete(no);
            check = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        map.put("check",check);

        return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
    }
}   