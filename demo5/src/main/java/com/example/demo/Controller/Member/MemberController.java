package com.example.demo.Controller.Member;

import com.example.demo.DTO.Member.MemberDTO;
import com.example.demo.Service.Member.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;


    //전체조회 페이지 이동
    @GetMapping("/list")
    public String list(Model model){
        List<MemberDTO> memberDTOS = memberService.findAll();
        model.addAttribute("memberDTOS", memberDTOS);
        return "member/list";
    }

    //개별조회 페이지 이동
    @GetMapping("/detail")
    public String detail(String userid, Model model){
        MemberDTO memberDTO = memberService.findByUserId(userid);
        model.addAttribute("memberDTO", memberDTO);
        return "member/detail";
    }

    //등록 페이지 이동
    @GetMapping("/register")
    public String register(Model model){
        MemberDTO memberDTO = new MemberDTO();
        model.addAttribute("memberDTO", memberDTO);
        return "member/register";
    }

    //등록 처리
    @PostMapping("/register")
    public String register(MemberDTO memberDTO){
        memberService.insert(memberDTO);
        return "redirect:/member/list";
    }

    //수정 페이지 이동
    @GetMapping("/update")
    public String update( Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        String userid = (String) session.getAttribute("userid");
        MemberDTO memberDTO = memberService.findByUserId(userid);
        model.addAttribute("memberDTO", memberDTO);
        return "member/update";
    }

    //관리자 수정 페이지
    @GetMapping("/admin/update")
    public String updateAdmin(String userid, Model model){
        MemberDTO memberDTO = memberService.findByUserId(userid);
        model.addAttribute("memberDTO", memberDTO);
        return "member/updateAdmin";
    }

    //개인이 자신의 정보를 수정처리
    @PostMapping("/update")
    public String updateProc(MemberDTO memberDTO){
        memberService.update(memberDTO);
        return "redirect:/logout";
    }

    //관리자 쪽에서 수정처리
    @PostMapping("/admin/update")
    public String updateAdminProc(MemberDTO memberDTO){
        memberService.update(memberDTO);
        return "redirect:/member/list";
    }

    //삭제처리 (회원은 삭제->탈퇴신청, 관리자만 삭제)
    //
    @GetMapping("/admin/delete")
    public String delete(String userid) {
        memberService.deleteByUserid(userid);
        return "redirect:/member/list";
    }

}
