package coloradohusky.rajavaintegration.network;

import coloradohusky.rajavaintegration.data.RA_Achievement;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.StringJoiner;

public class NetworkRequest {

    public static class RequestHeader {
        public String host = "";
        public int game = 0;
        public boolean hardcore = true;
        public String user = "";
        public String token = "";

        public RequestHeader(String host, int game, boolean hardcore, String user, String token) {
            this.host = host;
            this.game = game;
            this.hardcore = hardcore;
            this.user = user;
            this.token = token;
        }

        public RequestHeader() {

        }
    }

    public static URI buildLoginRequest(RequestHeader header, String pass) {
        return URI.create("https://" + header.host + "/dorequest.php?" +
                "u=" + encode(header.user) +
                "&r=login2" +
                "&p=" + encode(pass));
    }

    public static URI buildStartSessionRequest(RequestHeader header, int game) {
        return URI.create("https://" + header.host + "/dorequest.php?" +
                "u=" + encode(header.user) +
                "&t=" + encode(header.token) +
                "&r=startsession" +
                "&g=" + game);
    }

    public static URI buildPingRequest(RequestHeader header, String rp) {
        return URI.create("https://" + header.host + "/dorequest.php?" +
                "u=" + encode(header.user) +
                "&t=" + encode(header.token) +
                "&r=ping" +
                "&g=" + header.game);
    }

    public static URI buildAwardAchievementRequest(RequestHeader header, int ach) {
        return URI.create("https://" + header.host + "/dorequest.php?" +
                "u=" + encode(header.user) +
                "&t=" + encode(header.token) +
                "&r=awardachievement" +
                "&h=" + (header.hardcore ? "1" : "0") +
                "&a=" + ach +
                "&v=" + generateVerifyMD5(ach + header.user + header.hardcore + ach));
    }

    public static URI buildAwardAchievementsRequest(RequestHeader header, List<Integer> achs) {
        StringJoiner joiner = new StringJoiner(",");
        for (Integer ach : achs) {
            joiner.add(ach.toString());
        }

        return URI.create("https://" + header.host + "/dorequest.php?" +
                "u=" + encode(header.user) +
                "&t=" + encode(header.token) +
                "&r=awardachievements" +
                "&h=" + (header.hardcore ? "1" : "0") +
                "&a=" + joiner.toString() +
                "&v=" + generateVerifyMD5(achs.toString() + header.user + header.hardcore));
    }

    public static URI buildUploadAchievementRequest(RequestHeader header, RA_Achievement ach) {
        return URI.create("https://" + header.host + "/dorequest.php?" +
                "u=" + encode(header.user) +
                "&t=" + encode(header.token) +
                "&r=uploadachievement" +
                "&g=" + header.game +
                "&n=" + encode(ach.getTitle()) +
                "&d=" + encode(ach.getDescription()) +
                "&z=" + ach.getPoints() +
                "&x=" + encode(ach.getType()) +
                "&m=0=1" +
                "&f=" + ach.getCategory() +
                "&a=" + ach.getId() +
                "&b=" + encode(ach.getBadge()));
    }

    private static String generateVerifyMD5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(str.getBytes(StandardCharsets.US_ASCII));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not available", e);
        }
    }

    private static String encode(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Cannot encode URL in UTF-8", e);
        }
    }
}
