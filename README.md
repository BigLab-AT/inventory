inventory
=========

Inventory is a Java web-app with which you can manage any items of your belongings (e.g. RaspberryPis). Currently it is primarily used as an inventory for RaspberryPis. The big advantage is that there exists a RESTful interface through which the RaspberryPis can automatically update itself. To make this possible RaspiMon (https://github.com/tepeka/raspimon) has to be installed on every of your RaspberryPis.
Beside the elements which RaspiMon pushes to the inventory server you can record even more things on the server manually. QR-Codes (and later RFID Tags) can be generated and used to identify your belongings. After scanning such a code you will see all data gathered by the inventory server regarding this element.
If you use some of your RaspberryPis 24/7 you will see when it has updated the last time and if there are any problems regarding its up-time, configuration or free storage.
