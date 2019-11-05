package com.example.javiersis22project;

import android.content.Context;
import android.widget.Toast;
import com.example.crudmysqlandroid.MySingleton;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.makeText;

public class MantenimientoMySQL {
    boolean estadoGuardar = false;

    public void guardar(final Context context, final String codigo, final String descripcion, final String precio) {
        String url = "http://mjgl.com.sv/mysql_crud/guardar.php";
        final StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //En este metodo se recibe la respuesta en JSON desde el web service o API
                try {
                    JSONObject requestJSON = new JSONObject(response.toString());
                    String estado = requestJSON.getString("estado");
                    String mensaje = requestJSON.getString("mensaje");

                    if (estado.equals("1")) {
                        makeText(context, mensaje, Toast.LENGTH_SHORT).show();
                    } else if (mensaje.equals("2")) {
                        makeText(context, "Error, no se pudo guardar. \n" + "Intentelo más tarde.", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Notifica un error posible al realizar una accion de la BD remota.
                makeText(context, "Error, no se pudo guardar. \n" + "Verifique su acceso a internet.", Toast.LENGTH_SHORT).show();

            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/json; charset=utf-8");
                map.put("Accept", "application/json");
                map.put("codigo", codigo);
                map.put("descripcion", descripcion);
                map.put("precio", precio);
                return map;
            }
        };
     //   MySingleton.getInstance(context).addToRequestQueque(request);
    }
}

