package com.medinote.medinotebackspring.oauth.info;

import java.util.Map;

public abstract class OAuth2UserInfo {
    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getId();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getImageUrl();

    @Override
    public String toString() {
        return "OAuth2UserInfo{" +
                "attributes=" + attributes +
                '}';
    }
}
