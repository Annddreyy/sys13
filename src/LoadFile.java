import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.concurrent.Callable;

class LoadFile implements Callable<String> {
    final String filePath;
    final String newFileName;

    LoadFile(String filePath, String newFileName) {
        this.filePath = filePath;
        this.newFileName = newFileName;
    }

    public String call(){
        try {
            URLConnection conn = new URL(filePath).openConnection();

            InputStream in = conn.getInputStream();

            OutputStream out = new FileOutputStream(new File(newFileName));

            byte[] header = new byte[3];
            in.read(header);

            System.out.println(Arrays.toString(header));

            boolean findType = false;
            for (String key : TypeOfFiles.types.keySet()) {
                if (Arrays.toString(TypeOfFiles.types.get(key)).equals(Arrays.toString(header))) {
                    out.write(header);
                    out.write(in.readAllBytes());
                    findType = true;
                }
            }

            if (!findType) {
                out = new FileOutputStream(new File("OUT_TXT.txt"));
                out.write(in.readAllBytes());
                out.close();
                return "Not known file type. File was save as txt";
            }

            out.close();

            return "Ok";
        }
        catch (IOException e) {
            return "Error load file";
        }
    }
}
