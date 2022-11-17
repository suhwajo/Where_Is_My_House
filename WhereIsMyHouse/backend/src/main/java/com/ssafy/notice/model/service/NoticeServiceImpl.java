package com.ssafy.notice.model.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.notice.model.dto.NoticeDto;
import com.ssafy.notice.model.mapper.NoticeMapper;
import com.ssafy.util.SizeConstant;

@Service
public class NoticeServiceImpl implements NoticeService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
	@Autowired
	private NoticeMapper noticeDao;
	

	public int delete(String no) throws SQLException {
		return noticeDao.deleteByNo(no);
	}

	public int modify(NoticeDto noticeDto) throws SQLException {
		return noticeDao.update(noticeDto);
	}

	public NoticeDto detail(String no) throws SQLException {
		return noticeDao.selectByNo(no);
	}

	public int write(NoticeDto noticeDto) throws SQLException {
		return noticeDao.save(noticeDto);
	}

	public List<NoticeDto> list(int page, String keyword) throws SQLException{
		int spl = SizeConstant.SIZE_PER_LIST;  // size per page
		int start = (page - 1) * spl;
		
		Map<String, Object> map =new HashMap<>();
		map.put("start", start);
		map.put("spl",spl);
		map.put("keyword",keyword);
		
		return noticeDao.selectList(map);
	}

	public void hit(String no) throws SQLException {
		noticeDao.updateHit(no);
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
			
			Integer total = noticeDao.isEffectivePage(map);
			if(total!=null && total>0) {
				pages.add(i);
			}
			else break;
		}
		
		
		return pages;
	}

}
