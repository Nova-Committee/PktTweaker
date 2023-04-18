package committee.nova.pkttwkr.util;

public class StringUtil {
    public static String getFormatted(String id) {
        return id.contains(":") ? id : "pkttwkr:" + id;
    }
}
