const { save } = require('mongodb/lib/operations/collection_ops');

const MongoClient = require("mongodb").MongoClient;
const url = "mongodb://localhost:27017";
const dbName = "recipedb";
const recipeCollectionName = 'recipes';
const { Recipe, Ingredient, Instruction } = require('./recipe.spec.js');

class RecipeService {

    client;
    db;
    collection;

    constructor() {
    }

    /**
     * Save recipe to database (Returns a promise)
     */
    async store(recipe) {
        var client = await MongoClient.connect(url);
        var db = client.db(dbName);
        var collection = db.collection(recipeCollectionName);
        return await collection.insertOne(recipe);
    }

    async find(recipeId) {
        var client = await MongoClient.connect(url);
        var db = client.db(dbName);
        var collection = db.collection(recipeCollectionName);
        return await collection.findOne({ id: recipeId }, { projection: { _id: 0 } });
    }

    /**
        * Load database with new recipe. This one returns a promise which can be resolved by the executor.
        * We don't have to return promise here but I just wanted to test the variants await (Returns promise) and return Promise
    */
    async load() {
        let ingredient = new Ingredient('Wheat noodles', 1, 'st');
        let instruction = new Instruction('Boil the noodles');
        let recipe = new Recipe('1', 'Chow mein noodles', [instruction], [ingredient]);
        return await this.store(recipe);
    }


}

module.exports = RecipeService;