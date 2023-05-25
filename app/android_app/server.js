const express = require("express");
const app = express();
const http = require("http");
const inventory_logic = require("./inventory_logic_API");
const fs = require("fs");

global.sampleListTwo = [];
global.returnArray = "returnArray";
transaction_items_queried = "Queried";

/*app.get("/", (req, res) => {
  res.send("Hello from App Engine!");
});*/

// Listen to the App Engine-specified port, or 8080 otherwise
/*const PORT = process.env.PORT || 8080;
app.listen(PORT, () => {
  console.log(`Server listening on port ${PORT}...`);
}); */

/* MongoDB_transaction(8292550568944);
setTimeout(() => {
  MongoDB_getData();
}, 2000);

setTimeout(() => {
  global.returnArray = JSON.stringify(global.sampleListTwo);
  console.log(global.returnArray);
}, 3500); */

function dataProcessing(data) {
  /*inventory_logic.MongoDB_transaction(data);
  setTimeout(() => {
    inventory_logic.MongoDB_getData();
  }, 1000);

  setTimeout(() => {
    global.returnArray = JSON.stringify(global.sampleListTwo);
    console.log(global.returnArray);
  }, 2000);

  setTimeout(() => {
    return { message: global.returnArray };
  }, 2250);*/
  inventory_logic.inventory_overall(data);
}

app.all("/dataQuery/:transaction_id", function (req, res) {
  let id = parseInt(req.params.transaction_id);
  /*setTimeout(() => {
    dataProcessing(id);
  }, 500);
  setTimeout(() => {
    console.log(req.params.transaction_id);
    res.set({
      "Content-Type": "application/json",
    });
    res.send(global.returnArray);
  }, 3000);
  setTimeout(() => {
    global.sampleListTwo = [];
    global.returnArray = "returnArray";
    transaction_items_queried = "Queried";
  }, 5000);*/
  const query_promise = new Promise((resolve, reject) => {
    dataProcessing(id);
    resolve();
  });

  const res_send = new Promise((resolve, reject) => {
    /* console.log(req.params.transaction_id);
    res.set({
      "Content-Type": "application/json",
    });
    res.send(global.returnArray);
    console.log("response sent");
    resolve(); */
    if ((global.returnArray = "returnArray")) {
      setTimeout(() => {
        console.log(req.params.transaction_id);
        res.set({
          "Content-Type": "application/json",
        });
        res.send(global.returnArray);
        console.log("response sent");
        resolve();
      }, 2000);
    } else {
      console.log(req.params.transaction_id);
      res.set({
        "Content-Type": "application/json",
      });
      res.send(global.returnArray);
      console.log("response sent");
      resolve();
    }
  });

  const var_clear = new Promise((resolve, reject) => {
    global.sampleListTwo = [];
    global.returnArray = "returnArray";
    transaction_items_queried = "Queried";
    console.log(global.sampleListTwo);
    console.log(global.returnArray);
    console.log(transaction_items_queried);
    console.log("variables cleared");
    resolve();
  });

  query_promise
    .then(() => {
      console.log("query_promise completed");
      res_send;
    })
    .then(() => {
      var_clear;
    })
    .catch((err) => {
      console.log(err.message);
    });
});

/*app.all("/dataQuery/:transaction_id([0-9]+)", function (req, res) {
  let id = parseInt(req.params.transaction_id);
  console.log(typeof id);
  setTimeout(() => {
    inventory_logic.MongoDB_transaction(id);
  }, 1000);
  setTimeout(() => {
    inventory_logic.MongoDB_getData();
  }, 5000);
});*/

//app.listen(3000);

//Starts the server
const PORT = parseInt(process.env.PORT) || 8080;
app.listen(PORT, () => {
  console.log(`App listening on port ${PORT}`);
  console.log("Press Ctrl+C to quit.");
});
