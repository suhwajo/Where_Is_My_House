package com.ssafy.comment.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.ssafy.comment.model.dto.CommentDto;
import com.ssafy.comment.model.service.CommentService;
import com.ssafy.member.model.dto.MemberDto;
import com.ssafy.member.model.service.MemberService;

@RestController
@RequestMapping("/rest/comment")
@CrossOrigin("*")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/list")
	private ResponseEntity<?> list(@RequestParam("no")Integer no){
		List<CommentDto> comments = new ArrayList<>();
		try {
			comments = commentService.getComments(no);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("comments", comments);
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
	}
	
	@GetMapping("/detail")
	private ResponseEntity<?> detail(@RequestParam("id")String id){
		CommentDto commentDto;
		try {
			commentDto = commentService.detail(id);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
        Map<String,Object> map = new HashMap<String,Object>();
        
        map.put("comment",commentDto);
        return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
	}
	
	@PostMapping("/write")
	private ResponseEntity<?> write(@RequestBody Map<String,String> param) {
		boolean check = false;
		Map<String, Object> map = new HashMap<String, Object>();
		
		String userId = param.get("memberId");
		
		MemberDto memberDto = null;
		try {
			memberDto = (MemberDto) memberService.getMember(userId);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		if(memberDto != null) {
			try {
				String content = param.get("content");
				String board_id = param.get("no");
				
				CommentDto commentDto = new CommentDto();
				commentDto.setAuthor(memberDto.getId());
				commentDto.setContent(content);
				commentDto.setBoardId(board_id);
				
				commentService.write(commentDto);
				check=true;
			}
			catch(SQLException e) {
				e.printStackTrace();
				return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		map.put("check",check);
        return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
	}
	
	@PostMapping("/modify")
	private ResponseEntity<?> modify(@RequestBody Map<String,String> param) {
		boolean check = false;
		Map<String, Object> map = new HashMap<>();
		
		String userId = param.get("memberId");
		
		MemberDto memberDto = null;
		try {
			memberDto = (MemberDto) memberService.getMember(userId);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		if(memberDto != null) {
			try {
				String id = param.get("id");
				String content = param.get("content");
				
				int n = commentService.modify(memberDto.getId(), id, content);
				if(n>0) check=true;
			}catch(SQLException e) {
				e.printStackTrace();
				return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		map.put("check", check);
		
        return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
	}
	
	@DeleteMapping("/delete")
	private ResponseEntity<?> delete(@RequestBody Map<String, String> param) {
		boolean check = false;
		Map<String,Object> map = new HashMap<String,Object>();
		String userId = param.get("memberId");
		
		MemberDto memberDto = null;
		try {
			memberDto = (MemberDto) memberService.getMember(userId);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		if(memberDto != null) {
			try {
				String id = param.get("id");
				CommentDto comment = commentService.detail(id);
				
				if(comment.getAuthor().equals(memberDto.getId())) {
					commentService.delete(id);
					check= true;
				}
			}catch(SQLException e) {
				e.printStackTrace();
				return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		map.put("check", check);
		
        return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
	}
}
