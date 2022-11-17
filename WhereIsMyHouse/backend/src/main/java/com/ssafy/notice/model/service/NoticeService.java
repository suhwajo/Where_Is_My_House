package com.ssafy.notice.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.notice.model.dto.NoticeDto;

public interface NoticeService {

	int delete(String no) throws SQLException;

	int modify(NoticeDto noticeDto) throws SQLException;

	NoticeDto detail(String no) throws SQLException;

	int write(NoticeDto noticeDto) throws SQLException;

	List<NoticeDto> list(int page, String keyword) throws SQLException;

	void hit(String no) throws SQLException;

    List<Integer> pages(int page, String keyword) throws SQLException;
}
