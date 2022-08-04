package com.alisa.learning.folders.service;

import java.io.IOException;
import java.net.URISyntaxException;

public interface FolderService {

    void compareFileSystems() throws IOException, URISyntaxException;

    boolean compareUsingWalk(String first, String second) throws IOException;

    boolean compareByNamesUsingMap(String first, String second) throws IOException;

    boolean compareUsingRecursion(String first, String second) throws IOException;

}
