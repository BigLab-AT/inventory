package net.tepeka.inventory.dao;

import java.util.List;

import net.tepeka.inventory.entity.RaspberryPi;

public interface RaspberryPiDao {

  List<RaspberryPi> getAll();

  RaspberryPi get(int id);

  RaspberryPi get(String serial);
  
  RaspberryPi create();
  
  void update(RaspberryPi rpi);

  void stop();
}
