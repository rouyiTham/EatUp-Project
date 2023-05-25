const { MongoClient } = require("mongodb");
module.exports = {
  MongoDB_ecommerce: MongoDB_ecommerce,
  MongoDB_transaction: MongoDB_transaction,
  MongoDB_getData: MongoDB_getData,
  reassign: reassign,
  inventory_overall: inventory_overall,
};

//https://www.mongodb.com/blog/post/quick-start-nodejs-mongodb-how-to-get-connected-to-your-database
//test case to ensure database is connected

//MongoDB_transaction(8292550568944);
/*setTimeout(() => {
  MongoDB_getData();
}, 2000);
setTimeout(() => {
  reassign();
}, 3500);*.

/*MongoDB_transaction(4981829262400);
setTimeout(() => {
  MongoDB_getData();
}, 3500);
setTimeout(() => {
  global.returnArray = JSON.stringify(global.sampleListTwo);
  console.log(global.returnArray);
}, 7000);*/

global.transaction_items_queried = "A";
global.listItems = "x";
global.sampleListTwo = [];
global.returnArray = "x";

function MongoDB_ecommerce() {
  async function test() {
    const uri =
      "mongodb+srv://eatup:eatup@ecommerce-cluster-1.wkax8qo.mongodb.net/?retryWrites=true&w=majority";
    const client = new MongoClient(uri);

    try {
      await client.connect();

      await listDatabases(client);
    } catch (e) {
      console.error(e);
    } finally {
      await client.close();
    }
  }

  /*test().catch(console.error);*/

  async function listDatabases(client) {
    let databasesList = await client.db().admin().listDatabases();

    console.log("Databases:");
    databasesList.databases.forEach((db) => console.log(` - ${db.name}`));
  }

  //logs product name to the console as test case no.2
  async function mainTwo() {
    const uri =
      "mongodb+srv://eatup:eatup@ecommerce-cluster-1.wkax8qo.mongodb.net/?retryWrites=true&w=majority";
    const client = new MongoClient(uri);

    const databaseName = client.db("ecommerce_inventory_1");
    const collectionName = databaseName.collection("ecommerce_backend");

    async function listItemsFunc(cName) {
      let itemsTest = await cName.find({});
      await itemsTest.forEach((db) =>
        console.log(db.product_name, db.product_id, db.price)
      );
    }

    await listItemsFunc(collectionName);
  }

  //product object constructors
  class objectList {
    constructor(id, name, price, category, image_link) {
      this.id = id;
      this.name = name;
      this.price = price;
      this.category = category;
      this.image_link = image_link;
    }

    get get_id() {
      return this.id;
    }
  }

  async function mainThree() {
    const uri =
      "mongodb+srv://eatup:eatup@ecommerce-cluster-1.wkax8qo.mongodb.net/?retryWrites=true&w=majority";
    const client = new MongoClient(uri);

    const databaseName = client.db("ecommerce_inventory_1");
    const collectionName = databaseName.collection("ecommerce_backend_2");

    async function listItemsFunc(collectionName) {
      var sampleListTwo = [];
      let itemsTest = await collectionName.find({});
      await itemsTest.forEach((db) => {
        const itemPlace = new objectList(
          db.product_id,
          db.product_name,
          db.price,
          db.category,
          db.image_link
        );
        sampleListTwo.push(itemPlace);
      });
      function reassign() {
        global.listItems = sampleListTwo;
        console.log(global.listItems);
      }
      await reassign();
      /*setTimeout(() => {
        reassign();
      }, 500);*/
    }

    await listItemsFunc(collectionName);
  }

  async function overallFunction() {
    function promiseOne() {
      return new Promise((resolve, reject) => {
        const connectionTest = test();

        connectionTest
          .then((data) => {
            console.log("Connection is successful!");
          })

          .catch((err) => {
            console.log(err);
          });
      });
    }

    function promiseTwo() {
      return new Promise((resolve, reject) => {
        const queryTest = mainTwo();

        queryTest
          .then((data) => {
            console.log("Your query was successful!");
          })

          .catch((err) => {
            console.log(err);
          });
      });
    }

    function promiseThree() {
      return new Promise((resolve, reject) => {
        const dataTest = mainThree();

        dataTest
          .then((data) => {
            console.log("Requested array of data.");
          })

          .catch((err) => {
            console.log(err);
          });
      });
    }

    setTimeout(() => {
      promiseOne();
    }, 0);

    setTimeout(() => {
      promiseTwo();
    }, 2000);

    setTimeout(() => {
      promiseThree();
    }, 4000);
  }

  overallFunction();

  /* Succesful execution of the functions shall yield the following order:
  Hello its me!
  Database List - Connection is Successfull
  Object name, id and price - Query was successfull
  ObjectList Array - Requested array of data */
}

function MongoDB_transaction(barcode) {
  return new Promise((resolve, reject) => {
    const barcode_id = barcode;
    async function mainOne() {
      const uri =
        "mongodb+srv://eatup:eatup@ecommerce-cluster-1.wkax8qo.mongodb.net/?retryWrites=true&w=majority";
      const client = new MongoClient(uri);

      const databaseName = client.db("ecommerce_inventory_1");
      const collectionName = databaseName.collection("transaction_mocks");

      async function listItemsFunc(cName) {
        let itemsTest = await cName.find({ transaction_id: barcode_id });
        await itemsTest.forEach((db) => {
          const items = new transactionItems(
            db.transaction_id,
            db.item_1,
            db.item_2,
            db.item_3,
            db.item_4,
            db.item_5
          );
          global.transaction_items_queried = items;
        });
      }

      await listItemsFunc(collectionName);
      console.log(global.transaction_items_queried);
      resolve();
    }

    //product object constructors
    class transactionItems {
      constructor(transaction_id, item_1, item_2, item_3, item_4, item_5) {
        (this.transaction_id = transaction_id),
          (this.item_1 = item_1),
          (this.item_2 = item_2),
          (this.item_3 = item_3),
          (this.item_4 = item_4),
          (this.item_5 = item_5);
      }
    }

    mainOne();
  });
}

async function MongoDB_getData() {
  const getDataPromise = new Promise((resolve, reject) => {
    const uri =
      "mongodb+srv://eatup:eatup@ecommerce-cluster-1.wkax8qo.mongodb.net/?retryWrites=true&w=majority";
    const client = new MongoClient(uri);

    const databaseName = client.db("ecommerce_inventory_1");
    const collectionName = databaseName.collection("ecommerce_backend_2");

    class fullTransactionDetails {
      constructor(
        mocksQueried,
        product_id,
        product_name,
        food_identifier,
        expiration_date
      ) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.identifier = food_identifier;
        this.quantity = mocksQueried;
        this.expiration_date = expiration_date;
      }
    }

    async function applyFunctionToPropertiesExcludingKeys(obj, excludeKeys) {
      for (let key in obj) {
        if (!excludeKeys.includes(key)) {
          //constructor function here
          async function listItemsFunc(cName) {
            //var sampleListTwo = [];
            let itemsTest = await cName.find({
              product_id: transaction_items_queried[key].product_id,
              batch_no: transaction_items_queried[key].batch_no,
              //product_id: parameterList[key].product_id,
              //batch_no: parameterList[key].batch_no,
            });

            await itemsTest.forEach((db) => {
              const itemPlace = new fullTransactionDetails(
                transaction_items_queried[key].quantity,
                db.product_id,
                db.product_name,
                db.food_identifier,
                db.expiration_date
              );
              global.sampleListTwo.push(itemPlace);
            });

            /*setTimeout(() => {
              console.log(global.sampleListTwo);
            }, 500);*/
          }

          await listItemsFunc(collectionName);
          console.log(global.sampleListTwo);
        }
      }
      resolve(global.sampleListTwo);
      return global.sampleListTwo;
    }

    applyFunctionToPropertiesExcludingKeys(
      global.transaction_items_queried,
      "transaction_id"
    );
  });

  getDataPromise.then(() => {
    console.log("ABCD");
    console.log(global.sampleListTwo);
    return global.sampleListTwo;
  });
}

function reassign() {
  return new Promise((resolve, reject) => {
    /*global.returnArray = JSON.stringify(global.sampleListTwo);
    console.log("ressigned");
    console.log(global.returnArray);
    resolve();
    return global.returnArray;*/

    if ((global.sampleListTwo = [])) {
      setTimeout(() => {
        global.returnArray = JSON.stringify(global.sampleListTwo);
        console.log("ressigned");
        console.log(global.returnArray);
        resolve();
        return global.returnArray;
      }, 1500);
    } else {
      global.returnArray = JSON.stringify(global.sampleListTwo);
      console.log("ressigned");
      console.log(global.returnArray);
      resolve();
      return global.returnArray;
    }
  });
}

/*async function inventory_overall(query_string) {
  const query_string_parsed = parseInt(query_string);
  console.log(query_string_parsed);
  console.log(typeof query_string_parsed);
  await MongoDB_transaction(query_string_parsed);
  console.log("transaction completed");
  await MongoDB_getData();
  console.log("getData completed");
  await reassign();
  console.log("reassigned overall");
}*/

async function inventory_overall(query_string) {
  const query_string_parsed = parseInt(query_string);
  console.log(query_string_parsed);
  console.log(typeof query_string_parsed);
  MongoDB_transaction(query_string_parsed)
    .then(() => {
      console.log("transaction completed");
      MongoDB_getData();
    })
    .then(() => {
      console.log("getData completed");
      reassign();
    })
    .then(() => {
      console.log("reassigned overall");
    })
    .catch((err) => {
      console.log(err.message);
    });
}

//inventory_overall(4981829262400);
//MongoDB_ecommerce();
