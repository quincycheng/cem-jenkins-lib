def call(Map config = [:]) {
   theUrl = 'https://api.cem.cyberark.com/customer/platforms/accounts'

   result = cemGetRequest(url: theUrl, token: cemLogin(config) )
   println( result )
   return result
}
