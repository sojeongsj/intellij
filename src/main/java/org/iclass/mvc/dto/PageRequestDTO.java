package org.iclass.mvc.dto;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;


import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
public class PageRequestDTO {
	//page, size는 start, end 계산할때 필요한 값
	private int page =1;
	private int size=5;      //size








	// 는 한 개 페이지 글 갯수

	//sql 쿼리에 필요한 값
	private int start;		//페이지 시작 글번호 rownum
	private int end;		//페이지 마지막 글번호 rownum


	//검색 파라미터
	private String[] types;		//"twc"를 동적 쿼리로 전달하기 위해 배열로 변환 하여 저장 {"t","w","c"}
	private String type;		//view 에서는 "twc"로 전달되는 값 저장
	private String keyword;
	@DateTimeFormat(pattern = "yyyy-MM-dd")	private LocalDate from;
	@DateTimeFormat(pattern = "yyyy-MM-dd")	private LocalDate to;
	
	//int page,int size,String[] types,String keyword,LocalDate from,LocalDate to 는 list.html에서 검색 필드로 전달 해줄 파라미터들
	//list.html에서 검색버튼을 누르면 /community/list getmapping. 해당 핸들러 메소드에 인자로 PageRequestDTO 선언
	//핸들러 메소드는 PageRequestDTO로 모든 파라미터를 받음(생성자 + setter 동작)

	public void setDatas(){			//오라클에서만 필요. mysql은 쉽게 할수있는 limit 연산자 존재
		start = (page-1)* size+1;   //글목록 시작행번호(rownum)
		end = start + (size-1);

		//String "tc"와 같이 view에서 받은 파라미터값은 배열로 변경
		if(type != null && !type.isEmpty() && !type.equals("a")) {	//type="a"를 전체로 할 예정
			types = type.split("");
			//"tc"를 new String[]{"t","c"}로 변환
		}
	}

	//글 읽기, 글 수정, 글 삭제 등에 페이지번호와 검색 파라미터를 url에 계속 데리고 다녀야 함
	//이를 위해 문자열 생성 메소드 정의
	private String link;	//url에 들어갈 파라미터 문자열

	public String getLink(){
		if(link==null){
			StringBuilder builder = new StringBuilder();
			builder.append("page="+this.page);
			if(type != null && type.length()>0 && keyword!=null){
				builder.append("&type="+type);
				try {
					builder.append("&keyword="+ URLEncoder.encode(keyword,"UTF-8"));
					//키워드는 한글 등 다국어문자일 경우 인코딩이 필요함
				} catch (UnsupportedEncodingException e){

				}
			}

			if (from != null && to !=null){	//아직 UI에는 미구현한 상태
				builder.append("&from="+from);
				builder.append("&to="+to);
			}
			this.link= builder.toString();
		}
		return this.link;
		//최종 link는 ex) page=3&type=tc&keyword=hi&from=2023-03-01&to=2023-03-31
	}

}
