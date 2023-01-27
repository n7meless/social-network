package dev.socialnetwork.jwt;

import lombok.Getter;

public class Login {
    @Getter
    private final Jwt accessToken;
    @Getter
    private final Jwt refreshToken;

    private static final Long ACCESS_TOKEN_VALIDITY=1L;
    private static final Long REFRESH_TOKEN_VALIDITY=1440L;

    public Login(Jwt accessToken, Jwt refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static Login of(Long accountId, String accessSecret,Long accessTokenValidity, String refreshSecret, Long refreshTokenValidity) {
        return new Login(
                Jwt.of(accountId, accessTokenValidity, accessSecret),
                Jwt.of(accountId, refreshTokenValidity, refreshSecret) //1440L - one day
        );
    }
    public static Login of(Long accountId,String accessSecret, Long accessTokenValidity , Jwt refreshToken){
        return new Login(
                Jwt.of(accountId, accessTokenValidity, accessSecret),
                refreshToken
        );
    }
}
