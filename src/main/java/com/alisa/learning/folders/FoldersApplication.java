package com.alisa.learning.folders;

import java.io.IOException;
import java.net.URISyntaxException;

public class FoldersApplication {

    private static FolderService folderService = new FolderServiceImpl();

    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("I'm alive!");
        folderService.loadFile();
    }


}
