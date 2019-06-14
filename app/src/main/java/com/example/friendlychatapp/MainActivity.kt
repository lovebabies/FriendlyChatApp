package com.example.friendlychatapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val ANONYMOUS = "anonymous"
    private val TAG = "MainActivity"
    private var mUserName = ANONYMOUS
    lateinit var mFirebaseDatabase : FirebaseDatabase
    lateinit var mDatabaseReference: DatabaseReference
    lateinit var mMessageReference: DatabaseReference
    lateinit var mChildEventListener: ChildEventListener
    lateinit var mMessageAdapter: MessageAdapter

    private var mMessageList = ArrayList<FriendlyMessage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        progressBar.visibility = View.GONE
        //Init databaseRef
        mFirebaseDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mFirebaseDatabase.reference
        mMessageReference = mDatabaseReference.child("message")

        //Send Message
        sendButton.setOnClickListener {
            val friendlyMessage = FriendlyMessage(messageEditText.text.toString(), mUserName, null)
            mMessageReference.push().setValue(friendlyMessage)
            progressBar.visibility = View.VISIBLE
        }

        //Check for enable button
        messageEditText.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                sendButton.isEnabled = s.toString().trim().isNotEmpty()
            }
        })

        //setup child listener
        mChildEventListener = object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                progressBar.visibility = View.GONE
                mMessageAdapter.addItem(p0.getValue(FriendlyMessage::class.java)!!)
                messageRecyclerView.smoothScrollToPosition(mMessageList.size)
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }
        }
        mMessageReference.addChildEventListener(mChildEventListener)
    }

    private fun setupRecyclerView() {
        messageRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            mMessageAdapter = MessageAdapter()
            adapter = mMessageAdapter
            mMessageAdapter.setDataList(mMessageList)
        }
    }

}
