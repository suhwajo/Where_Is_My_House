package com.ssafy.board.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import com.ssafy.board.model.dto.BoardDto;
import com.ssafy.board.model.service.BoardService;
import com.ssafy.comment.model.dto.CommentDto;
import com.ssafy.comment.model.service.CommentService;
import com.ssafy.member.model.dto.MemberDto;
import com.ssafy.member.model.service.MemberService;
import com.ssafy.notice.model.dto.NoticeDto;

@RestController
@RequestMapping("/rest/board")
@CrossOrigin("*")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private CommentService commentService;
	
	@GetMapping("/list")
    private ResponseEntity<?> list(@RequestParam(value="page",required=false)Integer page, @RequestParam(value="keyword",required=false)String keyword) throws SQLException {
    	if(page==null) {
    		page=1;
    	}
        Map<String,Object> map = new HashMap<String,Object>();
    	List<BoardDto> list = boardService.list(page, keyword);

        map.put("list", list);

        List<Integer> pages = boardService.pages(page, keyword);
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

                BoardDto boardDto = new BoardDto();
                boardDto.setAuthor(memberDto.getId());
                boardDto.setTitle(title);
                boardDto.setContent(content);
                
                boardService.write(boardDto);
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
        BoardDto boardDto = boardService.detail(no);
        List<CommentDto> comments = commentService.getComments(Integer.parseInt(no));
        boardService.hit(no);
        Map<String,Object> map = new HashMap<String,Object>();
        
        HttpSession session = req.getSession();
        MemberDto memberDto = (MemberDto) session.getAttribute("userInfo");

        boolean checkUser = false;
        if(memberDto != null && memberDto.getId().equals(boardDto.getAuthor())) {
            checkUser = true;
        }

        map.put("checkUser",checkUser);
        map.put("board", boardDto);
        map.put("comments",comments);
        
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

                BoardDto boardDto = new BoardDto();
                boardDto.setNo(no);
                boardDto.setTitle(title);
                boardDto.setContent(content);

                boardService.modify(boardDto); //공지글 수정
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
	            boardService.delete(no);
	            check = true;
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        map.put("check",check);

	        return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
	    }
	
}
