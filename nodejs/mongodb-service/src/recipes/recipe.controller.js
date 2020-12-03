var bodyParser = require('body-parser');
var jsonParser = bodyParser.json();

const RecipeService = require('./recipe.service.js');
const RecipeServiceInstance = new RecipeService();

module.exports = function (app) {

    const ROOT = '/api/v1/';


    app.get('/api/v1/recipe/:recipeId', (req, resp) => {
        // #swagger.parameters['recipeId'] = { description: "Id to retrieve" } 
        // #swagger.description = 'Endpoint used to obatin a recipe'
        RecipeServiceInstance.find(req.params.recipeId)
            .then(data => {
                if (data === null)
                    resp.status(404).send();
                else
                    resp.status(200).send(data);
            }
            );
    })

    app.post('/api/v1/simulaterecipe', (req, resp) => {
        // #swagger.description = 'Simulate a recipe into mongodb to be used.'
        RecipeServiceInstance.load()
            .then(result => resp.status(200).send(result.ops[0]))
            .catch(err => resp.status(500).send(err));
    })

    app.post(ROOT + 'newrecipe', jsonParser, (req, resp) => {
        /*  
            #swagger.description = 'Endpoint used to store a recipe'
            #swagger.consumes = ['application/json']
            #swagger.parameters['obj'] = {
                in: 'body',
                type: "object",
                description: "Recipe data"
            } 
        */
        RecipeServiceInstance.store(req.body)
            .then(res => { console.log('Sending back response'); resp.json({ id: res.insertedId }); })
            .catch(err => { console.dir(err); resp.status(500).send(err); });

    })


}