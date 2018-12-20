package felipi.usandoretrofit2.models;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UdacityService {

    public static final String URL_BASE = "https://www.udacity.com/public-api/v0/";

    @GET("courses")
    Call<UdacityCatalog> listCatalog();
}
