package in.htlabs.tapas.toursalalah.model;

/**
 * Created by Tapas on 5/24/2015.
 */
public class Restaurant {
    private String r_id,r_name, r_image, r_details,r_lat,r_lon;

    public Restaurant() {
    }

    public Restaurant(String r_id, String r_name, String r_image, String r_details, String r_lat, String r_lon) {
        this.r_id = r_id;
        this.r_name = r_name;
        this.r_image = r_image;
        this.r_details = r_details;
        this.r_lat=r_lat;
        this.r_lon=r_lon;
    }

    public String getRId() {
        return r_id;
    }

    public void setRId(String r_id) {
        this.r_id = r_id;
    }

    public String getRName() {
        return r_name;
    }

    public void setRName(String r_name) {
        this.r_name = r_name;
    }

    public String getRImageUrl() {
        return r_image;
    }

    public void setRImageUrl(String r_image) {
        this.r_image = r_image;
    }

    public String getRDetails() {
        return r_details;
    }

    public void setRDetails(String r_details) {
        this.r_details = r_details;
    }

    public String getRLat() {
        return r_lat;
    }

    public void setRLat(String r_lat) {
        this.r_lat = r_lat;
    }

    public String getRLon() {  return r_lon;    }

    public void setRLon(String r_lon) {   this.r_lon = r_lon;   }

}