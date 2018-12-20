package felipi.usandoretrofit2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;

import felipi.usandoretrofit2.models.Course;
import felipi.usandoretrofit2.models.Instructor;
import felipi.usandoretrofit2.models.UdacityCatalog;
import felipi.usandoretrofit2.models.UdacityService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Felipi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UdacityService.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Estamos retornando uma classe que implementa a interface UdacityService
        UdacityService service = retrofit.create(UdacityService.class);
        Call<UdacityCatalog> requestCatalog = service.listCatalog();

        //Fazer a requisição de forma assíncrona para evitar bloquear o UI pro usuário
        //Precisa fazer a implementação caso não consiga obter uma requisição:

        requestCatalog.enqueue(new Callback<UdacityCatalog>() {
            @Override
            public void onResponse(Call<UdacityCatalog> call, Response<UdacityCatalog> response) {
                //É importante verificar se obteve uma resposta com sucesso
                if (!response.isSuccessful()){
                    Log.i("TAG","Erro: "+ response.code());
                }
                else{
                    //Retornou com sucesso

                    UdacityCatalog catalog = response.body();

                    for(Course c : catalog.courses){
                        Log.i(TAG, String.format("%s: %s", c.title, c.subtitle));
                        for (Instructor i: c.instructors)
                            Log.i(TAG, i.name);
                    }
                    Log.i(TAG,"----------------");
                }
            }

            @Override
            public void onFailure(Call<UdacityCatalog> call, Throwable t) {
                Log.e(TAG, "Erro: "+t.getMessage());
            }
        });

    }
}
