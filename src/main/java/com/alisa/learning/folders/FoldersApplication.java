package com.alisa.learning.folders;

import com.alisa.learning.folders.service.FolderService;
import com.alisa.learning.folders.service.FolderServiceImpl;
import java.io.IOException;
import java.net.URISyntaxException;

public class FoldersApplication {

    public static void main(String[] args) throws IOException, URISyntaxException {
        FolderService folderService = new FolderServiceImpl();
        folderService.compareFileSystems();
    }


}
