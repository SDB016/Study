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

    @GetMapping("/member/id")
    public findMemberIdResponse findMemberIdByName(@RequestBody @Valid findMemberIdRequest request){

        String id = memberService.findIdByName(request.getName());
        return new findMemberIdResponse(id);
    }

    @GetMapping("/member/pwd")
    public findMemberPwdResponse findMemberPwdByNameId(@RequestBody @Valid findMemberPwdRequest request){

        String pwd = memberService.findPwdByNameId(request.name, request.id);
        return new findMemberPwdResponse(pwd);
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
        private String userId;
    }

    @Data
    static class findMemberIdRequest{
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class findMemberPwdResponse{
        private String password;
    }

    @Data
    static class findMemberPwdRequest{

        private String name;
        private String id;
    }
}
