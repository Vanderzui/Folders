package com.alisa.learning.folders;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;

public class FolderServiceImpl implements FolderService {

    @Override
    public void loadFile() throws IOException {
        Properties prop = new Properties();
        InputStream is = getClass().getClassLoader().getResourceAsStream("application.properties");
        prop.load(is);
        final var firstPath = prop.getProperty("directory.first");
        final var secondPath = prop.getProperty("directory.second");

        File first = new File(firstPath);
        File second = new File(secondPath);
//        file.exists();
//        file.getName();
//        Arrays.stream(file.listFiles()).map(f -> f.getName()).toList(); //f ->folderPath + "/" + f.getName()
        System.out.println("");
        compareDirectoriesByFileNamesInside(firstPath, secondPath);


    }

    @Override
    public boolean compareTextFiles(String pathOne, String pathTwo) throws IOException {
//        Path filePath1 = Paths.get(pathOne);
        Path filePath1 = Paths.get("src/main/resources/first.txt");
        Path filePath2 = Paths.get("src/main/resources/second.txt");
        Path filePath3 = Paths.get("src/main/resources/third.txt");

        String content1 = Files.readString(filePath1);
        String content2 = Files.readString(filePath2);
        String content3 = Files.readString(filePath3);

        return content1.equals(content2);
//        return content1.equals(content3));
    }

    @Override
    public boolean compareDirectoriesByFileNamesInside(String firstDirectory,
            String secondDirectory) {
        File first = new File(firstDirectory);
        File second = new File(secondDirectory);
        return
                Arrays.stream(Objects.requireNonNull(first.listFiles()))
                        .map(File::getName)
                        .toList()
                        .equals(
                                Arrays.stream(Objects.requireNonNull(second.listFiles()))
                                        .map(File::getName)
                                        .toList());


    }
}

//        //old way
//        //------------------------------------------
//        File file = new File(folderPath);
//        file.exists();
//        file.getName();
//        Arrays.stream(file.listFiles()).map(f -> f.getName()).toList(); //f ->folderPath + "/" + f.getName()
//        //---

//-------------new way
////        final var s = Files.readString(Path.of(firstPath + "/one/Alisa.txt"));
//        final var start = Paths.get(firstPath);
//        start.getFileSystem();
//        if(Files.isDirectory(start)) {
//            Files.newDirectoryStream(start);
//        }