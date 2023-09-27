package org.iclass.mvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.iclass.mvc.dto.Community;
import org.iclass.mvc.dto.CommunityComments;
import org.iclass.mvc.service.CommunityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/community")
@RequiredArgsConstructor
@Log4j2
public class CommunityController {

    private final CommunityService service;

    @GetMapping("/list")
    private void list(@RequestParam(defaultValue = "1")
                      int page, Model model) {
        model.addAttribute("list",service.pagelist(page).get("list"));
        model.addAttribute("paging",service.pagelist(page).get("paging"));
        model.addAttribute("today", LocalDate.now());
    }

    @PostMapping("/write")
    public String save(Community dto
            , RedirectAttributes reAttr
    ) {	//파라미터가 많을 때, 그것들을 필드로 갖는 dto 또는 map으로 전달받기
        log.info("dto : {}",dto);
        service.insert(dto);

        reAttr.addFlashAttribute("message","글 등록이 완료되었습니다.");
        return "redirect:/community/list";
    }

    @GetMapping("/write")
    public void write() {
        //글쓰기 GET 요청은 view name만 지정하고 끝
    }

    @GetMapping("/read")
    public void read(long idx, @ModelAttribute("page")
    int page, Model model) {
        model.addAttribute("vo",service.selectByIdx(idx));
        model.addAttribute("cmtlist",service.commentsList(idx));


    }

    @PostMapping("/comments")
    public String comments(int page, int f , CommunityComments dto,
                           RedirectAttributes redirectAttributes) {
        log.info(">>>>>>> dto : {}",dto);
        service.comments(dto,f);
        redirectAttributes.addAttribute("page",page);
        redirectAttributes.addAttribute("idx",dto.getMref());
        if(f==1) {
            redirectAttributes.addFlashAttribute("message","댓글 등록이 완료되었습니다.");
        }
        else if(f==2) {
            redirectAttributes.addFlashAttribute("message","댓글 삭제가 완료되었습니다.");
        }
        //return "redirect:/community/read?page="+page+"&idx="+dto.getMref();
        return "redirect:/community/read"; //리다이렉트 애트리뷰트 사용하므로 쿼리스트링 사용X
    }

}
