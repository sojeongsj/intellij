package org.iclass.mvc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iclass.mvc.dao.CommunityCommentsMapper;
import org.iclass.mvc.dao.CommunityMapper;
import org.iclass.mvc.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommunityService {
	//컨트롤러는 요청에 대한 view를 담당. 비즈니스 로직은 서비스에서 처리하도록 함(개발자의 주요 처리내용)
	//서비스는 특정(단위) 기능을 중심을 메소드를 정의. dao는 하나의 sql로 만들어지는 메소드.
	private final CommunityMapper dao;
	private final CommunityCommentsMapper cmtdao;
	
	public List<Community> pagelist(PageRequestDTO pageRequestDTO){

		pageRequestDTO.setSize(5);
		pageRequestDTO.setDatas();
		List<Community> list = dao.pagelist(pageRequestDTO);	//글 목록


		//페이지 목록 구현은 예정
		return list;
	}

	//페이지 목록과 글 목록을 저장하는 DTO를 리턴타입으로 함
	public PageResponseDTO listWithSearch(PageRequestDTO pageRequestDTO){
		pageRequestDTO.setSize(5);
		pageRequestDTO.setDatas();
		List<Community> list = dao.pagelist(pageRequestDTO);	//글 목록
		PageResponseDTO pageResponseDTO = PageResponseDTO.of(pageRequestDTO,dao.count(pageRequestDTO),5);
		pageResponseDTO.setList(list);
		return pageResponseDTO;
	}

	//글 읽기
	public Community read(long idx) {
		dao.setReadCount(idx);		//조회수 증가
		return dao.selectByIdx(idx);	//PK로 조회
		
	}
	
	//글 수정
	public Community selectByIdx(long idx) {
		return dao.selectByIdx(idx);
	}
	//글 쓰기
	public int insert(Community vo) {
		return dao.insert(vo);
	}

	
	public int delete(long idx) {	//Integer, Long : 래퍼(wrapper) 클래스
		return dao.delete(idx);
	}

	public int update(Community vo) {
		return dao.update(vo);
	}
	
	@Transactional		//트랜잭션 처리를 위한 애노테이션-2개의 SQL이 하나의 처리 단위
	public void comments(CommunityComments dto, int f) {
		if(f==1) {			//댓글 등록
			cmtdao.insert(dto);
			cmtdao.setCommentCount(dto.getMref());		//댓글 개수 업데이트
		} else if(f==2) {	//댓글 삭제
			cmtdao.delete(dto.getIdx());
			cmtdao.setCommentCount(dto.getMref());		//댓글 개수 업데이트
		}
	}
	
	public List<CommunityComments> commentsList(long idx) {
		return cmtdao.commentsList(idx);
	}




}
