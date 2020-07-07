package api.restful.controller.security;

public class SecurityConstants {
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 864_000_000;
    public static final String HEADER_STRING = "token";
    public static final String SIGN_UP_URL = "/users/sign-up";
}