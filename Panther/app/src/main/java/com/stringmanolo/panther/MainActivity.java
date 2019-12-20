package com.stringmanolo.panther;

import com.stringmanolo.panther.R;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.webkit.WebChromeClient;
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

public class MainActivity extends Activity
{
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
    public void onCreate(Bundle savedInstanceState)
     {
      super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
 
        Panther = (WebView)findViewById(R.id.webkit);
 
 rectanguloMenuIcono = (View)findViewById(R.id.RectanguloMenuIcono);
rectanguloMenuIcono.setVisibility(View.INVISIBLE);
 
 
 listView=(ListView)findViewById(R.id.listView);  
        textView=(TextView)findViewById(R.id.textView);  

listItem = getResources().getStringArray(R.array.array_technology);  

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,  
                android.R.layout.simple_list_item_1, android.R.id.text1, listItem);  
        listView.setAdapter(adapter);  
  
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
   {  
            @Override  
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
      {    
                 String value=adapter.getItem(position);  
                Toast.makeText(getApplicationContext(),value,Toast.LENGTH_SHORT).show();  
                
       

       
if (value.equals("Motor De Busqueda"))
{
 
intentMotorDeBusq = new Intent(MainActivity.this,ActividadMotorDeBusqueda.class);

startActivityForResult(intentMotorDeBusq, CODEMotorIntent);


}           
      
if (value.equals("Codigo Fuente"))
{
 
Panther.loadUrl("javascript:document.write(\"<xmp contenteditable=\\\"true\\\" style=\\\"width:100%;height:300px;overflow-y:scroll;background-color:#336699;color:white;align:center\\\">\"+document.getElementsByTagName('html')[0].outerHTML+\"</xmp>\"+document.getElementsByTagName('html')[0].outerHTML);");  


}           
          
if (value.equals("Hacking"))
{
 
 
 
intentHacking = new Intent(MainActivity.this,ActividadHacking.class);
intentHacking.putExtra("CurrentUA", CuserAgent);
startActivityForResult(intentHacking, CODEHackingIntent);


}    
      
             
       }
   });
 
listView.setVisibility(View.INVISIBLE);
textView.setVisibility(View.INVISIBLE);
 
 
 
if (Build.VERSION.SDK_INT < 18)
{
Panther.getSettings().setRenderPriority(RenderPriority.HIGH);            
  /*  Panther.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH); */
}
  
 
Panther.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

Panther.getSettings().setLoadWithOverviewMode(true);

Panther.getSettings().setDomStorageEnabled(true);

Panther.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

Panther.setScrollbarFadingEnabled(true);
/*
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
{ Panther.setLayerType(View.LAYER_TYPE_HARDWARE, null);
}

else
{    Panther.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
}
*/
Panther.clearHistory(); Panther.clearCache(true);
Panther.clearFormData();
        Panther.getSettings().setJavaScriptEnabled(true);

String userAgent = new WebView(this).getSettings().getUserAgentString();


/*String userAgentModificado = "";

int x = 0;
for (int i=0; i<userAgent.length(); ++i)
{
   if(userAgent.charAt(i) == 'V' && userAgent.charAt(1+i) == 'e' && userAgent.charAt(2+i) == 'r' && userAgent.charAt(6+i) == 'n')
   {
      while(x!=i)
      {
      userAgentModificado += userAgent.charAt(x);
      ++x;
      }
   }
   
}
*/

String userAgentModificado=userAgent;

userAgentModificado+=" Panther/1.0.0";

CuserAgent = userAgentModificado; Panther.getSettings().setUserAgentString(userAgentModificado);
 
Panther.getSettings().setUseWideViewPort(true);    Panther.getSettings().setLoadWithOverviewMode(true);
      Panther.getSettings().setBuiltInZoomControls(true); 


         Panther.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                return false;
            }   
             
        });
 
iconoBarra = (ImageView) findViewById(R.id.favicon);
 
url = (EditText) findViewById(R.id.url);

        url.setText("PANTHER");
 
 
Panther.loadUrl("http://www.google.com");


/*
progressBar = (ProgressBar) findViewById(R.id.progressbar);
         
        Panther.setWebChromeClient(new WebChromeClient() 
        {
            @Override
            public void onProgressChanged(WebView view, int progress) 
            {               
progress=0;                progressBar.setProgress(0);
                progressBar.setVisibility(View.VISIBLE);
                MainActivity.this.setProgress(progress * 1000);
 
                progressBar.incrementProgressBy(progress);
 
                if(progress == 100)
                {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

*/
    }


 
    
public void ir(View view)
    {
       
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(url.getWindowToken(), 0);
 
      
/*        ((Button) MainActivity.this.findViewById(R.id.buttonDetener)).setEnabled(true);
*/ 

/* UrlAntesIr */
UAI = url.getText().toString();



if (UAI.charAt(0) == 'w' && UAI.charAt(1) == 'w' && UAI.charAt(2) == 'w' && UAI.charAt(3) == '.')
{
UAI = "https://" + UAI;
Panther.loadUrl(UAI);
}

else if (UAI.charAt(0) == 'h' && UAI.charAt(1) == 't' && UAI.charAt(2) == 't' && UAI.charAt(3) == 'p' &&
UAI.charAt(4) == ':' &&
UAI.charAt(5) == '/' &&
UAI.charAt(6) == '/')
{
Panther.loadUrl(url.getText().toString());
}

else if (UAI.charAt(0) == 'h' && UAI.charAt(1) == 't' && UAI.charAt(2) == 't' && UAI.charAt(3) == 'p' &&
UAI.charAt(4) == 's' &&
UAI.charAt(5) == ':' &&
UAI.charAt(6) == '/' &&
UAI.charAt(7) == '/')
{
Panther.loadUrl(url.getText().toString());
}


else if (UAI.charAt(0) == 'f' && UAI.charAt(1) == 'i' && UAI.charAt(2) == 'l' && UAI.charAt(3) == 'e' &&
UAI.charAt(4) == ':' &&
UAI.charAt(5) == '/' &&
UAI.charAt(6) == '/' &&
UAI.charAt(7) == '/')
{
Panther.loadUrl(url.getText().toString());
}

/* #codigo */
else if (UAI.charAt(0) == '#' && UAI.charAt(1) == 'c' && UAI.charAt(2) == 'o' && UAI.charAt(3) == 'd')
{

UAI="javascript:document.write(\"<xmp contenteditable=\\\"true\\\" style=\\\"width:100%;height:300px;overflow-y:scroll;background-color:black;color:green;align:center\\\">\"+document.getElementsByTagName('html')[0].outerHTML+\"</xmp>\"+document.getElementsByTagName('html')[0].outerHTML);";
           
Panther.loadUrl(UAI);

UAI="";
}


else if (UAI.charAt(0) == '#' && UAI.charAt(1) == 'c' && UAI.charAt(2) == 'o' && UAI.charAt(3) == 'o' && UAI.charAt(4) == 'k' && UAI.charAt(5) == 'i' && UAI.charAt(6) == 'e')
{

UAI="javascript:document.write(\"<xmp contenteditable=\\\"true\\\" style=\\\"width:100%;height:300px;overflow-y:scroll;background-color:black;color:green;align:center\\\">\"+document.cookie+\"</xmp>\"+document.getElementsByTagName('html')[0].outerHTML);";
           
Panther.loadUrl(UAI);

UAI="";
}

/* #archivo */
else if (UAI.charAt(0) == '#' && UAI.charAt(1) == 'a' && UAI.charAt(2) == 'r' && UAI.charAt(3) == 'c' &&
UAI.charAt(4) == 'h')
{

intentArchivos = new Intent(MainActivity.this, ActividadArchivos.class);

startActivity(intentArchivos);
   
}




/*Ver Codigo + URL
else if (UAI.charAt(0) == 'v' && UAI.charAt(1) == 'i' && UAI.charAt(2) == 'e' && UAI.charAt(3) == 'w' && UAI.charAt(4) == '-' && UAI.charAt(5) == 's' && UAI.charAt(6) == 'o' && UAI.charAt(7) == 'u' &&
UAI.charAt(8) == 'r' && UAI.charAt(9) == 'c' && UAI.charAt(10) == 'e' && UAI.charAt(11) == ':')
{
VS="";
   for(int i = 12; i < UAI.length(); ++i)
   {
   VS+=UAI.charAt(i);
   }


Panther.loadUrl(VS);

UAI="javascript:document.write(\"<xmp contenteditable=\\\"true\\\" style=\\\"width:90%;height:300px;overflow-y:scroll;padding:10px;background-color:black;color:yellow;align:center\\\">\"+document.getElementsByTagName('html')[0].outerHTML+\"</xmp>\"+document.getElementsByTagName('html')[0].outerHTML);";



   
           
Panther.loadUrl(UAI);

UAI="";
}
*/


else if (UAI.charAt(0) == '#' && UAI.charAt(1) == 'c' && UAI.charAt(2) == 'a' && UAI.charAt(3) == 'k' &&
UAI.charAt(4) == 'e')
{


UAI="javascript:document.cookie='enableCAKE=SI;path=/;expires=Thu, 17 Sep 2021 22:23:24'";
           
Panther.loadUrl(UAI);

UAI="";


}



else
{
/* SSE Selected Search Engine */
UAI = SSE + UAI;
Panther.loadUrl(UAI);
}
/* url.setText(Panther.getUrl()); */
     }
 
public void Config(View view)
{
/*
Panther.loadUrl("file:///android_asset/Config.html");
*/
listView.setVisibility(View.INVISIBLE);
textView.setVisibility(View.INVISIBLE);
 
 
   if(rectanguloMenuIcono.getVisibility() == View.INVISIBLE) 
   { rectanguloMenuIcono.setVisibility(View.VISIBLE);
listView.setVisibility(View.VISIBLE);
textView.setVisibility(View.VISIBLE);  
   }

   else
   { rectanguloMenuIcono.setVisibility(View.INVISIBLE);   listView.setVisibility(View.INVISIBLE);
textView.setVisibility(View.INVISIBLE);   
   }


}

 
 
 /*
    public void anterior(View view)
    {
        Panther.goBack();
    }
 
    public void siguiente(View view)
    {
        Panther.goForward();
    }
 
    public void detener(View view)
    {
        Panther.stopLoading();
    } 
    
}
    */
    
    
    @Override
public void onBackPressed()
{
   if(Panther.canGoBack())
   {
   Panther.goBack();
   }

   else
   {
   super.onBackPressed();
   }
}



 @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) 
{
super.onActivityResult(requestCode, resultCode, data); 

if (requestCode == CODEMotorIntent)
{
   if (resultCode == RESULT_OK)
   { 
SSE = data.getStringExtra("OpcionMotor"); 
   }
}
     
if (requestCode == CODEHackingIntent)
{
   if (resultCode == RESULT_OK)
   { 
userAgentHacking=data.getStringExtra("OpcionHacking"); 
 Panther.getSettings().setUserAgentString(userAgentHacking);   
     
    
   }
}
}


 

    
    
    









}
