package com.ssafy.member.model.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.home.model.mapper.HomeMapper;
import com.ssafy.home.parser.HomeParser;
import com.ssafy.member.model.dto.MemberDto;
import com.ssafy.member.model.mapper.MemberMapper;
import com.ssafy.util.SizeConstant;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService{
	
	private MemberMapper memberDao;
	
	private static MemberService memberService;
	

	public static MemberService getMemberService() {
		return memberService;
	}

	public int idCheck(String id) throws SQLException {
		return memberDao.idCheck(id);
	}

	public void join(MemberDto memberDto) throws SQLException {
		memberDao.insert(memberDto);
		
	}

	public MemberDto loginMember(String id, String pwd) throws Exception {
		return memberDao.select(id, pwd);
	}

	public String findMember(String name, String id, String phone) throws SQLException {
		return memberDao.findMember(name, id, phone);
	}

	public MemberDto getMember(String id) throws SQLException {
		return memberDao.getMember(id);
	}

	public void modifyMember(MemberDto memberDto) throws SQLException {
		memberDao.modifyMember(memberDto);
		
	}

	public void userDelete(String id) throws Exception {
		memberDao.deleteMember(id);
		
	}

	public List<MemberDto> getMembers(/* int page, String keyword */) throws SQLException {
		
//		int spl = SizeConstant.SIZE_PER_LIST;  // size per page
//		int start = (page - 1) * spl;
//		Map<String, Object> map = new HashMap<>();
//		map.put("start",start);
//		map.put("spl",spl);
//		map.put("keyword",keyword);
		
		return memberDao.selectMember(/* map */);
	}
	
}
