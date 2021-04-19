def call(Map config = [:]) {

  def get = new URL(reqUrl).openConnection()
  get.setRequestMethod('GET')
  get.setDoOutput(true)
  get.setRequestProperty('Content-Type', 'application/json')
  get.getOutputStream().write(reqBody.getBytes('UTF-8'))
  def getRC = get.getResponseCode()

  if (getRC.equals(200)) {
    respText = post.getInputStream().getText()
    post = null
    //println( (String)respText )
    respJson = readJSON text: (String)respText
    println( respJson.token )
  }
}
