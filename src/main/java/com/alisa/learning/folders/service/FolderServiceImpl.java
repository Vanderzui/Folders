package com.alisa.learning.folders.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public class FolderServiceImpl implements FolderService {

    private static final String DIRECTORIES = "directories";
    private static final String FILES = "files";
    private Map<String, List<String>> mapFirst;
    private Map<String, List<String>> mapSecond;

    public FolderServiceImpl() {
        mapFirst = new LinkedHashMap<>();
        mapFirst.put(DIRECTORIES, new ArrayList<>());
        mapFirst.put(FILES, new ArrayList<>());

        mapSecond = new LinkedHashMap<>();
        mapSecond.put(DIRECTORIES, new ArrayList<>());
        mapSecond.put(FILES, new ArrayList<>());
    }


    @Override
    public void compareFileSystems() throws IOException {
        Properties prop = new Properties();
        InputStream is = getClass().getClassLoader().getResourceAsStream("application.properties");
        prop.load(is);

        final var firstPath = prop.getProperty("directory.first");
        final var secondPath = prop.getProperty("directory.second");

        File first = new File(firstPath);
        File second = new File(secondPath);

        flattenTheTree(first, mapFirst);
        flattenTheTree(second, mapSecond);

        String equal = mapFirst.equals(mapSecond) ? "are equal" : " are not equal";
        System.out.println(
                "Two different directories one = %s and another %s with some stuff in it %s"
                        .formatted(firstPath, secondPath, equal));

    }

    private void flattenTheTree(File file, Map<String, List<String>> targetMap) {
        var files = file.listFiles();
        for (File innerFile : files) {
            addToMap(innerFile, targetMap);
            if (innerFile.isDirectory()) {
                flattenTheTree(innerFile, targetMap);
            }
        }
    }

    private void addToMap(File file, Map<String, List<String>> targetMap) {
        if (file.isDirectory()) {
            var dirs = targetMap.get(DIRECTORIES);
            dirs.add(file.getName());
        } else if (file.isFile()) {
            var files = targetMap.get(FILES);
            files.add(file.getName());
        }
    }

    /**
     * Will use it later
     */
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

    /**
     * Will use it later or maybe not
     */
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

//DO NOT LOOK
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