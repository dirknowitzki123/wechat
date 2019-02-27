require.config({
	baseUrl : "./",
    paths: {
    	
    	"require.css/css": ["http://apps.bdimg.com/libs/require-css/0.1.8/css", "assets/component/require-css/0.1.8/css"]
    	,"require.css/css.min": ["http://apps.bdimg.com/libs/require-css/0.1.8/css.min", "assets/component/require-css/0.1.8/css.min"]
    

		,"require.css.extend/css.extend": "assets/component/require-css-extend/0.1.8/css.extend"
    	,"require.css.extend/css.extend.min": "assets/component/require-css-extend/0.1.8/css.extend.min"

    		
		,"jquery": ["http://libs.baidu.com/jquery/1.11.3/jquery", "assets/component/jquery/1.11.3/jquery"]
    	,"jquery.min": ["http://libs.baidu.com/jquery/1.11.3/jquery.min", "assets/component/jquery/1.11.3/jquery.min"]
        

		,"bootstrap": ["http://libs.baidu.com/bootstrap/3.3.4/js/bootstrap", "assets/component/bootstrap/3.3.4/js/bootstrap"]
    	,"bootstrap.min": ["http://libs.baidu.com/bootstrap/3.3.4/js/bootstrap.min", "assets/component/bootstrap/3.3.4/js/bootstrap.min"]


    	,"bootstrap.datetimepicker": ["assets/component/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker"]
        ,"bootstrap.datetimepicker.min": ["http://cdn.bootcss.com/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min", "assets/component/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min"]

    	
    	,"moment.min": ["assets/component/moment/2.11.1/min/moment.min"]
    	,"moment.with.locales.min": ["assets/component/moment/2.11.1/min/moment-with-locales"]
    	,"locales.min": ["assets/component/moment/2.11.1/min/locales"]

		,"moment": ["http://cdn.bootcss.com/moment.js/2.11.1/moment.min", "assets/component/moment/2.11.1/min/moment.min"]
		,"moment.with.locales": ["http://cdn.bootcss.com/moment.js/2.11.1/moment-with-locales.min", "assets/component/moment/2.11.1/min/moment-with-locales.min"]
		,"locales": ["assets/component/moment/2.11.1/min/locales.min"]


    	,"validator": ["http://cdn.bootcss.com/validator/4.0.5/validator", "assets/component/validator/4.5.2/validator"]
		,"validator.min": ["http://cdn.bootcss.com/validator/4.0.5/validator.min", "assets/component/validator/4.5.2/validator.min"]


    	,"jquery.validate": ["http://cdn.bootcss.com/jquery-validate/1.14.0/jquery.validate", "assets/component/jquery-validate/1.14.0/dist/jquery.validate"]
		,"jquery.validate.min": ["http://cdn.bootcss.com/jquery-validate/1.14.0/jquery.validate.min", "assets/component/jquery-validate/1.14.0/dist/jquery.validate.min"]
    	,"additional.methods": ["http://cdn.bootcss.com/jquery-validate/1.14.0/additional-methods", "assets/component/jquery-validate/1.14.0/dist/additional-methods"]
		,"additional.methods.min": ["http://cdn.bootcss.com/jquery-validate/1.14.0/additional-methods.min", "assets/component/jquery-validate/1.14.0/dist/additional-methods.min"]
		
    },
    map: {
        "*": {
            "css2": "require.css.extend/css.extend",
        	"css2.min": "require.css.extend/css.extend.min"
        }
    },
    shim: {
    	
    	 //css 依赖加载器
    	 "require.css.extend/css.extend": {
    		 deps: ["require.css/css"]
         }
         ,"require.css.extend/css.extend.min": {
        	 deps: ["require.css/css.min"]
         }
         
         //moment
         ,"locales": {
        	 deps: ["moment.with.locales", "moment"]
         }
         ,"locales.min": {
        	 deps: ["moment.with.locales.min", "moment.min"]
         }
         
         //bootstrap
    	 ,"bootstrap": {
             deps: [
                 "jquery",
                 "css2!assets/component/bootstrap/3.3.4/css/bootstrap"
             ]
         }
         ,"bootstrap.min": {
             deps: [
                 "jquery.min",
                 "css2.min!assets/component/bootstrap/3.3.4/css/bootstrap.min"
             ]
         }
         
         //jquery.validate
         ,"jquery.validate": {
        	 deps: [ "jquery" ]
         }
         ,"jquery.validate.min": {
        	 deps: [ "jquery.min" ]
         }
         
         
         //bootstrap-datetimepicker
         ,"bootstrap.datetimepicker": {
        	 deps: [
    	        "bootstrap",
    	        "locales",
    	        "css2!http://cdn.bootcss.com/bootstrap-datetimepicker/4.17.37/css/bootstrap-datetimepicker|assets/component/bootstrap-datetimepicker/4.17.37/css/bootstrap-datetimepicker"
	         ]
         }
         ,"bootstrap.datetimepicker.min": {
        	 deps: [
    	        "bootstrap.min",
    	        "locales.min",
    	        "css2.min!http://cdn.bootcss.com/bootstrap-datetimepicker/4.17.37/css/bootstrap-datetimepicker.min|assets/component/bootstrap-datetimepicker/4.17.37/css/bootstrap-datetimepicker.min"
	         ]
         }
         
    }
})
