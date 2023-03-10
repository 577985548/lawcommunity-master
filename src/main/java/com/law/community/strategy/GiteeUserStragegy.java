package com.law.community.strategy;

import com.law.community.dto.AccessTokenDTO;
import com.law.community.provider.GiteeProvider;
import com.law.community.provider.dto.GiteeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GiteeUserStragegy implements UserStrategy {
    @Autowired
    private GiteeProvider giteeProvider;

    @Override
    public LoginUserInfo getUser(String code, String state) {
        AccessTokenDTO accessTokenDto = new AccessTokenDTO();
        accessTokenDto.setCode(code);
        accessTokenDto.setState(state);
        String accessToken = giteeProvider.getAccessToken(accessTokenDto);
        GiteeUser giteeUser = giteeProvider.getUser(accessToken);
        LoginUserInfo loginUserInfo = new LoginUserInfo();
        loginUserInfo.setName(giteeUser.getName());
        loginUserInfo.setAvatar_url(giteeUser.getAvatar_url());
        loginUserInfo.setBio(giteeUser.getBio());
        loginUserInfo.setId(giteeUser.getId());
        return loginUserInfo;
    }

    @Override
    public String getSupportedType() {
        return "gitee";
    }
}
