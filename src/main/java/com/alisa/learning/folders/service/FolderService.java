package com.alisa.learning.folders.service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public interface FolderService {

    void compareFileSystems() throws IOException, URISyntaxException;

    boolean isEqualFiles(File pathOne, File pathTwo) throws IOException;

    boolean compareDirectoriesByFileNamesInside(String firstDirectory, String secondDirectory);

    boolean compareUsingWalk(String first, String second) throws IOException;

    void compareByNamesUsingMap(String first, String second) throws IOException;

}
