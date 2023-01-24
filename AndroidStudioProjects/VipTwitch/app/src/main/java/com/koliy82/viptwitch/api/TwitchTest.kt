//package com.koliy82.viptwitch.api
//
//import java.io.BufferedReader
//import java.io.InputStreamReader
//import java.io.OutputStreamWriter
//import java.net.HttpURLConnection
//import java.net.URL
//import java.nio.charset.Charset
//import java.util.regex.Pattern
//import java.util.stream.Collectors
//import kotlin.concurrent.thread
//
//object TwitchTest {
//
//
//    var name = "koliy82_"
//    var twitchID = 275379591
//
//
//    //Application
//    val appname = "testtttttttt"
//    val redirectURL = "http://localhost"
//    val appID = "x7oul1qh4ww08r8yk8kb8zd5z3jz5t"
//    val appSecret = "lnjn4rhkcicw0i4vgr7iumsy9yvq5s"
//    lateinit var token:String
//
//    fun getToken(block:(String)->Unit) = thread {
//        val url = URL("https://id.twitch.tv/oauth2/token")
//        val con: HttpURLConnection = url.openConnection() as HttpURLConnection
//        con.requestMethod = "POST";
//        con.doOutput = true;
//        val REG = Pattern.compile("\"access_token\"\\:\\\"([A-z0-9]+)\\\"")
//        val writer = OutputStreamWriter(con.outputStream)
//        val resp = "client_id=${appID}" +
//                "&client_secret=${appSecret}" +
//                "&grant_type=client_credentials"
//        writer.write(resp)
//        writer.close()
//        if (con.responseCode != 200) {
//            System.err.println("connection failed");
//            block("Connection err")
//        }
//        BufferedReader(
//            InputStreamReader(con.inputStream, Charset.forName("utf-8"))
//        ).use { reader ->
//            val output = reader.lines().collect(Collectors.joining(System.lineSeparator()))
//            //output - Ответ в виде json
//            val mt = REG.matcher(output)
//            if(mt.find()){
//                block(mt.group(1))
//            }
//        }
//    }
//    fun getFollowerList(token:String, block:(String)->Unit) = thread {
//        val con = URL("https://api.twitch.tv/helix/users/follows?from_id=${appID}&to_id=${twitchID}").openConnection() as HttpURLConnection
//        con.requestMethod = "GET";
//        con.doOutput = true;
//        con.setFixedLengthStreamingMode(200)
//        con.setRequestProperty ("Authorization", "Bearer ${token}")
//        con.setRequestProperty ("Client-ID", appID)
//        //val writer = OutputStreamWriter(con.outputStream)
//        if (con.responseCode != 200) {
//          System.err.println("connection failed");
//          block("Dadwd")
//        }
//        BufferedReader(
//            InputStreamReader(con.errorStream, Charset.forName("utf-8"))
//        ).use { reader ->
//            val output = reader.lines().collect(Collectors.joining(System.lineSeparator()))
//            //output - Ответ в виде json
//            block(output)
//            //ani
//            //TODO: Parse json
//        }
//    }
//}