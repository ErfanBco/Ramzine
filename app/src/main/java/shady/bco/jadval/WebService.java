package shady.bco.jadval;

import com.google.gson.annotations.SerializedName;

public class WebService {
    @SerializedName("Status")
    private String Status;


    @SerializedName("Link")
    private String Link;



    public String getStatus() {
        return Status;
    }

    public String getLink() {
        return Link;
    }


}
