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
    private Properties prop = new Properties();

    public FolderServiceImpl() throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream("application.properties");
        prop.load(is);

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

    private boolean isEqualDirectories2(File first, File second) {
        return first.getName().equals(second.getName());
    }

    /**
     * Will use it later
     */
    @Override
    public boolean isEqualFiles(File pathOne, File pathTwo) throws IOException {
        if (pathOne.isHidden() && pathTwo.isHidden()
                && pathOne.getName().equals(pathTwo.getName())) {
            return true;
        } else if (!pathOne.isHidden() && !pathTwo.isHidden()) {
//          final var reader1 = Files.newBufferedReader(pathOne.toPath());
            String content1 = Files.readString(pathOne.toPath());
            String content2 = Files.readString(pathTwo.toPath());
            return content1.equals(content2);
        } else {
            return false;
        }
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

    @Override
    public void compareByNamesUsingMap(String first, String second) throws IOException {

        final var firstPath = prop.getProperty("directory.first");
        final var secondPath = prop.getProperty("directory.second");

        File firstFile = new File(firstPath);
        File secondFile = new File(secondPath);
        boolean result = compare2(firstFile, secondFile);
        System.out.println(result);

        flattenTheTree(firstFile, mapFirst);
        flattenTheTree(secondFile, mapSecond);
        String equal = mapFirst.equals(mapSecond) ? "are equal" : "are not equal";
        System.out.println(
                "Two different directories one = %s and another %s with some stuff in it %s"
                        .formatted(firstPath, secondPath, equal));
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

    @Override
    public boolean compareUsingWalk(String first, String second) throws IOException {
        Path path1 = Paths.get(first);
        Path path2 = Paths.get(second);
        final var firstThree = Files.walk(path1).map(Path::getFileName).skip(1).toList();
        final var secondThree = Files.walk(path2).map(Path::getFileName).skip(1).toList();
        return firstThree.equals(secondThree);
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