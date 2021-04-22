# Jenkins Shared Library for CyberArk Cloud Entitlements Manager

  * [Quick Demo](#quick-demo)
  * [Installation](#installation)
  * [Configuration](#configuration)
  * [Steps](#steps)
    + [Get Accounts](#get-accounts)
    + [Get Entities](#get-entities)
    + [Get Entity Details](#get-entity-details)
    + [Get Recommendations](#get-recommendations)
    + [Get Remediations](#get-remediations)
  * [Sample Pipeline](#sample-pipeline)
  * [Webhook](#webhook)
  * [Reference](#reference)

## Quick Demo

![user_prompt](https://user-images.githubusercontent.com/4685314/115691283-7d6f5b00-a390-11eb-9921-bddb20fbe7a5.PNG)

1. [Install this shared library](#installation)
2. Add a new Jenkins pipeline project
3. In the pipeline configuration, under `Pipeline`, set the following values
   - Definition: `Pipeline script from SCM`
   - SCM: `Git`
   - Repository URL: `https://github.com/quincycheng/cem-jenkins-lib.git`
   - Branch Specifier: `main`
   - Script Path: `example/Jenkinsfile`
4. Click `Build Now` to execute the pipeline
5. When the progress paused at `Check CEM Settings` step, click on it and fill in the user prompts, as shown in the above screen capture, and click `Proceed`
   (You can also update the pipeline or Jenkins settings to set `CEM_ORG` & `CEM_APIKEY` environment variables to avoid this prompt)
5. Hover to each of the steps and click `Logs` to review the output, and [sample pipeline](/example/Jenkinsfile)

## Installation

1. At Jenkins web console, access `Manage Jenkins` > `Configure System`
2. Under `Global Pipeline Libraries`, add the following library
   - Name: `cem-jenkins-lib`
   - Default version: `main`
   - Modern SCM \ GitHub \ Repository HTTPS URL: `https://github.com/quincycheng/cem-jenkins-lib.git`


## Configuration

1. Add `@Library("cem-jenkins-lib") _` at the beginning of the pipeline script
2. Add CEM API Key to pipeline, by either:
   - Environment variables `CEM_ORG` for CEM organization & `CEM_APIKEY` for CEM API Access Key (using Conjur if possible), or
   - Passing `org` and `accessKey` as parameters to the steps

## Steps
Below are the step avaliable in this shared library and their corresponding sample code snippets. 
All steps will return results as JSON objects.
Please refer to the [official CyberArk CEM API doc](https://docs.cyberark.com/Product-Doc/OnlineHelp/CEM/Latest/en/Content/HomeTilesLPs/LP-Tile6.htm) for parameters & expected results

### Get Accounts
```
def result = cemGetAccounts()
println (result.data.size() )
for (platform in result.data) {
    println("Platform: $platform.platform")
    platform.accounts.each {
        println("workspace id: ${it.workspace_id}, status: ${it.workspace_status}")
    }
}
```

### Get Entities
```
def result = cemGetEntities(platform: env.demo_platform)
println("total no of entities: " + result.hits.size() )
result.hits.each {
    println "Name: $it.entityName, Score: $it.riskTotalScore"
}
```

### Get Entity Details
```
def result = cemGetEntityDetails(platform: env.demo_platform, accountId: env.demo_accountId, entityId: env.demo_entityId)
println "Name: $result.entity_name, Score: $result.exposure_level"
```

### Get Recommendations
```
def result = cemGetRecommendations(platform: env.demo_platform, accountId: env.demo_accountId, entityId: env.demo_entityId)                    
println("Recommendations of $result.entity_id")
result.recommendations.active_recommendations.each {
    println "$it"
}
```

### Get Remediations
```
def result = cemGetRemediations(platform: env.demo_platform, accountId: env.demo_accountId, entityId: env.demo_entityId)
println("Remediations of $result.entityId")
result.remediations.each {
    println("$it.UN_USED_PERMISSIONS.LEAST_PRIVILEGE.data")
}
```

## Sample Pipeline
You can refer to the sample pipeline for all steps at [/example/Jenkinsfile](/example/Jenkinsfile)

## Webhook
You can refer to the procedure to register the CEM webhook at https://github.com/quincycheng/cem-jenkins-slack-conjur#to-register-cem-webhook-in-jenkins
Please note that setting up webhook doesn't require this shared library as dependency.

## Reference
- Jenkins Shared Library: https://www.jenkins.io/doc/book/pipeline/shared-libraries/
- CyberArk CEM API doc: https://docs.cyberark.com/Product-Doc/OnlineHelp/CEM/Latest/en/Content/HomeTilesLPs/LP-Tile6.htm
