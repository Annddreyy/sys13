import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        ArrayList<LoadFile> loadingFiles = new ArrayList<>();

        while (true) {
            System.out.println("Введите путь к файлу, который необходимо скачать: ");
            String file = in.next();
            System.out.println("Введите название файла: ");
            String fileName = in.next();

            loadingFiles.add(new LoadFile(file, fileName));

            System.out.println("Введите 1, чтобы закончить ввод, либо любой символ для продолжения: ");
            String command = in.next();
            if (Objects.equals(command, "1")) break;
        }

        ExecutorService executor = Executors.newFixedThreadPool(loadingFiles.size());

        ArrayList<Future<String>> futures = new ArrayList<>();
        for (int i = 0; i < loadingFiles.size(); i++)
            futures.add(executor.submit(loadingFiles.get(i)));

        try {
            for (int i = 0; i < loadingFiles.size(); i++){
                String result = futures.get(i).get();
                System.out.printf("Load %s: %s\n", loadingFiles.get(i).filePath, result);
                if (Objects.equals(result, "Ok") && loadingFiles.get(i).filePath.endsWith(".mp3")) {
                    String musicFilePath = "C:\\Users\\Andre\\IdeaProjects\\sys14\\" + loadingFiles.get(i).newFileName;
                    String musicPlayerPath = "C:\\Program Files (x86)\\Windows Media Player\\wmplayer.exe";

                    ProcessBuilder processBuilder = new ProcessBuilder(musicPlayerPath, musicFilePath);

                    Process process = processBuilder.start();
                    try {
                        process.waitFor();
                    } catch (InterruptedException e) {
                        System.out.println("Ошибка!");
                    }
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        executor.shutdown();
    }
}
