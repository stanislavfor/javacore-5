package backup_app;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.*;

public class UtilityBackup {

    public void run() {
        System.out.println("\nUtilityBackup is running...");

//    public static void main(String[] args) {

//      Применение методов Paths.get(String) и Path.resolveSibling(String other)

        Path SOURCE_DIR = Paths.get("src/");
        Path BACKUP_DIR = SOURCE_DIR.resolveSibling("backup");
        Path FILE_PATH = SOURCE_DIR.resolveSibling("backup/backup_archive.zip");
        try {
//            backupDirectory(SOURCE_DIR, BACKUP_DIR);
            backupDirectory(SOURCE_DIR, BACKUP_DIR, FILE_PATH);
            System.out.println("Резервное копирование выполнено.");
        } catch (IOException e) {
            System.err.println("Ошибка резервного копирования.");
            e.printStackTrace();
        }
    }

    public static void backupDirectory(Path SOURCE_DIR, Path BACKUP_DIR, Path FILE_PATH) throws IOException {

        if (!Files.exists(BACKUP_DIR)) {
            Files.createDirectories(BACKUP_DIR);
        }


//         Применение метода ZipOutputStream — для создания архива
//         ZipInputStream — для чтения архивов

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(FILE_PATH.toFile()))) {
            Files.walkFileTree(SOURCE_DIR, new SimpleFileVisitor<Path>() {

                //          Метод putNextEntry() используется для добавления объектов ZipEntry в архив
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    zos.putNextEntry(new ZipEntry(String.valueOf(SOURCE_DIR.relativize(file))));
                    Files.copy(file, zos);
                    zos.closeEntry();
                    return FileVisitResult.CONTINUE;
                }

                //          FileVisitResult — перечисление возможных возвращаемых значений для методов интерфейса FileVisitor
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    zos.putNextEntry(new ZipEntry(SOURCE_DIR.relativize(dir) + "/"));
                    zos.closeEntry();
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            System.err.println("Ошибка при копировании из : " + SOURCE_DIR);
            throw e;
        }

    }


}
