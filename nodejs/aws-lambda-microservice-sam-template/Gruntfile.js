var grunt = require('grunt');
grunt.loadNpmTasks('grunt-aws-lambda');
grunt.loadNpmTasks('grunt-swagger-js-codegen');

grunt.initConfig({
   lambda_invoke: {
      create: {
         options: {
            handler : 'create',
            file_name: 'backend-function/index.js',
            event: 'event-create.json'
         }
      },
      get: {
         options: {
            handler : 'get',
            file_name: 'backend-function/index.js',
            event: 'event-get.json'
         }
      }
   },
   'swagger-js-codegen': {
       service: {
                options: {
                    apis: [
                        {
                            swagger: 'swagger-cors.yaml',
                            className: 'Model',
                            moduleName: 'Model' // This is the model and file name
                        }
                    ],
                    dest: 'lib'
                },
                dist: {
                }
            }
    }
});

grunt.registerTask('invoke', ['lambda_invoke'])
grunt.registerTask('codegen', ['swagger-js-codegen'])
