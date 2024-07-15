package com.stringmanolo.panther;

import com.stringmanolo.panther.R;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.webkit.JsPromptResult;
import android.widget.ProgressBar;
import android.webkit.WebChromeClient;
import android.webkit.JsResult;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageView;
import android.webkit.CookieManager;
import android.content.Intent;
import android.webkit.WebSettings.RenderPriority;
import android.os.Build.VERSION;
import android.os.Build;
import android.webkit.WebSettings;
import android.widget.AdapterView; 
import android.widget.ArrayAdapter;
import android.widget.ListView;  
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
  Intent intentArchivos = null;
  Intent intentMotorDeBusq = null;
  Intent intentHacking = null;
  private static final int CODEMotorIntent = 0;
  private static final int CODEHackingIntent = 0;

  private WebView Panther;
  private ProgressBar progressBar;
  private EditText url;
  private ImageView iconoBarra;
  private View rectanguloMenuIcono;

  private ListView listView;  
  private TextView textView;  
  private String[] listItem;  

  String userAgentHacking = "";
  String CuserAgent;
  String UAI = "";
  String VS = "";
  String SSE = "https://google.com/search?q=";
  int progress=0;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Panther = (WebView)findViewById(R.id.webkit);

    rectanguloMenuIcono = (View)findViewById(R.id.RectanguloMenuIcono);
    rectanguloMenuIcono.setVisibility(View.INVISIBLE);

    listView = (ListView)findViewById(R.id.listView);  
    textView = (TextView)findViewById(R.id.textView);  

    listItem = getResources().getStringArray(R.array.array_technology);  

    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,  
      android.R.layout.simple_list_item_1, android.R.id.text1, listItem);  
    listView.setAdapter(adapter);  

    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {  
      @Override  
      public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {    
        String value = adapter.getItem(position);  
        Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();  

        if (value.equals("Motor De Busqueda")) {
          intentMotorDeBusq = new Intent(MainActivity.this, ActividadMotorDeBusqueda.class);
          startActivityForResult(intentMotorDeBusq, CODEMotorIntent);
        }           

        if (value.equals("Codigo Fuente")) {
          Panther.loadUrl("javascript:document.write(\"<xmp contenteditable=\\\"true\\\" style=\\\"width:100%;height:300px;overflow-y:scroll;background-color:#336699;color:white;align:center\\\">\"+document.getElementsByTagName('html')[0].outerHTML+\"</xmp>\"+document.getElementsByTagName('html')[0].outerHTML);");  
        }           

        if (value.equals("Hacking")) {
          intentHacking = new Intent(MainActivity.this, ActividadHacking.class);
          intentHacking.putExtra("CurrentUA", CuserAgent);
          startActivityForResult(intentHacking, CODEHackingIntent);
        }    
      }
    });

    listView.setVisibility(View.INVISIBLE);
    textView.setVisibility(View.INVISIBLE);

    if (Build.VERSION.SDK_INT < 18) {
      /* Panther.getSettings().setRenderPriority(RenderPriority.HIGH); */          
    }

    Panther.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
    Panther.getSettings().setLoadWithOverviewMode(true);
    Panther.getSettings().setDomStorageEnabled(true);
    Panther.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
    Panther.setScrollbarFadingEnabled(true);
    /*
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      Panther.setLayerType(View.LAYER_TYPE_HARDWARE, null);
    } else {
      Panther.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }
    */
    Panther.clearHistory(); 
    Panther.clearCache(true);
    Panther.clearFormData();
    Panther.getSettings().setJavaScriptEnabled(true);

    String userAgent = new WebView(this).getSettings().getUserAgentString();

    String userAgentModificado = userAgent + " Panther/1.0.0";
    CuserAgent = userAgentModificado; 
    Panther.getSettings().setUserAgentString(userAgentModificado);

    Panther.getSettings().setUseWideViewPort(true);    
    Panther.getSettings().setLoadWithOverviewMode(true);
    Panther.getSettings().setBuiltInZoomControls(true); 

    Panther.setWebViewClient(new WebViewClient() {
      @Override
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return false;
      }   
    });

    Panther.setWebChromeClient(new WebChromeClient() {
      private boolean dialogShowing = false;

      @Override
      public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
        if (!dialogShowing) {
          dialogShowing = true;
          AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
          builder.setMessage(message).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              result.confirm();
              dialogShowing = false;
            }
          }).setCancelable(false).show();

          return true;
        }
        return false;
      }

      @Override
      public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
        if (!dialogShowing) {
          dialogShowing = true;
          AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
          builder.setMessage(message).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              result.confirm();
              dialogShowing = false;
            }
          }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              result.cancel();
              dialogShowing = false;
            }
          }).setCancelable(false).show();
          return true;
        }
        return false;
      }

      @Override
      public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
        if (!dialogShowing) {
          dialogShowing = true;
          final EditText input = new EditText(MainActivity.this);
          input.setText(defaultValue);

          AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
          builder.setMessage(message).setView(input)
          .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              String value = input.getText().toString();
              result.confirm(value);
              dialogShowing = false;
            }
          }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              result.cancel();
              dialogShowing = false;
            }
          }).setCancelable(false).show();
          return true;
        }
        return false;
      }
    });


    iconoBarra = (ImageView) findViewById(R.id.favicon);
    url = (EditText) findViewById(R.id.url);
    url.setText("PANTHER");

    Panther.loadUrl("http://www.google.com");
  }

  public void ir(View view) {
    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    inputMethodManager.hideSoftInputFromWindow(url.getWindowToken(), 0);

    UAI = url.getText().toString();

    if (UAI.startsWith("www.")) {
      UAI = "https://" + UAI;
      Panther.loadUrl(UAI);
    } else if (UAI.startsWith("http://") || UAI.startsWith("https://") || UAI.startsWith("file://")) {
      Panther.loadUrl(url.getText().toString());
    } else if (UAI.startsWith("#cod")) {
      UAI = "javascript:document.write(\"<xmp contenteditable=\\\"true\\\" style=\\\"width:100%;height:300px;overflow-y:scroll;background-color:black;color:green;align:center\\\">\"+document.getElementsByTagName('html')[0].outerHTML+\"</xmp>\"+document.getElementsByTagName('html')[0].outerHTML);";
      Panther.loadUrl(UAI);
      UAI = "";
    } else if (UAI.startsWith("#cookie")) {
      UAI = "javascript:document.write(\"<xmp contenteditable=\\\"true\\\" style=\\\"width:100%;height:300px;overflow-y:scroll;background-color:black;color:green;align:center\\\">\"+document.cookie+\"</xmp>\"+document.getElementsByTagName('html')[0].outerHTML);";
      Panther.loadUrl(UAI);
      UAI = "";
    } else if (UAI.startsWith("#arch")) {
      intentArchivos = new Intent(MainActivity.this, ActividadArchivos.class);
      startActivity(intentArchivos);
    } else if (UAI.startsWith("#cake")) {
      UAI = "javascript:document.cookie='enableCAKE=SI;path=/;expires=Thu, 17 Sep 2021 22:23:24'";
      Panther.loadUrl(UAI);
      UAI = "";
    } else {
      UAI = SSE + UAI;
      Panther.loadUrl(UAI);
    }
  }

  public void Config(View view) {
    listView.setVisibility(View.INVISIBLE);
    textView.setVisibility(View.INVISIBLE);

    if(rectanguloMenuIcono.getVisibility() == View.INVISIBLE) { 
      rectanguloMenuIcono.setVisibility(View.VISIBLE);
      listView.setVisibility(View.VISIBLE);
      textView.setVisibility(View.VISIBLE);  
    } else { 
      rectanguloMenuIcono.setVisibility(View.INVISIBLE);   
      listView.setVisibility(View.INVISIBLE);
      textView.setVisibility(View.INVISIBLE);   
    }
  }

  @Override
  public void onBackPressed() {
    if(Panther.canGoBack()) {
      Panther.goBack();
    } else {
      super.onBackPressed();
    }
  }

  @Override 
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data); 

    if (requestCode == CODEMotorIntent) {
      if (resultCode == RESULT_OK) { 
        SSE = data.getStringExtra("OpcionMotor"); 
      }
    }

    if (requestCode == CODEHackingIntent) {
      if (resultCode == RESULT_OK) { 
        userAgentHacking = data.getStringExtra("OpcionHacking"); 
        Panther.getSettings().setUserAgentString(userAgentHacking);   
      }
    }
  }
}

