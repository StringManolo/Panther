package com.stringmanolo.panther;

import com.stringmanolo.panther.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ListView;

public class ActividadArchivos extends Activity {
  private ListView ListaArchivos;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_archivos); 
   
    ListaArchivos = (ListView) findViewById(R.id.ListaArchivos);
  }
}
