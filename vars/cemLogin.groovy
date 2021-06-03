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


  post.setRequestProperty('Content-Type', 'application/json')
  post.setRequestProperty("Accept", 'application/json');
  post.setRequestProperty('User-Agent', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36')
  post.setRequestProperty('Host', 'api.cem.cyberark.com')
  post.setRequestProperty('Content-Length', Integer.toString( reqBody.getBytes('UTF-8').length ) )
  post.setRequestProperty('Accept-Encoding', 'gzip, deflate, br')
  post.setRequestProperty('Connection', 'keep-alive')


//  println("debug - login api content length: " + Integer.toString( reqBody.getBytes('UTF-8').length ))
  
 

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
     //println("error message: " + post.getErrorStream().getText() )
    
             InputStream error = post.getErrorStream();
         for (int i = 0; i < error.available(); i++) {
            System.out.println("" + error.read());
         }

    
  }
}

