package fr.madera.madera_backend.security;

public class SecurityConstants {
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/user/sign-up";
    public static final String FILMS_URL = "/films";
    public static final String SAVE_COMMANDE = "/commande";
    public static final String SAVE_SIEGE_RESA = "/siege/sieges";
    public static final String ROLE_URL = "/role";

}