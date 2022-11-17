package com.ssafy.board.model.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.board.model.dto.BoardDto;
import com.ssafy.board.model.mapper.BoardMapper;
import com.ssafy.util.SizeConstant;

@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private BoardMapper boardDao;
	
	@Override
	public int delete(String no) throws SQLException {
		return boardDao.deleteByNo(no);
	}

	@Override
	public int modify(BoardDto boardDto) throws SQLException {
		return boardDao.update(boardDto);
	}

	@Override
	public BoardDto detail(String no) throws SQLException {
		return boardDao.selectByNo(no);
	}

	@Override
	public int write(BoardDto boardDto) throws SQLException {
		return boardDao.save(boardDto);
	}

	@Override
	public List<BoardDto> list(int page, String keyword) throws SQLException {
		int spl = SizeConstant.SIZE_PER_LIST;  // size per page
		int start = (page - 1) * spl;
		
		Map<String, Object> map =new HashMap<>();
		map.put("start", start);
		map.put("spl",spl);
		map.put("keyword",keyword);
		
		return boardDao.selectList(map);
	}

	@Override
	public void hit(String no) throws SQLException {
		boardDao.updateHit(no);
	}

	@Override
	public List<Integer> pages(int page, String keyword) throws SQLException {
		List<Integer> pages = new ArrayList<>();
		if(keyword=="") {
			keyword=null;
		}
		
		for(int i = page-2; i <= page+2; i++) {

			if(i < 1)
				continue;

			int spl = SizeConstant.SIZE_PER_LIST;  // size per page
			int start = (i - 1) * spl;
			
			Map<String, Object> map =new HashMap<>();
			map.put("start", start);
			map.put("spl",spl);
			map.put("keyword",keyword);
			
			Integer total = boardDao.isEffectivePage(map);
			
			if(total!=null && total>0)pages.add(i);
			else break;
		}

		return pages;
	}
	
}
