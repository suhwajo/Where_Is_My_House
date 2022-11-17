package com.ssafy.notice.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.notice.model.dto.NoticeDto;

public interface NoticeMapper {

	List<NoticeDto> selectList(Map<String,Object> map) throws SQLException;

	int save(NoticeDto noticeDto) throws SQLException;

	NoticeDto selectByNo(String no) throws SQLException;

	int update(NoticeDto noticeDto) throws SQLException;

	int deleteByNo(String no) throws SQLException;

	void updateHit(String no) throws SQLException;

	//int start, int spl, String keyword
	Integer isEffectivePage(Map<String,Object> map) throws SQLException;
}
