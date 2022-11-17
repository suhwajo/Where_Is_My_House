package com.ssafy.member.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.member.model.dto.MemberDto;

public interface MemberService {
	int idCheck(String id) throws SQLException;
	void join(MemberDto memberDto) throws SQLException;
	MemberDto loginMember(String id, String pwd) throws Exception;
	String findMember(String name, String id, String phone) throws SQLException;
	MemberDto getMember(String id) throws SQLException;
	void modifyMember(MemberDto sendDto) throws SQLException;
	void userDelete(String id) throws SQLException, Exception;

	List<MemberDto> getMembers(/* int page, String keyword */) throws SQLException;

}
