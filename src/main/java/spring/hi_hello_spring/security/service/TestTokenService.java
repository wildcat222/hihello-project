//package spring.hi_hello_spring.security.service;
//
//public class TestTokenService {
//
//    @Value("${jwt.secret}")
//    private String secretKey;
//
//    @Value("${jwt.refresh-expiration}")
//    private long refreshExpiration;
//
//    private final JwtTokenProvider jwtTokenProvider;
//    private final Jedis redisClient;
//
//    public TokenService(JwtTokenProvider jwtTokenProvider, Jedis redisClient) {
//        this.jwtTokenProvider = jwtTokenProvider;
//        this.redisClient = redisClient;
//    }
//
//    /**
//     * 엑세스 토큰의 유효성을 검증합니다. 만약 토큰이 만료되었다면 리프레시 토큰을 처리합니다.
//     * 그렇지 않으면 Redis에서 토큰의 존재 및 무결성을 확인합니다.
//     */
//    public String handleAccessToken(String accessToken, String refreshToken) {
//        // Step 1: 엑세스 토큰 검증
//        if (!jwtTokenProvider.validateToken(accessToken)) {
//            if (jwtTokenProvider.isTokenExpired(accessToken)) {
//                // 엑세스 토큰이 만료된 경우, 리프레시 토큰 검증으로 이동
//                return handleRefreshToken(refreshToken);
//            } else {
//                throw new AccessDeniedException("유효하지 않은 엑세스 토큰입니다.");
//            }
//        }
//
//        // Step 5: Redis에서 엑세스 토큰 확인
//        String storedAccessToken = redisClient.get(getRedisKey(accessToken));
//        if (storedAccessToken == null) {
//            throw new AccessDeniedException("Redis에 엑세스 토큰이 없습니다.");
//        }
//
//        // 저장된 엑세스 토큰과 비교
//        if (!storedAccessToken.equals(accessToken)) {
//            throw new AccessDeniedException("엑세스 토큰이 일치하지 않습니다.");
//        }
//
//        return "엑세스 토큰이 유효합니다.";
//    }
//
//    /**
//     * 리프레시 토큰을 검증합니다. 유효한 경우, 토큰을 재발급합니다.
//     */
//    private String handleRefreshToken(String refreshToken) {
//        // Step 2: 리프레시 토큰 검증
//        if (!jwtTokenProvider.validateToken(refreshToken)) {
//            if (jwtTokenProvider.isTokenExpired(refreshToken)) {
//                // 리프레시 토큰이 만료된 경우, 토큰 무효화 후 예외 발생
//                invalidateToken(refreshToken);
//                throw new AccessDeniedException("리프레시 토큰이 만료되었습니다.");
//            } else {
//                throw new AccessDeniedException("유효하지 않은 리프레시 토큰입니다.");
//            }
//        }
//
//        // Step 3: Redis에서 리프레시 토큰 확인
//        String storedRefreshToken = redisClient.get(getRedisKey(refreshToken));
//        if (!StringUtils.hasText(storedRefreshToken)) {
//            throw new AccessDeniedException("Redis에 리프레시 토큰이 없습니다.");
//        }
//
//        // Step 4: 리프레시 토큰 비교
//        if (!storedRefreshToken.equals(refreshToken)) {
//            invalidateToken(refreshToken);
//            throw new AccessDeniedException("리프레시 토큰이 일치하지 않습니다.");
//        }
//
//        // Step 4-1: 토큰 재발급 (로테이션)
//        String newAccessToken = jwtTokenProvider.createAccessToken();
//        String newRefreshToken = jwtTokenProvider.createRefreshToken();
//
//        // 새 토큰을 Redis에 저장하고 만료 시간 설정
//        storeToken(newAccessToken, refreshExpiration);
//        storeToken(newRefreshToken, refreshExpiration);
//
//        return "엑세스 및 리프레시 토큰이 재발급되었습니다.";
//    }
//
//    /**
//     * Redis에서 토큰을 삭제합니다.
//     */
//    private void invalidateToken(String token) {
//        redisClient.del(getRedisKey(token));
//    }
//
//    /**
//     * Redis에 토큰을 저장하고 지정된 만료 시간을 설정합니다.
//     */
//    private void storeToken(String token, long expiration) {
//        redisClient.setex(getRedisKey(token), Duration.ofMillis(expiration).getSeconds(), token);
//    }
//
//    /**
//     * 토큰에서 추출한 subject를 사용하여 Redis 키를 생성합니다.
//     */
//    private String getRedisKey(String token) {
//        return "TOKEN:" + jwtTokenProvider.getTokenSubject(token);
//    }
//}
