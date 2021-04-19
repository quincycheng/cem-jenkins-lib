def call(Map config = [:]) {
   theUrl = 'https://api.cem.cyberark.com/cloudEntities/api/search?'
   if (config.platform?.trim()) { theUrl += "&platform=${config.platform}" }
   if (config.accountId?.trim()) { theUrl += "&account_id=${config.accountId}" }
   if (config.fullAdmin?.trim()) { theUrl += "&full_admin=${config.fullAdmin}" }
   if (config.shadowAdmin?.trim()) { theUrl += "&shadow_admin=${config.shadowAdmin}" }
   if (config.nextToken?.trim()) { theUrl += "&next_token=${config.nextToken}" }

   result = cemGetRequest(url: theUrl, token: cemLogin(config) )
   return result
}
