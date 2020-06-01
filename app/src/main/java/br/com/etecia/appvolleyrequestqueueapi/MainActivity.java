package br.com.etecia.appvolleyrequestqueueapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

public class MainActivity extends AppCompatActivity {
    Button btnAcessarServer;
    TextView txtRespostaWEB;
    String server_url = "http://192.168.100.5/projetovolleyapi/helloserver.php";
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAcessarServer = findViewById(R.id.btnAcessaServer);
        txtRespostaWEB = findViewById(R.id.txtRespostaWEB);

        Cache cache = new DiskBasedCache(getCacheDir(), 1024*1024);
        Network network =  new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache,network);
        requestQueue.start();

        btnAcessarServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        server_url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                txtRespostaWEB.setText(response);
                                requestQueue.stop();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        txtRespostaWEB.setText("Error...");
                        error.printStackTrace();
                    }
                }
                );
                requestQueue.add(stringRequest);
            }
        });

    }
}
