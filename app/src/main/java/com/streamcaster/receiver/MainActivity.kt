package com.streamcaster.receiver

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONObject
import java.security.SecureRandom
import java.util.concurrent.TimeUnit

/**
 * StreamCaster Receiver für Fire TV Stick.
 *
 * Verbindet sich mit dem StreamCaster-Signaling-Server (gleiche Logik wie die
 * Browser-Receiver-Seite), generiert einen Room-Code, empfängt CommandOps und
 * spielt Media ab. AirPlay-Server (UxPlay NDK-Port) und ExoPlayer-Playback sind
 * als TODO markiert — siehe PROMPT.md.
 */
class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "StreamCasterRx"
        private const val SIGNALING_URL = "wss://streamcaster-signaling.streamcaster.workers.dev/ws"
        private const val ALPHABET = "23456789ABCDEFGHJKMNPQRSTUVWXYZ"
        private val client = OkHttpClient.Builder()
            .pingInterval(30, TimeUnit.SECONDS)
            .build()
    }

    private var roomCode: String = ""
    private var webSocket: WebSocket? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: setContentView(R.layout.activity_main) — Glassmorphism-Idle-Screen
        //       mit großem Room-Code + Status-Text (siehe PROMPT.md UI-Spec).

        roomCode = generateRoomCode(6)
        Log.i(TAG, "Room code: $roomCode")
        connectSignaling(roomCode)
    }

    /** Gleiches Alphabet wie die Webapp — kein 0/O/1/I/L. */
    private fun generateRoomCode(length: Int = 6): String {
        val bytes = ByteArray(length)
        SecureRandom().nextBytes(bytes)
        return bytes.map { ALPHABET[(it.toInt() and 0xFF) % ALPHABET.length] }.joinToString("")
    }

    private fun connectSignaling(code: String) {
        val url = "$SIGNALING_URL?code=$code&role=receiver"
        val request = Request.Builder().url(url).build()
        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(ws: WebSocket, response: Response) {
                Log.i(TAG, "Signaling connected")
            }

            override fun onMessage(ws: WebSocket, text: String) {
                handleMessage(text)
            }

            override fun onFailure(ws: WebSocket, t: Throwable, response: Response?) {
                Log.e(TAG, "Signaling failure", t)
                // TODO: Reconnect mit Backoff (z.B. Exponential Backoff).
            }
        })
    }

    private fun handleMessage(text: String) {
        try {
            val msg = JSONObject(text)
            when (msg.getString("t")) {
                "joined" -> Log.i(TAG, "Waiting for sender…")
                "peer-joined" -> Log.i(TAG, "Sender connected")
                "cmd" -> executeCommand(msg.getJSONObject("op"))
                "webrtc" -> handleWebRTC(msg.getString("kind"), msg.get("payload"))
                "state" -> { /* sender-ack, ignore */ }
                "error" -> Log.w(TAG, "Signaling error: ${msg.getString("message")}")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Bad message: $text", e)
        }
    }

    private fun executeCommand(op: JSONObject) {
        when (op.getString("name")) {
            "load" -> {
                val url = op.optString("url")
                val title = op.optString("title")
                Log.i(TAG, "LOAD: $title — $url")
                // TODO: ExoPlayer.preload(url) — mp4 direkt, HLS via Media3-HLS.
            }
            "play" -> Log.i(TAG, "PLAY")
            "pause" -> Log.i(TAG, "PAUSE")
            "seek" -> Log.i(TAG, "SEEK ${op.optDouble("time")}")
            "volume" -> Log.i(TAG, "VOLUME ${op.optDouble("value")}")
            "stop" -> Log.i(TAG, "STOP")
            "subtitle" -> Log.i(TAG, "SUBTITLE ${op.optString("label")}")
            "stream-start" -> Log.i(TAG, "STREAM-START ${op.optString("kind")}")
            "stream-stop" -> Log.i(TAG, "STREAM-STOP")
        }
    }

    private fun handleWebRTC(kind: String, payload: Any?) {
        // TODO: an ReceiverPeer weiterreichen (WebRTC-Decoding via ExoPlayer).
        Log.i(TAG, "WEBRTC $kind")
    }

    override fun onDestroy() {
        webSocket?.close(1000, "app closing")
        super.onDestroy()
    }
}
