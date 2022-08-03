package com.alisa.learning.folders;

import com.alisa.learning.folders.service.FolderService;
import com.alisa.learning.folders.service.FolderServiceImpl;
import java.io.IOException;
import java.net.URISyntaxException;

public class FoldersApplication {

    private static FolderService folderService = new FolderServiceImpl(); //todo

    public static void main(String[] args) throws IOException, URISyntaxException {
        folderService.compareFileSystems();
    }


}
