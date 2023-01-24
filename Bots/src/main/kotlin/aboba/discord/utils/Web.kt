package aboba.discord.utils

import java.net.HttpURLConnection
import java.net.URL


fun sendReq() : String? {
    val url = "https://api.twitch.tv/helix/streams?user_id=786428224"
    val httpClient: HttpURLConnection = URL(url).openConnection() as HttpURLConnection
    httpClient.setRequestMethod("GET")
    httpClient.setRequestProperty("User-Agent", "Mozilla/5.0")
    httpClient.setRequestProperty("Accept-Language", "en-US,en;q=0.5")
    httpClient.setRequestProperty("Authorization", "Bearer 5rn92qvwa3kryrf33nyiuu7wm95lnr");
    httpClient.setRequestProperty("Client-Id", "gp762nuuoqcoxypju8c569th9wz7q5");
    val resp = httpClient.responseCode
    if(resp > 400){
        println("ОШБИКА ${resp}: что то сломалось")
        return null
    }
    val json = String(httpClient.inputStream.readAllBytes(), Charsets.UTF_8)
    httpClient.disconnect()
    return json
}