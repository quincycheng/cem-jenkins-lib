def call(Map config = [:]) {
   def requestBody = "{\"organization\": \"${config.org}\","accessKey\":\"${config.apiKey}\"}"


  sh "echo ${requestBody}"

  response = httpRequest customHeaders: [[name: 'Content-Type', value: 'application/x-www-form-urlencoded;charset=UTF-8']], 
		httpMode: "POST",
  		requestBody:requestBody,
		url: "https://api.cem.cyberark.com/apis/login"
             

  sh "echo ${response.content}"


}
