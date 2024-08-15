import java.io.*;
import java.net.Socket;

public class MultimediaHandler {

    public static void sendFile(File file, Socket socket) throws IOException {
        try (FileInputStream fis = new FileInputStream(file);
                BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream())) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            bos.flush();
        }
    }

    public static void receiveFile(String savePath, Socket socket) throws IOException {
        try (BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
                FileOutputStream fos = new FileOutputStream(savePath)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }
    }
}
