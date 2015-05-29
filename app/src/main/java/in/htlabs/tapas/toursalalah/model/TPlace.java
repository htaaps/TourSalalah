package in.htlabs.tapas.toursalalah.model;

/**
 * Created by Tapas on 5/29/2015.
 */
public class TPlace {
    private String p_id,p_name, p_image, p_details,p_lat,p_lon;

    public TPlace() {
    }

    public TPlace(String p_id, String p_name, String p_image, String p_details, String p_lat, String p_lon) {
        this.p_id = p_id;
        this.p_name = p_name;
        this.p_image = p_image;
        this.p_details = p_details;
        this.p_lat=p_lat;
        this.p_lon=p_lon;
    }

    public String getPId() {
        return p_id;
    }

    public void setPId(String p_id) {
        this.p_id = p_id;
    }

    public String getPName() {
        return p_name;
    }

    public void setPName(String p_name) {
        this.p_name = p_name;
    }

    public String getPImageUrl() {
        return p_image;
    }

    public void setPImageUrl(String p_image) {
        this.p_image = p_image;
    }

    public String getPDetails() {
        return p_details;
    }

    public void setPDetails(String p_details) {
        this.p_details = p_details;
    }

    public String getPLat() {
        return p_lat;
    }

    public void setPLat(String p_lat) {
        this.p_lat = p_lat;
    }

    public String getPLon() {  return p_lon;    }

    public void setPLon(String p_lon) {   this.p_lon = p_lon;   }

}