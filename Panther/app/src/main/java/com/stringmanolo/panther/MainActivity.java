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
import android.webkit.ConsoleMessage;
import android.webkit.ValueCallback;
import android.webkit.WebStorage;
import java.io.File;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;

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

  private TextView consoleOutput;
  private EditText consoleInput;
  private Button executeButton;

  String userAgentHacking = "";
  String CuserAgent;
  String UAI = "";
  String VS = "";
  /* String SSE = "https://google.com/search?q="; */
  String SSE = "https://html.duckduckgo.com/html/?q=";
  int progress = 0;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Panther = (WebView) findViewById(R.id.webkit);
    consoleOutput = (TextView) findViewById(R.id.consoleOutput);
    consoleInput = (EditText) findViewById(R.id.consoleInput);
    executeButton = (Button) findViewById(R.id.executeButton);

    rectanguloMenuIcono = (View) findViewById(R.id.RectanguloMenuIcono);
    rectanguloMenuIcono.setVisibility(View.INVISIBLE);

    listView = (ListView) findViewById(R.id.listView);
    textView = (TextView) findViewById(R.id.textView);

    listItem = getResources().getStringArray(R.array.array_technology);

    final ArrayAdapter < String > adapter = new ArrayAdapter < String > (this,
      android.R.layout.simple_list_item_1, android.R.id.text1, listItem);
    listView.setAdapter(adapter);

    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView < ? > adapterView, View view, int position, long l) {
        String value = adapter.getItem(position);
        /* Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();  */

        if (value.equals("Search Engine")) {
          intentMotorDeBusq = new Intent(MainActivity.this, ActividadMotorDeBusqueda.class);
          startActivityForResult(intentMotorDeBusq, CODEMotorIntent);
        }

        if (value.equals("Source Code")) {
          Panther.loadUrl("javascript:document.write(\"<xmp contenteditable=\\\"true\\\" style=\\\"width:100%;height:300px;overflow-y:scroll;background-color:#336699;color:white;align:center\\\">\"+document.getElementsByTagName('html')[0].outerHTML+\"</xmp>\"+document.getElementsByTagName('html')[0].outerHTML);");
        }

        if (value.equals("Hacking")) {
          intentHacking = new Intent(MainActivity.this, ActividadHacking.class);
          intentHacking.putExtra("CurrentUA", CuserAgent);
          startActivityForResult(intentHacking, CODEHackingIntent);
        }

        if (value.equals("Console")) {
          if (consoleOutput.getVisibility() == View.VISIBLE) {
            consoleOutput.setVisibility(View.INVISIBLE);
            consoleInput.setVisibility(View.INVISIBLE);
            executeButton.setVisibility(View.INVISIBLE);
          } else {
            consoleOutput.setVisibility(View.VISIBLE);
            consoleInput.setVisibility(View.VISIBLE);
            executeButton.setVisibility(View.VISIBLE);
          }
        }
        
        if (value.equals("Exit")) {
          Panther.clearCache(true);
          Panther.clearFormData();
   
          CookieManager.getInstance().removeSessionCookies(null);
          CookieManager.getInstance().removeAllCookies(null);
          Panther.clearHistory();
          WebStorage.getInstance().deleteAllData();
          
          /* Delete internal cache folder */
          File cacheDir = getCacheDir();
          if (cacheDir.isDirectory()) {
            String[] files = cacheDir.list();
            for (String file : files) {
              File temp = new File(cacheDir, file);
              temp.delete();
            }
          }
          
          finish();
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
    Panther.getSettings().setJavaScriptEnabled(true);

    String userAgent = new WebView(this).getSettings().getUserAgentString();
    String userAgentModificado = "Mozilla/5.0 (Linux; Android 14) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.6478.122 Mobile Safari/537.36";
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
      @Override
      public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        String message = consoleMessage.message() + " -- From line " +
          consoleMessage.lineNumber() + " of " +
          consoleMessage.sourceId();
        consoleOutput.append(message + "\n");
        return true;
      }

      private boolean dialogShowing = false;

      @Override
      public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
        if (!dialogShowing) {
          dialogShowing = true;
          AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
          builder.setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
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
          builder.setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              result.confirm();
              dialogShowing = false;
            }
          }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                String value = input.getText().toString();
                result.confirm(value);
                dialogShowing = false;
              }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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

    /* Panther.loadUrl("http://www.google.com"); */
    /* Panther.loadUrl("https://www.startpage.com"); */
    /* This configures the Search Engine for Privacy before usage */
    Panther.loadUrl("https://www.startpage.com/do/mypage.pl?prfe=449a86aead17f960544d34b1551bb169bc5a2a309ac760de96cf64a4273cee7234af643ebc051ef75227121e6b14d907cc8322d8ff38fcc5edf75d4fdc9358c4fe551829452f29e105cbf242ba");

    // Define una instancia de ValueCallback fuera del método onClick
    final ValueCallback < String > resultCallback = new ValueCallback < String > () {
      @Override
      public void onReceiveValue(String value) {
        // Aquí puedes manejar la respuesta del comando JavaScript, si es necesario
        consoleOutput.append("Result: " + value + "\n");
      }
    };

    // Configura el OnClickListener para el botón executeButton
    executeButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String jsCommand = consoleInput.getText().toString();
        Panther.evaluateJavascript(jsCommand, resultCallback);
      }
    });
    
    
    Panther.setDownloadListener(new MyDownloadListener());
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

    if (rectanguloMenuIcono.getVisibility() == View.INVISIBLE) {
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
    if (Panther.canGoBack()) {
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
  
  
  private class MyDownloadListener implements DownloadListener {
    @Override
    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
      String fileName = URLUtil.guessFileName(url, contentDisposition, mimetype);
      DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
      request.setMimeType(mimetype);
      request.addRequestHeader("User-Agent", userAgent);
      request.setDescription("Downloading File ...");
      request.setTitle(fileName);

      File downloadsDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Panther_Downloads");
      if (!downloadsDirectory.exists()) {
        downloadsDirectory.mkdirs();
      }

      request.setDestinationUri(Uri.fromFile(new File(downloadsDirectory, fileName)));
      DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
      if (downloadManager != null) {
        downloadManager.enqueue(request);
        Toast.makeText(getApplicationContext(), "Downloading: Download/Panther_Downloads/", Toast.LENGTH_SHORT).show();
      } else {
        Toast.makeText(getApplicationContext(), "DownloadManager not available", Toast.LENGTH_SHORT).show();
      }
    }
  }
}
