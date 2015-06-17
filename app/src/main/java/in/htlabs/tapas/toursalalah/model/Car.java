package in.htlabs.tapas.toursalalah.model;

/**
 * Created by Tapas on 5/30/2015.
 */
public class Car {

    private String c_id,c_name, c_image, c_details,c_lat,c_lon,price;

    public Car() {
    }

    public Car(String c_id, String c_name, String c_image, String c_details, String c_lat, String c_lon, String price) {
        this.c_id = c_id;
        this.c_name = c_name;
        this.c_image = c_image;
        this.c_details = c_details;
        this.c_lat=c_lat;
        this.c_lon=c_lon;
        this.price=price;
    }
    public String getCId() {
        return c_id;
    }

    public void setCId(String h_id) {
        this.c_id = c_id;
    }

    public String getCName() {
        return c_name;
    }

    public void setCName(String c_name) {
        this.c_name = c_name;
    }

    public String getCImageUrl() {
        return c_image;
    }

    public void setCImageUrl(String c_image) {
        this.c_image = c_image;
    }

    public String getCDetails() {
        return c_details;
    }

    public void setCDetails(String c_details) {
        this.c_details = c_details;
    }

    public String getCLat() {
        return c_lat;
    }

    public void setCLat(String c_lat) {
        this.c_lat = c_lat;
    }

    public String getCLon() {  return c_lon;    }

    public void setCLon(String c_lon) {   this.c_lon = c_lon;   }

    public String getCPrice() {  return price;    }

    public void setCPrice(String price) {   this.price = price;   }
}
