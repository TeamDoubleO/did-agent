package com.doubleo.didagent.controller;

import com.doubleo.didagent.dto.request.poll.HospitalInvitationInfoRequest;
import com.doubleo.didagent.dto.response.poll.InvitationInfoResponse;
import com.doubleo.didagent.service.MemberPollService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/polls")
@RequiredArgsConstructor
@Slf4j
public class MemberPollController {

    private final MemberPollService memberPollService;

    @GetMapping("/mediator-invitation")
    public InvitationInfoResponse getMediatorInvitationInfo() {
        return memberPollService.getMediatorInvitation();
    }

    @PostMapping("/hospital-invitation")
    public InvitationInfoResponse getHospitalInvitationInfo(
            @RequestBody HospitalInvitationInfoRequest request) {
        return memberPollService.getHospitalInvitation(request);
    }
}
