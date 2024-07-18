package com.stringmanolo.panther;

import com.stringmanolo.panther.R;

import android.widget.AdapterView; 
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ListView;  
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.content.Intent;

import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class ActividadHacking extends Activity {
  private EditText etUserAgent;
  private ListView listaHacking;  
  private TextView textoHacking;  
  private String[] listaItemsHacking;  
   
  String stringToPassBack = "NuevoUserAgent";
   
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_hacking);
    
    etUserAgent=(EditText)findViewById(R.id.userAgent);
    listaHacking=(ListView)findViewById(R.id.listaHacking);  
    textoHacking=(TextView)findViewById(R.id.textoHacking);  
        
    listaItemsHacking = getResources().getStringArray(R.array.hacking);  
        
    Intent getCurrentUA = getIntent();
    String CurrentUA = getCurrentUA.getExtras().getString("CurrentUA"); 
    etUserAgent.setText(CurrentUA);
   /*  etUserAgent.setText("Mozilla/5.0 (iPhone; CPU iPhone OS 12_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148");  */
                    
    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_1, android.R.id.text1, listaItemsHacking);  
    listaHacking.setAdapter(adapter);  
    listaHacking.setOnItemClickListener(new AdapterView.OnItemClickListener() {  
      @Override  
      public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {    
        String value=adapter.getItem(position);  
        if (value.equals("User-Agent")) {
          Intent intent = new Intent();
          intent.putExtra("OpcionHacking", stringToPassBack);
          setResult(RESULT_OK, intent);
          finish();   
        }           
      }
    });          
  }
  
  public void irUA(View view) {
    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    inputMethodManager.hideSoftInputFromWindow(etUserAgent.getWindowToken(), 0);

    stringToPassBack = etUserAgent.getText().toString();

    Intent intent = new Intent();
    intent.putExtra("OpcionHacking", stringToPassBack);
    setResult(RESULT_OK, intent);
    finish();   
  }
}

