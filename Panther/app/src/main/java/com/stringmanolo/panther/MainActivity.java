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
import android.webkit.PermissionRequest;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import android.graphics.Typeface;
import android.widget.ScrollView;
import android.os.Handler;
import android.os.Looper;

import java.net.URL;

public class MainActivity extends Activity {
  Intent intentArchivos = null;
  Intent intentMotorDeBusq = null;
  Intent intentHacking = null;
  private static final int CODEMotorIntent = 0;
  private static final int CODEHackingIntent = 0;

  private WebView Panther;
  private ProgressBar progressBar;
  private EditText url;
  private EditText omnibox;
  private ImageView iconoBarra;
  private View rectanguloMenuIcono;

  private ListView listView;
  private TextView textView;
  private String[] listItem;

  private TextView logsOutput;

  private TextView consoleOutput;
  private EditText consoleInput;
  private Button executeButton;

  String userAgentHacking = "";
  String CuserAgent;
  String UAI = "";
  String VS = "";
  String SSE = "https://html.duckduckgo.com/html/?q=";
  int progress = 0;
  boolean jsEnabled = true;
  boolean blockerEnabled = true;
  boolean blockFingerprintEnabled = false;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Panther = (WebView) findViewById(R.id.webkit);

    logsOutput = (TextView) findViewById(R.id.logsOutput);

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

        if (value.equals("Logs")) {
          if (logsOutput.getVisibility() == View.VISIBLE) {
            logsOutput.setVisibility(View.INVISIBLE);
          } else {
            logsOutput.setVisibility(View.VISIBLE);
          }
        }

        if (value.equals("Javascript")) {   
          if (jsEnabled) {
            jsEnabled = false;
            Toast.makeText(getApplicationContext(), "Javascript is now disabled", Toast.LENGTH_SHORT).show();
          } else {
            jsEnabled = true;
            Toast.makeText(getApplicationContext(), "Javascript is now enabled", Toast.LENGTH_SHORT).show();
          }
          Panther.getSettings().setJavaScriptEnabled(jsEnabled);
        }

        if (value.equals("Block ADS/Trackers")) {
          if (blockerEnabled) {
            blockerEnabled = false;
            Toast.makeText(getApplicationContext(), "Blocker is now disabled", Toast.LENGTH_SHORT).show();
          } else {
            blockerEnabled = true;
            Toast.makeText(getApplicationContext(), "Blocker is now removing ads and trackers", Toast.LENGTH_SHORT).show();
          }
        }

        if (value.equals("Block Fingerprint")) {
          if (blockFingerprintEnabled) {
            blockFingerprintEnabled = false;
            Toast.makeText(getApplicationContext(), "Fingerprint Blocking is Disabled", Toast.LENGTH_SHORT).show();
          } else {
            blockFingerprintEnabled = true;
            Toast.makeText(getApplicationContext(), "Fingerprint Blocker is now faking Fingerprint values", Toast.LENGTH_SHORT).show();
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

    /* Hidden by default */
    listView.setVisibility(View.INVISIBLE);
    textView.setVisibility(View.INVISIBLE);
    logsOutput.setVisibility(View.INVISIBLE);
    consoleOutput.setVisibility(View.INVISIBLE);
    consoleInput.setVisibility(View.INVISIBLE);
    executeButton.setVisibility(View.INVISIBLE);

    if (Build.VERSION.SDK_INT < 18) {
      /* Panther.getSettings().setRenderPriority(RenderPriority.HIGH); */
    }

    Panther.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
    Panther.getSettings().setLoadWithOverviewMode(true);
    Panther.getSettings().setDomStorageEnabled(true);
    Panther.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
    Panther.setScrollbarFadingEnabled(true); 
    Panther.getSettings().setJavaScriptEnabled(true);

    //String userAgent = new WebView(this).getSettings().getUserAgentString();
    String userAgentModificado = "Mozilla/5.0 (Linux; Android 14) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.6478.122 Mobile Safari/537.36";
    CuserAgent = userAgentModificado;
    Panther.getSettings().setUserAgentString(userAgentModificado);

    Panther.getSettings().setUseWideViewPort(true);
    Panther.getSettings().setLoadWithOverviewMode(true);
    Panther.getSettings().setBuiltInZoomControls(true);

    consoleOutput.setText("Use the url #clearconsole to clear\n\n\n\n\n\n\n\n");
    logsOutput.setText("! Use the url #clearlogs to clear. #list to list available urls\n\n\n");

    Panther.setWebViewClient(new WebViewClient() {
      @Override
      public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        final String url = request.getUrl().toString();
        final Handler mHandler = new Handler(Looper.getMainLooper());
        mHandler.post(new Runnable() {
          @Override
          public void run() {
            logsOutput.append("+ Request for " + url + "\n");
          }
        });

        List<String> adServers = loadAdServersFromAssets(view.getContext());
        if (blockerEnabled != false && adServers != null  && adServers.size() > 0) {
          for (String adServerUrl : adServers) {
            if (url.toLowerCase().contains(adServerUrl)) {
              final String auxAdServerUrl = adServerUrl;
              final String auxUrl = url;
              final Handler mHandler2 = new Handler(Looper.getMainLooper());
              mHandler2.post(new Runnable() {
                @Override
                public void run() {
                  logsOutput.append("- PANTHER_AD_TRACKER_BLOCKER: " + auxUrl + " blocked using regex /" + auxAdServerUrl + "/gi\n");
                }
              });

              String adBlockerCode = "" 
                + "<!doctype html><html><head><meta charset='utf-8'></head>"
                + "<body><h1>Panther AD Blocker</h1>" 
                + "Blocked '" + adServerUrl + "' AD Server."
              + "</body></html>";
              InputStream adBlockerPage = new ByteArrayInputStream(adBlockerCode.getBytes(StandardCharsets.UTF_8));
              return new WebResourceResponse("text/html", "utf-8", adBlockerPage);
            }
          }
        }
       /* Inyect JS here: */
        //if (url.endsWith(".html") || url.endsWith(".htm") || url.endsWith("/") || url.endsWith("/javascript")) { // add php etc
          try {
            InputStream inputStream = new URL(url).openStream();
            String html = readStream(inputStream);

            if (blockFingerprintEnabled != false && html.contains("<html") && html.contains("<head")) {
            // Modifica el HTML para incluir el script
            String injectedScript = 
              "<script>"
              + "(function() {"
                + "const fakeBattery = {"
                + "  level: 0.75,"
                + "  charging: true,"
                + "  chargingTime: 0,"
                + "  dischargingTime: Infinity,"
                + "  addEventListener: function() {}"
                + "};"
                + "Object.defineProperty(navigator, 'getBattery', {"
                + "  value: function() {"
                + "    return Promise.resolve(fakeBattery);"
                + "  }"
                + "});"
                + "if ('BatteryManager' in window) {"
                + "  window.BatteryManager = fakeBattery;"
                + "}"
              + "})();"
            + "</script>";

            /* Debug */
            final String auxUrl = url;
            final Handler auxHandler = new Handler(Looper.getMainLooper());
            auxHandler.post(new Runnable() {
              @Override
              public void run() {
                logsOutput.append("- PANTHER_FINGERPRINT_BLOCKER: " + auxUrl + " page was modified to prevent fingerprint");
              }
            });


            String replacement = "<head>";
            /*if (html.contains("<meta charset=\"UTF-8\">")) {
              replacement = "<meta charset=\"UTF-8\">";
            } else if (html.contains('<meta charset="utf-8">')) {
              replacement = "<meta charset=\"utf-8\">";
            } else {
              
            }*/ 

            String modifiedHtml = html.replace(replacement,/* injectedScript +*/ replacement);
            return new WebResourceResponse("text/html", "utf-8", new ByteArrayInputStream(modifiedHtml.getBytes(StandardCharsets.UTF_8)));
           }
          } catch (IOException e) {

          }
        //}
        /* End JS injection */
        return super.shouldInterceptRequest(view, request);
      }


      private List<String> loadAdServersFromAssets(Context context) {
        List<String> adServers = new ArrayList<>();
        BufferedReader reader = null;
        try {
          InputStream is = context.getAssets().open("adservers.txt");
          reader = new BufferedReader(new InputStreamReader(is));
          String line;
          while ((line = reader.readLine()) != null) {
            adServers.add(line.trim()); 
          }
        } catch (IOException e) {
          e.printStackTrace();
        } finally {
          if (reader != null) {
            try {
              reader.close();
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        }
        return adServers;
      }


      @Override 
      public void onPageFinished(WebView view, String url) {
        final String auxUrl = url;
        final Handler mHandler = new Handler(Looper.getMainLooper());
        mHandler.post(new Runnable() {
          @Override
          public void run() {
            logsOutput.append("-> " + auxUrl + " loaded\n\n");
            omnibox.setText(auxUrl);
          }
        });

        super.onPageFinished(view, url);
      }

      @Override
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
        final String auxUrl = url;
        final Handler mHandler = new Handler(Looper.getMainLooper());
        mHandler.post(new Runnable() {
          @Override
          public void run() {
            logsOutput.append("> Redirecting to " + auxUrl + "\n");
          }
        });

        return false;
      }
    });

    Panther.setWebChromeClient(new WebChromeClient() { 
      @Override
      public void onPermissionRequest(final PermissionRequest request) {
        final String[] requestedPermissions = request.getResources();
        
        new AlertDialog.Builder(MainActivity.this)
        .setTitle("Permission Request")
        .setMessage("The website is requesting permission to access device resources.")
        .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            request.grant(requestedPermissions);
          }
        })
        .setNegativeButton("Deny", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            request.deny();
          }
        })
        .show();
      }
 
      @Override
      public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        String message = consoleMessage.message() + " -- From line "
          + consoleMessage.lineNumber() + " of "
        + consoleMessage.sourceId();
        consoleOutput.append(message + "\n");
        return true;
      }

      private boolean dialogShowing = false;

      @Override
      public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
        if (!dialogShowing) {
          dialogShowing = true;
          AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
          final TextView textView = new TextView(MainActivity.this);
          textView.setText(message);
          textView.setTypeface(Typeface.MONOSPACE);
          textView.setPadding(32, 32, 32, 32);
          textView.setTextIsSelectable(true);

          ScrollView scrollView = new ScrollView(MainActivity.this);
          scrollView.addView(textView);

          builder.setView(scrollView).setPositiveButton("OK", new DialogInterface.OnClickListener() {
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
          builder.setMessage(message).setView(input).setPositiveButton("OK", new DialogInterface.OnClickListener() {
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
    omnibox = (EditText) findViewById(R.id.url);
    url.setText("PANTHER");


    Panther.loadUrl("https://html.duckduckgo.com/html/");
    //Panther.loadUrl("file:///android_asset/landing/index.html");

    final ValueCallback < String > resultCallback = new ValueCallback < String > () {
      @Override
      public void onReceiveValue(String value) {
        consoleOutput.append("Result: " + value + "\n");
      }
    };

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

    if (UAI.startsWith("#list")) {
      final ValueCallback < String > resultCallback = new ValueCallback < String > () {
        @Override
        public void onReceiveValue(String value) {}
      };
      Panther.evaluateJavascript("alert('Available Urls\\n\\n"
        + "#code           Show Source Code\\n"
        + "#cookie         Show The Cookies\\n"
        + "#clearconsole   Clear The Console\\n"
        + "#clearlogs      Clear The Logs\\n"
        + "#url            Shows Current Url\\n"
        + "')",
      resultCallback); 
    } else if (UAI.startsWith("#clearconsole")) {
      consoleOutput.setText("Use the url #clearconsole to clear\n\n\n\n\n\n\n\n");
    } else if (UAI.startsWith("#clearlogs")) {
      logsOutput.setText("! Use the url #clearlogs to clear\n\n\n");
    } else if (UAI.startsWith("#url")) {
      final ValueCallback < String > resultCallback = new ValueCallback < String > () {
        @Override
        public void onReceiveValue(String value) {}
      };
      Panther.evaluateJavascript("alert(window.location)", resultCallback); 
    } else if (UAI.startsWith("www.")) {
      UAI = "https://" + UAI;
      Panther.loadUrl(UAI);
    } else if (UAI.startsWith("http://") || UAI.startsWith("https://") || UAI.startsWith("file://")) {
      Panther.loadUrl(url.getText().toString());
    } else if (UAI.startsWith("#cod")) {
     final ValueCallback < String > resultCallback = new ValueCallback < String > () {
        @Override
        public void onReceiveValue(String value) {}
      };
      Panther.evaluateJavascript("alert(document.querySelectorAll('html')[0].outerHTML)", resultCallback);  
    } else if (UAI.startsWith("#cookie")) {
      final ValueCallback < String > resultCallback = new ValueCallback < String > () {
        @Override
        public void onReceiveValue(String value) {}
      };
      Panther.evaluateJavascript("alert(document.cookie)", resultCallback);  
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

  /* For js injection */
  private String readStream(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line).append("\n");
        }
        reader.close();
        return result.toString();
    }
}
