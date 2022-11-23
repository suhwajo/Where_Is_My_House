package com.ssafy.member.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.member.model.dto.MemberDto;

public interface MemberMapper {
	int idCheck(String id) throws SQLException;

	void insert(MemberDto memberDto) throws SQLException;

	MemberDto select(String id, String pwd) throws SQLException;

	String findMember(String name, String id, String phone) throws SQLException;

	MemberDto getMember(String id) throws SQLException;

	void modifyMember(MemberDto memberDto) throws SQLException;

	void deleteMember(String id) throws SQLException;

	List<MemberDto> selectMember(Map<String,Object> map) throws SQLException;
}
