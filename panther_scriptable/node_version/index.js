const WebSocket = require("ws");

const wss = new WebSocket.Server({ port: 13370 });

wss.on("connection", ws => {
  console.log("Panther is connected");
  ws.on("message", message => {
    console.log(`Received message: ${message}`);
    // Handle commands / send instructions
    ws.send(`Server received: ${message}`);
  });

  ws.on("close", () => {
    console.log("Panther disconnected");
  });

  ws.send("Panther is now connected to Panther_Scriptable_Node");
});

console.log(`Panther Scriptable is Ready. Start the connection from Panther`);

