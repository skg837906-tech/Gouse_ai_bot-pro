package com.gouse.bot
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    data class Msg(val text: String, val isUser: Boolean)
    private val chats = mutableListOf<Msg>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val rv = findViewById<RecyclerView>(R.id.chatRecycler)
        val input = findViewById<EditText>(R.id.inputMessage)
        val send = findViewById<Button>(R.id.btnSend)
        val adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(p: android.view.ViewGroup, v: Int): RecyclerView.ViewHolder {
                val tv = TextView(p.context); tv.setPadding(30,30,30,30); tv.textSize = 16f
                return object : RecyclerView.ViewHolder(tv) {}
            }
            override fun onBindViewHolder(h: RecyclerView.ViewHolder, pos: Int) {
                val m = chats[pos]; val tv = h.itemView as TextView
                tv.text = (if(m.isUser) "👤 You: " else "🤖 Gouse Bot: ") + m.text
                tv.setTextColor(if(m.isUser) android.graphics.Color.WHITE else android.graphics.Color.parseColor("#00FF88"))
            }
            override fun getItemCount() = chats.size
        }
        rv.layoutManager = LinearLayoutManager(this); rv.adapter = adapter
        fun add(t: String, u: Boolean) { chats.add(Msg(t,u)); adapter.notifyItemInserted(chats.size-1); rv.scrollToPosition(chats.size-1) }
        add("Hello! I am Gouse Bot Pro 🚀 Your Professional AI Assistant. How can I help?", false)
        send.setOnClickListener {
            val q = input.text.toString().trim(); if(q.isEmpty()) return@setOnClickListener
            add(q, true); input.text.clear()
            val ans = when {
                q.lowercase().contains("hello") -> "Hello Boss Gouse! How are you?"
                q.lowercase().contains("who") -> "I am Gouse Bot Pro, made by Gouse. Professional AI like ChatGPT."
                q.lowercase().contains("code") -> "Yes! I can build Android Apps, Websites, Python tools. Tell me what app you want?"
                else -> "Gouse Bot Pro Answer for: '$q'\n\nThis is professional AI response. I can answer GK, Coding, Movies, Study, Business ideas. Ask more clearly!"
            }
            add(ans, false)
        }
    }
}
