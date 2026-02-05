# demo-for-ci

# CI Server Setup

### 1. Start the Server
Compile and launch the Java Jetty server:
```bash
mvn compile exec:java
```

### 2. Install ngrok
To bridge the gap between GitHub and your local Mac (the "Cloud Server" from our plan), you need to install ngrok. This tool creates a secure tunnel to your local port :
```bash
brew install ngrok/ngrok/ngrok

```

### 3. Authenticate your Account
Before you can use the tunnel, you must link your local installation to your ngrok account. 
Create a ngrok account if you dont already have one. Replace <YOUR_TOKEN_HERE> with the token from your ngrok dashboard:
```bash

ngrok config add-authtoken <YOUR_TOKEN_HERE>

```

### 4. Launch your tunnel
Finally, start the tunnel to make your local server visible to the internet:
```bash

ngrok http 8080

```

### 4. Setup the webbhook
Copy the Forwarding URL from your ngrok terminal (e.g., https://xxxx.ngrok-free.dev).

Go to your GitHub repository: Settings > Webhooks > Add webhook.

Paste the URL into the Payload URL field.

Set Content type to application/json.

Click Add webhook.