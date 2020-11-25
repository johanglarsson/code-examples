const app = require('express')()
const http = require('http')
const swaggerUi = require('swagger-ui-express')
const swaggerFile = require('./swagger/swagger_output.json')
const bodyParser = require('body-parser');

http.createServer(app).listen(8080)
console.log("Listening at:// port:%s (HTTP)", 8080)

app.use('/doc', swaggerUi.serve, swaggerUi.setup(swaggerFile), bodyParser.json())

require('./src/recipes/recipe.controller')(app)