package com.alisa.learning.folders;

import java.io.IOException;
import java.net.URISyntaxException;

public interface FolderService {

    void loadFile() throws IOException, URISyntaxException;

    boolean compareTextFiles(String pathOne, String pathTwo) throws IOException;

    boolean compareDirectoriesByFileNamesInside(String firstDirectory, String secondDirectory);

}
