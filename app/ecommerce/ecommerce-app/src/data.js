const { MongoClient } = require("mongodb");

global.listItems = "Hello its me!";

//https://www.mongodb.com/blog/post/quick-start-nodejs-mongodb-how-to-get-connected-to-your-database
//test case to ensure database is connected

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
    itemsTest.forEach((db) =>
      console.log(db.product_name, db.product_id, db.price)
    );
  }

  listItemsFunc(collectionName);
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
}

async function mainThree() {
  const uri =
    "mongodb+srv://eatup:eatup@ecommerce-cluster-1.wkax8qo.mongodb.net/?retryWrites=true&w=majority";
  const client = new MongoClient(uri);

  const databaseName = client.db("ecommerce_inventory_1");
  const collectionName = databaseName.collection("ecommerce_backend");

  async function listItemsFunc(collectionName) {
    let itemsTest = await collectionName.find({});
    itemsTest.forEach((db) => {
      var sampleListTwo = [];
      const itemPlace = new objectList(
        db.product_id,
        db.product_name,
        db.price,
        db.category,
        db.image_link
      );
      //console.log(itemPlace);
      sampleListTwo.push(itemPlace);
      //console.log(sampleListTwo);
      //console.log(listItems);
      global.listItems = sampleListTwo;
      console.log(global.listItems);
    });
  }

  listItemsFunc(collectionName);
}

function overallFunction() {
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
    new Promise((resolve, reject) => {
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
    new Promise((resolve, reject) => {
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
  }, 1000);

  setTimeout(() => {
    promiseThree();
  }, 1500);
}

console.log(global.listItems);
overallFunction();

/* Succesful execution of the functions shall yield the following order:
Hello its me!
Database List - Connection is Successfull
Object name, id and price - Query was successfull
ObjectList Array - Requested array of data */
