package dev.c20.workflow.commons.tools;

import java.io.Serializable;

public class StoragePathUtil implements Serializable  {
    
    private String resource;
    private boolean findFile = true;
    private boolean findFolder = true;

    public String getPath() {
        return this.resource;
    }

    public String getPathDbLIKE() {
        return this.resource + "%";
    }

    public StoragePathUtil setPath(String path) {
        this.resource = path;
        this.maxLevel = this.getPathLevel();
        return this;
    }

    public StoragePathUtil() {
    }

    public StoragePathUtil(String path) {
        this.resource = path;
    }

    private Boolean showFolders = true ;
    private Boolean showFiles = true;

    private int maxLevel = 0;

    public boolean isFolder() {

        if( StringUtils.isEmpty(resource) ) {
            return false;
        }
        return resource.endsWith("/");
    }

    public int getLevel() {
        return this.getPathLevel();
    }

    public StoragePathUtil setRecursive( boolean recursive) {
        if( recursive ) {
            maxLevel = 10000;
        } else {
            maxLevel = this.getPathLevel();
        }
        return this;
    }

    public boolean isFile() {

        if( StringUtils.isEmpty(resource) ) {
            return false;
        }
        return !resource.endsWith("/");
    }

    public String getName() {

        if ("/".equals( resource)) {
            return "/";
        }
        // remove the last char, for a folder this will be "/", for a file it does not matter
        String parent = (resource.substring(0, resource.length() - 1));
        // now as the name does not end with "/", check for the last "/" which is the parent folder name
        return parent.substring(parent.lastIndexOf('/') + 1);
    }

    public String getExtension() {

        if( isFolder() ) {
            return null;
        }
        String name = getName();

        if( name.indexOf(".") == -1 ) {
            return null;
        }

        return name.substring(name.indexOf(".")+1);
    }

    public String getFolderPath() {

        return resource.substring(0, resource.lastIndexOf('/') + 1);
    }

    public String getParentFolder() {

        if ( StringUtils.isEmptyOrWhitespaceOnly(resource) || resource == "/" ) {
            return null;
        }
        // remove the last char, for a folder this will be "/", for a file it does not matter
        String parent = (resource.substring(0, resource.length() - 1));
        // now as the name does not end with "/", check for the last "/" which is the parent folder name
        return parent.substring(0, parent.lastIndexOf('/') + 1);
    }

    public int getPathLevel() {

        int level = -1;
        int pos = 0;
        while (resource.indexOf('/', pos) >= 0) {
            pos = resource.indexOf('/', pos) + 1;
            level++;
        }
        if( resource.endsWith("/") ) {
            level--;
        }
        return level;
    }

    public String getPathFromLevel( int level) {
        String result = getPathPart(level-1);
        return "/" + resource.substring(result.length());
    }
    public String getPathNameFromLevel( int level) {
        return splitPath()[level];
    }

    public String getPathPart(int level) {

        resource = getFolderPath();
        String result = null;
        int pos = 0, count = 0;
        if (level >= 0) {
            // Walk down from the root folder /
            while ((count < level) && (pos > -1)) {
                count++;
                pos = resource.indexOf('/', pos + 1);
            }
        } else {
            // Walk up from the current folder
            pos = resource.length();
            while ((count > level) && (pos > -1)) {
                count--;
                pos = resource.lastIndexOf('/', pos - 1);
            }
        }
        if (pos > -1) {
            // To many levels walked
            result = resource.substring(0, pos + 1);
        } else {
            // Add trailing slash
            result = (level < 0) ? "/" : resource;
        }
        return result;
    }

    public String[] splitPath(  ) {
        if( StringUtils.isEmpty(resource) ) {
            return null;
        }

        if( resource.startsWith("/") ) {
            resource = resource.substring(1);
        }

        return resource.split("/");
    }


    public boolean isFindFolder() {
        return findFolder;
    }

    public StoragePathUtil setFindFolder(boolean findFolder) {
        this.findFolder = findFolder;
        return this;
    }

    public boolean isFindFile() {
        return findFile;
    }

    public StoragePathUtil setFindFile(boolean findFile) {
        this.findFile = findFile;
        return this;
    }

    public Boolean getShowFolders() {
        return showFolders;
    }

    public StoragePathUtil setShowFolders(Boolean showFolders) {
        this.showFolders = showFolders;
        return this;
    }

    public Boolean getShowFiles() {
        return showFiles;
    }

    public StoragePathUtil setShowFiles(Boolean showFiles) {
        this.showFiles = showFiles;
        return this;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public StoragePathUtil setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
        return this;
    }
}
