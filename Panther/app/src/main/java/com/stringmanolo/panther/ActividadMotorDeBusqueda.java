package com.stringmanolo.panther;

import com.stringmanolo.panther.R;

import android.widget.AdapterView; 
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ListView;  
import android.widget.Toast;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.content.Intent;

public class ActividadMotorDeBusqueda extends Activity {
  private ListView listaMotores;  
  private TextView textoMotores;  
  private String[] listaItemsMotores;  

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_motor_de_busqueda);
                
    listaMotores=(ListView)findViewById(R.id.listaMotores);  
    textoMotores=(TextView)findViewById(R.id.textoMotores);  
    listaItemsMotores = getResources().getStringArray(R.array.motores_busqueda);  
        
    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_1, android.R.id.text1, listaItemsMotores);  
    listaMotores.setAdapter(adapter);  
  
    listaMotores.setOnItemClickListener(new AdapterView.OnItemClickListener() {  
      @Override  
      public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        String value=adapter.getItem(position);
        if (value.equals("Google")) {
          String stringToPassBack = "https://google.com/search?q=";
          Intent intent = new Intent();
          intent.putExtra("OpcionMotor", stringToPassBack);
          setResult(RESULT_OK, intent);
          finish();   
        } else if (value.equals("Yippy")) {
          String stringToPassBack = "https://yippy.com/search?query=";
          Intent intent = new Intent();
          intent.putExtra("OpcionMotor", stringToPassBack);
          setResult(RESULT_OK, intent);
          finish();    
        } else if (value.equals("DuckDuckGo")) {
          String stringToPassBack = "https://duckduckgo.com/?q=";
          Intent intent = new Intent();
          intent.putExtra("OpcionMotor", stringToPassBack);
          setResult(RESULT_OK, intent);
          finish();
        } else if (value.equals("Bing")) {
          String stringToPassBack = "https://www.bing.com/search?q=";
          Intent intent = new Intent();
          intent.putExtra("OpcionMotor", stringToPassBack);
          setResult(RESULT_OK, intent);
          finish();
        } else if (value.equals("Yandex")) {
          String stringToPassBack = "https://yandex.com/search/touch/?text=";
          Intent intent = new Intent();
          intent.putExtra("OpcionMotor", stringToPassBack);
          setResult(RESULT_OK, intent);
          finish();
        } else if (value.equals("Ask")) {
          String stringToPassBack = "https://ask.com/web?q=";
          Intent intent = new Intent();
          intent.putExtra("OpcionMotor", stringToPassBack);
          setResult(RESULT_OK, intent);
          finish();
        } else if (value.equals("Hispavista")) {
          String stringToPassBack = "https://buscar.hispavista.com/?q=a&str=a&cadena=";
          Intent intent = new Intent();
          intent.putExtra("OpcionMotor", stringToPassBack);
          setResult(RESULT_OK, intent);
          finish();
        } else if (value.equals("Terra")) {
          String stringToPassBack = "https://buscador.terra.es/Default.aspx?query=";
          Intent intent = new Intent();
          intent.putExtra("OpcionMotor", stringToPassBack);
          setResult(RESULT_OK, intent);
          finish();
        } else if (value.equals("Youtube")) {
          String stringToPassBack = "https://youtube.com/results?search_query=";
          Intent intent = new Intent();
          intent.putExtra("OpcionMotor", stringToPassBack);
          setResult(RESULT_OK, intent);
          finish();
        } else if (value.equals("Facebook")) {
          String stringToPassBack = "https://m.facebook.com/public/";
          Intent intent = new Intent();
          intent.putExtra("OpcionMotor", stringToPassBack);
          setResult(RESULT_OK, intent);
          finish();
        } else if (value.equals("Twitter")) {
          String stringToPassBack = "https://twitter.com/search?q=";
          Intent intent = new Intent();
          intent.putExtra("OpcionMotor", stringToPassBack);
          setResult(RESULT_OK, intent);
          finish();
        }
      }
    });
  }
}
