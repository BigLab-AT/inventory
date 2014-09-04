package net.tepeka.inventory.dao.xml;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import net.tepeka.inventory.dao.RaspberryPiDao;
import net.tepeka.inventory.entity.RaspberryPi;
import net.tepeka.inventory.util.file.DirectoryWatcher;
import net.tepeka.inventory.util.file.DirectoryWatcherListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RaspberryPiXmlDao implements RaspberryPiDao, DirectoryWatcherListener {

  private static final String FILENAME_PREFIX = "rpi";
  private static final String FILE_ENDING = ".xml";
  private static final String CONTENT_TYPE = "text/xml";

  private final Logger log = LogManager.getLogger(RaspberryPiXmlDao.class.getName());
  private final List<RaspberryPi> rpis;
  private final Path dir;
  private final DirectoryWatcher watcher;
  private final Unmarshaller u;

  private int idCounter = 0;

  public RaspberryPiXmlDao(String path) throws JAXBException, IOException {
    rpis = new ArrayList<RaspberryPi>();
    // -- check directory
    dir = FileSystems.getDefault().getPath(path);
    File file = dir.toFile();
    if (!file.exists()) {
      file.mkdir();
    }
    if (!file.exists()) {
      throw new IllegalStateException(String.format("Directory '%s' could not be created.", path));
    }
    // -- create jaxb parser
    JAXBContext context = JAXBContext.newInstance(RaspberryPi.class);
    u = context.createUnmarshaller();
    // -- update data
    loadDirectory();
    // -- start watcher
    watcher = new DirectoryWatcher(dir, CONTENT_TYPE);
    watcher.addFileChangeListener(this);
    watcher.start();
    log.info("Started filewatcher for directory " + path);
  }

  @Override
  public List<RaspberryPi> getAll() {
    return rpis;
  }

  @Override
  public RaspberryPi get(int id) {
    List<RaspberryPi> results = rpis.parallelStream().filter(r -> id == r.id).collect(Collectors.toList());
    switch (results.size()) {
      case 0 :
        return null;

      case 1 :
        return results.get(0);

      default :
        // error
        throw new IllegalStateException(String.format("Found %s rpis with id '%s'.", results.size(), id));
    }
  }

  public RaspberryPi get(String serial) {
    List<RaspberryPi> results = rpis.parallelStream().filter(r -> serial.equals(r.serial)).collect(Collectors.toList());
    switch (results.size()) {
      case 0 :
        return null;

      case 1 :
        return results.get(0);

      default :
        // error
        throw new IllegalStateException(String.format("Found %s rpis with serialnumber '%s'.", results.size(), serial));
    }
  }

  @Override
  public RaspberryPi create() {
    final RaspberryPi rpi = new RaspberryPi();
    final int id = ++idCounter;
    rpi.id = id;
    rpis.add(rpi);
    log.info("Creating new raspberry rpi#" + rpi.id);
    return rpi;
  }

  @Override
  public void update(RaspberryPi rpi) {
    rpis.removeIf(r -> r.id == rpi.id);
    rpis.add(rpi);
    log.info("Updating raspberry rpi#" + rpi.id);
  }

  @Override
  public void fileModified(Path path) {
    try {
      RaspberryPi rpi = parseFile(path.toFile());
      if (rpi != null) {
        final int id = parseIdFromFilename(path.getFileName().toString());
        // id in filename must equal id from file content
        if (id != rpi.id) {
          log.warn(String.format("Cannot add rpi because id in filename does not equal id from file."));

          // new id must not be higher than counter
        } else if (rpi.id > idCounter) {
          log.warn(String.format("Cannot add rpi because id %s is too high. Take id %s.", rpi.id, idCounter));

        } else {
          update(rpi);
        }
      }
    } catch (NumberFormatException e) {
      log.error("Cannot modify rpi because filname could not be parsed.");

    } catch (Exception e) {
      log.error("Cannot modify rpi.", e);
    }
  }

  @Override
  public void fileCreated(Path path) {
    try {
      RaspberryPi rpi = parseFile(path.toFile());
      if (rpi != null) {
        final int id = parseIdFromFilename(path.getFileName().toString());
        // id in filename must equal id from file content
        if (id != rpi.id) {
          log.warn(String.format("Cannot add rpi because id in filename does not equal id from file."));

          // check if id is already taken
        } else if (rpis.stream().anyMatch(r -> r.id == rpi.id)) {
          log.warn(String.format("Cannot add rpi because id %s is already in use.", rpi.id));

          // new id must not be higher than counter
        } else if (rpi.id > idCounter) {
          log.warn(String.format("Cannot add rpi because id %s is too high. Take id %s.", rpi.id, idCounter));

          // id ok, add rpi
        } else {
          rpis.add(rpi);
          idCounter++;
          log.info("Added new raspberry rpi#" + rpi.id);
        }
      }
    } catch (NumberFormatException e) {
      log.error("Cannot add rpi because filname could not be parsed.");

    } catch (Exception e) {
      log.error("Cannot add rpi.", e);
    }
  }

  @Override
  public void fileDeleted(Path path) {
    try {
      final int id = parseIdFromFilename(path.getFileName().toString());
      // -- try to remove
      if (rpis.removeIf(r -> r.id == id)) {
        log.info(String.format("Removed rpi#%s successfully", id));

      } else {
        log.warn(String.format("There is no rpi#%s which can be removed.", id));
      }
    } catch (NumberFormatException e) {
      log.error("Cannot remove rpi because filname could not be parsed.");

    } catch (Exception e) {
      log.error("Cannot remove rpi.", e);
    }
  }

  private void loadDirectory() throws JAXBException {
    rpis.clear();
    Arrays.asList(dir.toFile().listFiles(new FileFilter() {
      @Override
      public boolean accept(File pathname) {
        return pathname.getName().endsWith(FILE_ENDING);
      }
    })).forEach(file -> rpis.add(parseFile(file)));
    idCounter = rpis.size();
  }

  private RaspberryPi parseFile(File file) {
    try {
      return (RaspberryPi) u.unmarshal(file);

    } catch (Exception e) {
      log.error(String.format("Could not parse file '%s'.", file.getName()), e);
      return null;
    }
  }

  private int parseIdFromFilename(String filename) {
    return Integer.parseInt(filename.substring(FILENAME_PREFIX.length(), filename.length() - FILE_ENDING.length()));
  }

}