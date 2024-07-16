package com.stringmanolo.panther;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class ActividadMotorDeBusqueda extends Activity {

  private ListView listaMotores;
  private TextView textoMotores;
  private String[] listaItemsMotores;

  private Map<String, String> urlMap = new HashMap<>();

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_motor_de_busqueda);

    listaMotores = (ListView) findViewById(R.id.listaMotores);
    textoMotores = (TextView) findViewById(R.id.textoMotores);
    listaItemsMotores = getResources().getStringArray(R.array.motores_busqueda);

    initUrlMap();

    final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, listaItemsMotores);
    listaMotores.setAdapter(adapter);

    listaMotores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        String value = adapter.getItem(position);
        String url = urlMap.get(value);
        if (url != null) {
          Intent intent = new Intent();
          intent.putExtra("OpcionMotor", url);
          setResult(RESULT_OK, intent);
          finish();
        }
      }
    });
  }

  private void initUrlMap() {
    urlMap.put("AllRecipes", "https://www.allrecipes.com/search?q=");
    urlMap.put("Baidu", "https://www.baidu.com/s?word=");
    urlMap.put("Bing", "https://www.bing.com/search?q=");
    urlMap.put("DuckDuckGo", "https://duckduckgo.com/?q=");
    urlMap.put("Ebay", "https://www.ebay.com/sch/i.html?_nkw=");
    urlMap.put("Ecosia", "https://www.ecosia.org/search?q=");
    urlMap.put("Facebook", "https://m.facebook.com/public/");
    urlMap.put("Google", "https://google.com/search?q=");
    urlMap.put("QWant", "https://www.qwant.com/?q=");
    urlMap.put("Yandex", "https://yandex.com/search/touch/?text=");
    urlMap.put("Youtube", "https://youtube.com/results?search_query=");
  }
}

