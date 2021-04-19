def call(Map config = [:]) {
   sh echo ${config.org} ${conjur.apiKey}
}
