def call(Map config = [:]) {

  def get = new URL(config.url).openConnection()
  //get.addHeader("Authorization", "Bearer ${config.token}")

  get.setRequestMethod('GET')
  get.setDoOutput(true)
  get.setRequestProperty('Content-Type', 'application/json')
  get.setRequestProperty('Authorization', "Bearer ${config.token}")

  
  def getRC = get.getResponseCode()

  if (getRC.equals(200)) {
    respText = get.getInputStream().getText()
    get = null
    //println( (String)respText )
    respJson = readJSON text: (String)respText
    println( respText )
  }
}
