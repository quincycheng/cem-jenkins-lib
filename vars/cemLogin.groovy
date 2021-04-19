def call(Map config = [:]) {
  def reqBody = "{\"organization\": \"${config.org}\",\"accessKey\":\"${config.apiKey}\"}"
  def reqUrl = "https://api.cem.cyberark.com/apis/login"

  def post = new URL(reqUrl).openConnection();
  post.setRequestMethod("POST")
  post.setDoOutput(true)
  post.setRequestProperty("Content-Type", "application/json")
  post.getOutputStream().write(reqBody.getBytes("UTF-8"));
  def postRC = post.getResponseCode();

  if(postRC.equals(200)) {
    respText = post.getInputStream().getText() 
    println( respText );

    //def resultJson = readJSON text: post.getInputStream().getText();
    //println( resultJson.token )
  }


}
