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
        final var firstPath = prop.getProperty("directory.first");
        final var secondPath = prop.getProperty("directory.second");

        System.out.println(compareUsingWalk(firstPath, secondPath));
        System.out.println(compareByNamesUsingMap(firstPath, secondPath));
        System.out.println(compareUsingRecursion(firstPath, secondPath));

    }


    private boolean isEqualDirectories(File first, File second) {
        final var strings = Arrays.stream(Objects.requireNonNull(first.listFiles()))
                .map(File::getName)
                .toList();
        final var strings1 = Arrays.stream(Objects.requireNonNull(second.listFiles()))
                .map(File::getName)
                .toList();
        return strings.equals(strings1);
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
     * Comparing just by names :(
     */
    @Override
    public boolean compareByNamesUsingMap(String first, String second) throws IOException {

        final var firstPath = prop.getProperty("directory.first");
        final var secondPath = prop.getProperty("directory.second");

        File firstFile = new File(firstPath);
        File secondFile = new File(secondPath);

        flattenTheTree(firstFile, mapFirst);
        flattenTheTree(secondFile, mapSecond);
        return mapFirst.equals(mapSecond);
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
     * Comparing just by names :(
     */
    @Override
    public boolean compareUsingWalk(String first, String second) throws IOException {
        Path path1 = Paths.get(first);
        Path path2 = Paths.get(second);
        final var firstTree = Files.walk(path1).map(Path::getFileName).skip(1).toList();
        final var secondTree = Files.walk(path2).map(Path::getFileName).skip(1).toList();

        return firstTree.equals(secondTree);
    }


    @Override
    public boolean compareUsingRecursion(String firstPath, String secondPath) throws IOException {
        File firstFile = new File(firstPath);
        File secondFile = new File(secondPath);
        return isEqual(firstFile, secondFile);
    }

    private boolean isEqual(File firstFile, File secondFile) throws IOException {
        if (firstFile.isFile() && secondFile.isFile()) {
            if (!isEqualFiles(firstFile, secondFile)) {
                return false;
            }
        }
        if (firstFile.isDirectory() && secondFile.isDirectory()) {
            File[] firstDirContent = firstFile.listFiles();
            File[] secondDirContent = secondFile.listFiles();
            if (firstDirContent.length > 0 && secondDirContent.length > 0) {
                for (int i = 0; i < firstDirContent.length; i++) {
                    if (firstDirContent.length == secondDirContent.length) {
                        if (!isEqual(firstDirContent[i], secondDirContent[i])) {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            } else {
                return isEqualsDirectoryName(firstFile, secondFile);
            }
        }
        return true;
    }

    private boolean isEqualsDirectoryName(File first, File second) {
        return first.getName().equals(second.getName());
    }

    private boolean isEqualFiles(File fileOne, File fileTwo) throws IOException {
        if (fileOne.isHidden() && fileTwo.isHidden()
                && fileOne.getName().equals(fileTwo.getName())) {
            return true;
        } else if (
                Files.size(fileOne.toPath()) == Files.size(fileTwo.toPath()) &&
                        (!fileOne.isHidden() && !fileTwo.isHidden())) {
//          final var reader1 = Files.newBufferedReader(pathOne.toPath());
            String content1 = Files.readString(fileOne.toPath());
            String content2 = Files.readString(fileTwo.toPath());
            return content1.equals(content2);
        } else {
            return false;
        }
    }
}
