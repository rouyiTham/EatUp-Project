const { MongoClient } = require("mongodb");
//import * as MongoClient from "../../../../node_modules/mongodb/mongodb";
module.exports = { mainThree };

global.listItems = "Hello its me!";
global.listVegetables = "Vegetables";
global.listMeat = "Meat";
global.listPoultry = "Poultry";
global.listFruits = "Fruits";
global.listSeafood = "Seafood";
global.listEggs = "Eggs!";

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

  get get_id() {
    return this.id;
  }
}

/*function objectList(id, name, price, category, image_link) {
  this.id = id;
  this.name = name;
  this.price = price;
  this.category = category;
  this.image_link = image_link;

  Object.defineProperties(this, "id", {
    get: function () {
      return this.id;
    },
  });
} */

async function mainThree() {
  const uri =
    "mongodb+srv://eatup:eatup@ecommerce-cluster-1.wkax8qo.mongodb.net/?retryWrites=true&w=majority";
  const client = new MongoClient(uri);

  const databaseName = client.db("ecommerce_inventory_1");
  const collectionName = databaseName.collection("ecommerce_backend");

  async function listItemsFunc(collectionName) {
    var sampleListTwo = [];
    let itemsTest = await collectionName.find({});
    itemsTest.forEach((db) => {
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

    setTimeout(() => {
      reassign();
    }, 500);
  }

  listItemsFunc(collectionName);
}

async function mainFour() {
  function listItemsUnique() {
    for (let i = 0; i < global.listItems.length - 1; i++) {
      const arrayNo = global.listItems[i];
      const arrayNoAdd = global.listItems[i + 1];

      if (arrayNo.get_id === arrayNoAdd.get_id) {
        global.listItems.splice(i + 1, 1);
        i--;
      } else {
        console.log(arrayNo.get_id);
      }
    }
  }

  listItemsUnique();
}

async function mainFive() {
  const uri =
    "mongodb+srv://eatup:eatup@ecommerce-cluster-1.wkax8qo.mongodb.net/?retryWrites=true&w=majority";
  const client = new MongoClient(uri);

  const databaseName = client.db("ecommerce_inventory_1");
  const collectionName = databaseName.collection("ecommerce_backend");

  async function listVegetables(collectionName) {
    var sampleListThree = [];
    let itemsTest = await collectionName.find({ category: "vegetable" });
    itemsTest.forEach((db) => {
      const itemPlace = new objectList(
        db.product_id,
        db.product_name,
        db.price,
        db.category,
        db.image_link
      );
      sampleListThree.push(itemPlace);
    });
    function reassign() {
      global.listVegetables = sampleListThree;
      console.log("List of vegetables");
      console.log(global.listVegetables);
    }

    setTimeout(() => {
      reassign();
    }, 500);
  }

  async function listMeat(collectionName) {
    var sampleListThree = [];
    let itemsTest = await collectionName.find({ category: "meat" });
    itemsTest.forEach((db) => {
      const itemPlace = new objectList(
        db.product_id,
        db.product_name,
        db.price,
        db.category,
        db.image_link
      );
      sampleListThree.push(itemPlace);
    });
    function reassign() {
      global.listMeat = sampleListThree;
      console.log("List of meat");
      console.log(global.listMeat);
    }

    setTimeout(() => {
      reassign();
    }, 500);
  }

  async function listPoultry(collectionName) {
    var sampleListThree = [];
    let itemsTest = await collectionName.find({ category: "poultry" });
    itemsTest.forEach((db) => {
      const itemPlace = new objectList(
        db.product_id,
        db.product_name,
        db.price,
        db.category,
        db.image_link
      );
      sampleListThree.push(itemPlace);
    });
    function reassign() {
      global.listPoultry = sampleListThree;
      console.log("List of poultry");
      console.log(global.listPoultry);
    }

    setTimeout(() => {
      reassign();
    }, 500);
  }

  async function listFruits(collectionName) {
    var sampleListThree = [];
    let itemsTest = await collectionName.find({ category: "fruits" });
    itemsTest.forEach((db) => {
      const itemPlace = new objectList(
        db.product_id,
        db.product_name,
        db.price,
        db.category,
        db.image_link
      );
      sampleListThree.push(itemPlace);
    });
    function reassign() {
      global.listFruits = sampleListThree;
      console.log("List of fruits");
      console.log(global.listFruits);
    }

    setTimeout(() => {
      reassign();
    }, 500);
  }

  async function listSeafood(collectionName) {
    var sampleListThree = [];
    let itemsTest = await collectionName.find({ category: "seafood" });
    itemsTest.forEach((db) => {
      const itemPlace = new objectList(
        db.product_id,
        db.product_name,
        db.price,
        db.category,
        db.image_link
      );
      sampleListThree.push(itemPlace);
    });
    function reassign() {
      global.listSeafood = sampleListThree;
      console.log("List of seafood");
      console.log(global.listSeafood);
    }

    setTimeout(() => {
      reassign();
    }, 500);
  }

  async function listEggs(collectionName) {
    var sampleListThree = [];
    let itemsTest = await collectionName.find({ category: "eggs" });
    itemsTest.forEach((db) => {
      const itemPlace = new objectList(
        db.product_id,
        db.product_name,
        db.price,
        db.category,
        db.image_link
      );
      sampleListThree.push(itemPlace);
    });
    function reassign() {
      global.listEggs = sampleListThree;
      console.log("List of egg products");
      console.log(global.listEggs);
    }

    setTimeout(() => {
      reassign();
    }, 500);
  }

  setTimeout(() => {
    listVegetables(collectionName);
  }, 500);

  setTimeout(() => {
    listMeat(collectionName);
  }, 1000);

  setTimeout(() => {
    listPoultry(collectionName);
  }, 1500);

  setTimeout(() => {
    listFruits(collectionName);
  }, 2000);

  setTimeout(() => {
    listSeafood(collectionName);
  }, 2500);

  setTimeout(() => {
    listEggs(collectionName);
  }, 3000);
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

  function promiseFour() {
    new Promise((resolve, reject) => {
      const dataSortOne = mainFour();

      dataSortOne
        .then((data) => {
          console.log("Unique product id has been sorted");
        })

        .catch((err) => {
          console.log(err);
        });
    });
  }

  function promiseFive() {
    new Promise((resolve, reject) => {
      const dataSortOne = mainFive();

      dataSortOne
        .then((data) => {
          console.log("Category done");
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
  }, 3000);

  setTimeout(() => {
    promiseFour();
  }, 10000);

  setTimeout(() => {
    promiseFive();
  }, 12000);
}

overallFunction();

/* Succesful execution of the functions shall yield the following order:
Hello its me!
Database List - Connection is Successfull
Object name, id and price - Query was successfull
ObjectList Array - Requested array of data */

/*module.exports = {
  objectList,
  listDatabases,
  mainTwo,
  mainThree,
  mainFour,
  mainFive,
}; */
