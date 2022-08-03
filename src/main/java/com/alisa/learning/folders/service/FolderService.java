package com.alisa.learning.folders.service;

import java.io.IOException;
import java.net.URISyntaxException;

public interface FolderService {

    void compareFileSystems() throws IOException, URISyntaxException;

    boolean compareTextFiles(String pathOne, String pathTwo) throws IOException;

    boolean compareDirectoriesByFileNamesInside(String firstDirectory, String secondDirectory);

}
