def call(Map config = [:]) {
   theUrl = 'https://api.cem.cyberark.com/recommendations/remediations?'
   if (config.platform?.trim()) { theUrl += "&platform=${config.platform}" }
   if (config.accountId?.trim()) { theUrl += "&account_id=${config.accountId}" }
   if (config.entityId?.trim()) { theUrl += "&entity_id=${config.entityId}" }

   result = cemGetRequest(url: theUrl, token: cemLogin(config) )
   return result
}
