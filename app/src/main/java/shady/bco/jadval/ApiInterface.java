package shady.bco.jadval;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    @POST("TheGame.php")
    Call<WebService> loginCall(@Query("ID") String ID, @Query("Version") String Version);
}
