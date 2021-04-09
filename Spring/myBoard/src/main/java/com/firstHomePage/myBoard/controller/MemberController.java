package com.firstHomePage.myBoard.controller;

import com.firstHomePage.myBoard.domain.Member;
import com.firstHomePage.myBoard.service.MemberService;
import com.firstHomePage.myBoard.web.MemberForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/member")
    public CreateMemberResponse join(@RequestBody @Valid CreateMemberRequest request){

        Member member = new Member(request.id, request.password, request.name, request.nickname, request.age);
        Long memberId = memberService.join(member);

        return new CreateMemberResponse(memberId);
    }

    @PatchMapping("/member/{id}")
    public String updateMember(
            @PathVariable Long id,
            @RequestBody @Valid UpdateMemberRequest request){

        memberService.update(id, request.id, request.password, request.nickname);

        return "Update Done!";
    }

    @GetMapping("/member/{memberId}")
    public findMemberIdResponse findMemberIDByName(@RequestBody @Valid findMemberIdRequest request){

        Member member = memberService.findOneByName(request.getName());
        return new findMemberIdResponse(member.getLoginId());
    }

    public void findMemberByIdPwd(){

    }

    @DeleteMapping("/member/{id}")
    public String deleteMember(@PathVariable Long id){

        Member member = memberService.findOne(id);
        memberService.delete(member);

        return "Delete Done!";
    }


    //=====static class========//
    @Data
    @AllArgsConstructor
    static class CreateMemberResponse{
        private Long id;
    }

    @Data
    static class CreateMemberRequest{
        private String id;
        private String password;
        private String name;
        private String nickname;
        private int age;
    }

    @Data
    static class UpdateMemberRequest{
        private String id;
        private String password;
        private String nickname;
    }

    @Data
    @AllArgsConstructor
    static class findMemberIdResponse{
        private String id;
    }

    @Data
    static class findMemberIdRequest{
        private String name;
    }
}
