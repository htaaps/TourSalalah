package in.htlabs.tapas.toursalalah.model;

/**
 * Created by Tapas on 5/24/2015.
 */
public class Hotel {
    private String h_id,h_name, h_image, h_details,h_lat,h_lon;

        public Hotel() {
        }

        public Hotel(String h_id, String h_name, String h_image, String h_details, String h_lat, String h_lon) {
            this.h_id = h_id;
            this.h_name = h_name;
            this.h_image = h_image;
            this.h_details = h_details;
            this.h_lat=h_lat;
            this.h_lon=h_lon;
        }

    public String getHId() {
        return h_id;
    }

    public void setHId(String h_id) {
        this.h_id = h_id;
    }

    public String getHName() {
            return h_name;
        }

    public void setHName(String h_name) {
            this.h_name = h_name;
        }

    public String getHImageUrl() {
            return h_image;
        }

    public void setHImageUrl(String h_image) {
            this.h_image = h_image;
        }

    public String getHDetails() {
            return h_details;
        }

    public void setHDetails(String h_details) {
            this.h_details = h_details;
        }

    public String getHLat() {
        return h_lat;
    }

    public void setHLat(String h_lat) {
        this.h_lat = h_lat;
    }

    public String getHLon() {  return h_lon;    }

    public void setHLon(String h_lon) {   this.h_lon = h_lon;   }

}