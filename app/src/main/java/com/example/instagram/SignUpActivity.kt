package com.example.instagram

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_sign_up)


    signin_link_btn.setOnClickListener {
      startActivity(Intent(this, SignInActivity::class.java))
    }

    signup_btn.setOnClickListener {
      CreateAccount()
    }
  }

  private fun CreateAccount() {
    val fullname = fullname_signup.text.toString()
    val username = username_signup.text.toString()
    val email = email_signup.text.toString()
    val password = password_signup.text.toString()

    //when has all the if statement
    when{
      TextUtils.isEmpty(fullname) -> Toast.makeText(this,"full name is required.", Toast.LENGTH_LONG).show()
      TextUtils.isEmpty(username) -> Toast.makeText(this,"user name is required.", Toast.LENGTH_LONG).show()
      TextUtils.isEmpty(email) -> Toast.makeText(this,"email is required.", Toast.LENGTH_LONG).show()
      TextUtils.isEmpty(password) -> Toast.makeText(this,"password is required.", Toast.LENGTH_LONG).show()

      else ->{
        val progressDialog = ProgressDialog(this@SignUpActivity)
        progressDialog.setTitle("SignUp")
        progressDialog.setMessage("Please wait, this may take a while...")
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()

        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

        mAuth.createUserWithEmailAndPassword(email,password)
          .addOnCompleteListener { task ->
            if (task.isSuccessful) {
              saveUserInfo(fullname, username, email, progressDialog)
            } else {
              val message = task.exception!!.toString()
              Toast.makeText(this, "Error: $message", Toast.LENGTH_LONG).show()
              mAuth.signOut()
              progressDialog.dismiss()
            }
          }
      }
    }
  }

  private fun saveUserInfo(fullname: String, username: String, email: String, progressDialog:ProgressDialog) {

    val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
    val usersRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Users")

    val userMap = HashMap<String, Any>()
    userMap["uid"] = currentUserID
    userMap["fullname"] = fullname
    userMap["username"] = username
    userMap["email"] = email
    userMap["bio"] = "hey I am using Instagra Clone App."
    userMap["image"] ="https://firebasestorage.googleapis.com/v0/b/instagram-8026e.appspot.com/o/Default_Images%2Fprofile.png?alt=media&token=1c59d901-e841-4698-8bb0-0540c52bb6c2"

    usersRef.child(currentUserID).setValue(userMap)
      .addOnCompleteListener { task ->
        if( task.isSuccessful){
            progressDialog.dismiss()
          Toast.makeText(this,"password is required.", Toast.LENGTH_LONG).show()

          val intent = Intent(this@SignUpActivity, MainActivity::class.java)
          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
          finish()
        }else{
          val message = task.exception!!.toString()
          Toast.makeText(this, "Error: $message", Toast.LENGTH_LONG).show()
          FirebaseAuth.getInstance().signOut()
          progressDialog.dismiss()
        }
      }
  }
}
