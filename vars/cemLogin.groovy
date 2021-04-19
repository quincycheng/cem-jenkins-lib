def call(Map config = [:]) {

  theOrg = config.org ? config.org : System.getenv("CEM_ORG")
  theApiKey = config.accessKey ? config.accessKey : System.getenv("CEM_APIKEY")

  def reqBody = "{\"organization\": \"${theOrg}\",\"accessKey\":\"${theApiKey}\"}"
  def reqUrl = 'https://api.cem.cyberark.com/apis/login'

  def post = new URL(reqUrl).openConnection()
  post.setRequestMethod('POST')
  post.setDoOutput(true)
  post.setRequestProperty('Content-Type', 'application/json')
  post.getOutputStream().write(reqBody.getBytes('UTF-8'))
  def postRC = post.getResponseCode()

  if (postRC.equals(200)) {
    respText = post.getInputStream().getText()
    post = null
    respJson = readJSON text: (String)respText
    return respJson.token
  }
}
