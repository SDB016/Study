package jpabook.jpashop_clone.controller;

import jpabook.jpashop_clone.domain.Address;
import jpabook.jpashop_clone.domain.Member;
import jpabook.jpashop_clone.service.MemberService;
import jpabook.jpashop_clone.web.MemberForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = "/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping(value = "/members/new")
    public String create(@Valid MemberForm form, BindingResult result){

        if(result.hasErrors()){
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping(value = "/members")
    public String list(Model model) {
        List<Member> members = memberService.findAll();
        model.addAttribute("members", members);
        return "members/memberList";
    }

    /**
     * 회원 수정 폼
     */
    @GetMapping(value = "/members/{memberId}/edit")
    public String updateMemberForm(@PathVariable("memberId") Long memberId, Model model) {

        Member member = memberService.findOne(memberId);

        MemberForm form = new MemberForm();
        form.setId(member.getId());
        form.setName(member.getName());
        form.setCity(member.getAddress().getCity());
        form.setStreet(member.getAddress().getStreet());
        form.setZipcode(member.getAddress().getZipcode());

        model.addAttribute("form", form);
        return "members/updateMemberForm";
    }

    /**
     * 회원 수정
     */
    @PostMapping(value = "members/{memberId}/edit")
    public String updateMember(@PathVariable(name = "memberId") Long memberId, @ModelAttribute("form") MemberForm form) {

        memberService.updateMember(memberId, form.getName(), form.getCity(), form.getStreet(), form.getZipcode());
        return "redirect:/members";
    }
}
