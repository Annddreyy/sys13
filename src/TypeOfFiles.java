import java.util.HashMap;

public class TypeOfFiles {
    public static HashMap<String, byte[]> types;
    static {
        types = new HashMap<>();
        types.put("mp3", new byte[]{73, 68, 51});
        types.put("png", new byte[]{(byte)0x89, 0x50, 0x4E});
        types.put("mp4", new byte[]{0, 0, 0});
        types.put("jpg", new byte[]{(byte)0xFF, (byte)0xD8, (byte)0xFF});
    }
}
