# Panther

Panther is a privacy focused Android Browser based on WebView.

### Download Lastest Version
[Panther\_stable\_ver.1.0.1.apk](https://github.com/StringManolo/Panther/releases/download/V1.0.1/Panther_stable_ver.1.0.1.apk)

### Features
- Most Basic Browser Features
- Use Hardcoded UserAgent by default:
  - Usually UserAgents reveal your operative system and browser version to every single web you visit.
  - Even browsers like Tor Browser do not fix this. And when they do, they only overwrite the HTTP headers to fake it.
  - Panther actually overwrites the navigator.useragent property to protect you from javascript fingerprint attacks too.
  - This is important to give you extra privacy and security, protecting you from Automated Web Xploit Kits.
  - "Mozilla/5.0 (Linux; Android 14) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.6478.122 Mobile Safari/537.36"
- Editable UserAgent _Hacking -> User-Agent -> Set your own and tap the "IR" button_
- Change Search Engine
- Allows Zoom In/Out even when webpages try to disable it
- Clears internal history after opening the Browser:
  - Internal history is only saved to allow you to go back and forward between pages
- Clears internal cache after opening the Browser;
  - Internal Cache is only saved during the session for faster loading of pages;
- Special Urls:
  - #code _shows renderized source code_
  - #cookie _shows website cookies_
