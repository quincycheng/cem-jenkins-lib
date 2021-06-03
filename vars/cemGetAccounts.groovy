def call(Map config = [:]) {
   theUrl = 'https://api.cem.cyberark.com/customer/platforms/accounts'

   println( "debug " + cemLogin(config) )
   
   result = cemGetRequest(url: theUrl, token: cemLogin(config) )
   return result
}
