package org.lulz.jrat;

/**
 * Created by Vyas on 10/4/2017.
 */

public class RatSighting {
    long uniqueKey;
    String createdDate;
    String locationType;
    long incidentZip;
    String incidentAddress;
    String city;
    String borough;
    double latitude;
    double longitude;

    RatSighting(long key, String date, String type, long zip, String addr, String cty, String brgh,
                double lat, double longit) {
        this.uniqueKey = key;
        this.createdDate = date;
        this.locationType = type;
        this.incidentZip = zip;
        this.incidentAddress = addr;
        this.city = cty;
        this.borough = brgh;
        this.latitude = lat;
        this.longitude = longit;
    }

    @Override
    public String toString() {
        return "RatSighting{" + " \n" +
               "uniqueKey= " + Long.toString(uniqueKey) + ", \n" +
               "createdDate= " + createdDate + ", \n" +
               "locationType= " + locationType + ", \n" +
               "incidentZip= " + Long.toString(incidentZip) + ", \n" +
               "incidentAddress= " + incidentAddress + ", \n" +
               "city= " + city + ", \n" +
               "borough= " + borough + ", \n" +
               "latitude= " + Double.toString(latitude) + ", \n" +
               "longitude= " + Double.toString(longitude) + "}\n";
    }
}
