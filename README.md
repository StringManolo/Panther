# Panther &nbsp; &nbsp; &nbsp; &nbsp; [![Download Panther](https://img.shields.io/badge/Download-Panther-blue)](https://github.com/StringManolo/Panther/releases/download/V1.0.6/Panther_stable_ver.1.0.6.apk)


Panther is a privacy-focused Android browser based on WebView.  
  

### Features
- Blocker
  - Blocks common AD servers by replacing the downloaded AD by a custom file
  - Blocks common Trackers by replacing the downloaded javascript by a custom file
  - You can Enable/Disable the Blocker
  - You can try to access [https://google.com/search?q=Panther_Blocker_Test](https://google.com/search?q=Panther_Blocker_Tester) to confirm that is working. (The blocker will block any url that contains the "Panther_Blocker_Test" text)
  - List of blocked strings: [Panther/Panther/app/src/main/assets
/adservers.txt](https://github.com/StringManolo/Panther/blob/master/Panther/app/src/main/assets/adservers.txt)
<br>

- Console support
  - Add a console to view the console.log in Android, run commands, etc
<br>  
  
- Basic Browser Functionality
<br>

- Hardcoded User-Agent by default:
  - User-Agents typically reveal your operating system and browser version to every website you visit.
  - Even browsers like Tor Browser do not fully address this issue. When they do, they only overwrite the HTTP headers to fake it.
  - Panther overwrites the `navigator.userAgent` property to protect you from JavaScript fingerprinting attacks.
  - This enhances your privacy and security, protecting you from automated web exploit kits.
  - Default User-Agent: "Mozilla/5.0 (Linux; Android 14) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.6478.122 Mobile Safari/537.36"
<br>

- Editable User-Agent: _Go to Hacking -> User-Agent -> Set your own and tap the "GO" button_
<br>

- Change Search Engine
<br>

- Disable/Enable Javascript
<br>

- Allows Zoom In/Out even when webpages try to disable it
<br>

- Clears internal history upon opening the browser:
  - Internal history is only saved to allow navigation between pages
<br>

- Clears internal cache upon opening the browser:
  - Internal cache is only saved during the session for faster page loading
<br>

- Special URLs:
  - `#code` - Shows rendered source code
  - `#cookie` - Shows website cookies

