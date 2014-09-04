package net.tepeka.inventory.util.file;

import java.nio.file.Path;

public interface DirectoryWatcherListener {

  void fileModified(Path path);
  
  void fileCreated(Path path);
  
  void fileDeleted(Path path);
  
}
