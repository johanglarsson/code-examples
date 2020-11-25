const swaggerAutogen = require('swagger-autogen')()

const outputFile = './swagger/swagger_output.json'
const endpointsFiles = ['./src/recipes/recipe.controller.js']
const doc = {
    info: {
        title: "Recipes API",
        description: "API to test mongodb and express"
    },
    host: "localhost:8080",
    schemes: ['http']
}

swaggerAutogen(outputFile, endpointsFiles, doc)