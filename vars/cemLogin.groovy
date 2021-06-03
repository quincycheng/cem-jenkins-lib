def call(Map config = [:]) {

  theOrg = config.org ? config.org : env.CEM_ORG
  theApiKey = config.accessKey ? config.accessKey : env.CEM_APIKEY
  isDebug = config.debug ? config.debug : false

  
  System.setProperty("sun.net.http.allowRestrictedHeaders", "true") 
  
  def reqBody = "{\"organization\": \"${theOrg}\",\"accessKey\": \"${theApiKey}\"}"
  def reqUrl = 'https://api.cem.cyberark.com/apis/login'

  def post = new URL(reqUrl).openConnection()
  post.setRequestMethod('POST')
  post.setDoOutput(true)
  post.setDoInput(true);

  post.setRequestProperty('Content-Type', 'application/json')
  //post.setRequestProperty("Accept", '*/*');
  post.setRequestProperty('User-Agent', 'PostmanRuntime/7.28.0')
  post.setRequestProperty('Host', 'api.cem.cyberark.com')
  post.setRequestProperty('Content-Length', Integer.toString( reqBody.getBytes('UTF-8').length ) )

  //println("debug - login api body: " + reqBody )
  println("debug - login api content length: " + Integer.toString( reqBody.getBytes('UTF-8').length ))

  
  
  // post.setRequestProperty('Content-Length', Integer.toString( reqBody.getBytes('UTF-8').length ))
  //println("debug - login api content length: " + post.getContentLength() )
  //post.setRequestProperty('Content-Length', Integer.toString( post.getContentLength() ))  
  
 

  post.getOutputStream().write(reqBody.getBytes('UTF-8'))
  post.getOutputStream().flush()
  
  def postRC = post.getResponseCode()
  

  if (postRC.equals(200)) {
    respText = post.getInputStream().getText()
    post = null
    respJson = readJSON text: (String)respText
    
    println("debug - login response Json: $respJson ")
    
    return respJson.token
  }  else {
     println("error - login api response code: $postRC ")
    println("error message: " + post.getErrorStream().getText() )
  }
}
