package net.tepeka.inventory.dao.dummy;

import java.util.ArrayList;
import java.util.List;

import net.tepeka.inventory.dao.RaspberryPiDao;
import net.tepeka.inventory.entity.RaspberryPi;

public class DummyDaoFactory {

	public static RaspberryPiDao createRaspberryPiDao() {
		return new RaspberryPiDao() {

			@Override
			public List<RaspberryPi> getAll() {
				return new ArrayList<>();
			}

			@Override
			public RaspberryPi get(int id) {
				return null;
			}

      @Override
      public RaspberryPi get(String serial) {
        return null;
      }

      @Override
      public void update(RaspberryPi rpi) {        
      }
      
      @Override
      public RaspberryPi create() {
        return null;
      }

      @Override
      public void stop() {
      }
		};
	}
}
