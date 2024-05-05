package com.medinote.medinotebackspring.oauth.entity;

import lombok.Getter;

@Getter
public enum ProviderType {
    GOOGLE,
    NAVER,
    KAKAO,
    LOCAL;
}