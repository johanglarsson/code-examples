var grunt = require('grunt');
grunt.loadNpmTasks('grunt-aws-lambda');

grunt.initConfig({
   lambda_invoke: {
      create: {
         options: {
            handler : 'create',
            file_name: 'api-backend-function/index.js',
            event: 'event-create.json'
         }
      },
      get: {
         options: {
            handler : 'get',
            file_name: 'api-backend-function/index.js',
            event: 'event-get.json'
         }
      }
   }
});

grunt.registerTask('invoke', ['lambda_invoke'])
