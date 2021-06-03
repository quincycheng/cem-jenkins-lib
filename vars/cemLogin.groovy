def call(Map config = [:]) {

  theOrg = config.org ? config.org : env.CEM_ORG
  theApiKey = config.accessKey ? config.accessKey : env.CEM_APIKEY
  isDebug = config.debug ? config.debug : false

  
 // System.setProperty("sun.net.http.allowRestrictedHeaders", "true") 
  
  def reqBody = "{\"organization\": \"${theOrg}\",\"accessKey\":\"${theApiKey}\"}"
  def reqUrl = 'https://api.cem.cyberark.com/apis/login'

  def post = new URL(reqUrl).openConnection()
  post.setRequestMethod('POST')
  post.setDoOutput(true)
  post.setRequestProperty('Content-Type', 'application/json')
  post.setRequestProperty('Content-Length', reqBody.getBytes('UTF-8').length.toString())
  post.setRequestProperty('Host', 'api.cem.cyberark.com')

  post.getOutputStream().write(reqBody.getBytes('UTF-8'))
  def postRC = post.getResponseCode()
  

  if (postRC.equals(200)) {
    respText = post.getInputStream().getText()
    post = null
    respJson = readJSON text: (String)respText
    
    println("debug - login response Json: $respJson ")
    
    return respJson.token
  }  else {
     println("error - login api response code: $postRC ")
  }
}
