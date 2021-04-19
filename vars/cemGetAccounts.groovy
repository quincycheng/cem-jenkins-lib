def call(Map config = [:]) {

   def token = cemLogin(org=${config.org}, apiKey=${config.apiKey})
}
