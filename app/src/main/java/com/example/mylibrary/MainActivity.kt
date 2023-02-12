package com.example.mylibrary

import  androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mylibrary.databinding.ActivityMainBinding
import com.example.mylibrary.databinding.ActivityMainBinding.inflate
import com.example.mylibrary.databinding.AddDialogBinding
import com.example.mylibrary.databinding.RemoveDialogBinding
import kotlin.math.log


class MainActivity : AppCompatActivity(), MyAdapter.BookEvents {
    private lateinit var binding :ActivityMainBinding
    lateinit var myAdapter: MyAdapter
    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)

        val bookList =  arrayListOf(
            Book(
                "The Master and Margarita",
                "Mikhail Bulgakov",
                "Soviet",
                "1967",
                4.3f,
                "https://s24.picofile.com/file/8455694076/The_Master_And_Margarita.jpg"
            ),
            Book(
                "1984",
                "George Orwell",
                "English",
                "1949",
                4.2f,
                "https://s24.picofile.com/file/8455694092/1984.jpg"
            ),
            Book(
                "The Trial",
                "Franz Kafka",
                "German",
                "1925",
                4.0f,
                "https://s25.picofile.com/file/8455694118/Trial.jpg"
            ),
            Book(
                "The Brothers Karamazov",
                "Fyodor Dostoevsky",
                "Russian",
                "1880",
                4.4f,
                "https://s25.picofile.com/file/8455694168/karamazov.jpg"
            ),
            Book(
                "Dune",
                "Frank Herbert",
                "English",
                "1965",
                4.2f,
                "https://s25.picofile.com/file/8455694192/Dune.jpg"
            ),
            Book(
                "Sonechka",
                "Lyudmila Ulitskaya",
                "Russian",
                "1992",
                3.9f,
                "https://s25.picofile.com/file/8455694242/Sonechka.jpg"
            ),
            Book(
                "The Castle",
                "Franz Kafka",
                "German",
                "1926",
                3.9f,
                "https://s25.picofile.com/file/8455694268/castle.jpg"
            ),
            Book(
                "A Red Flower",
                "Vsevolod Garshin",
                "Russian",
                "1883",
                4.0f,
                "https://s24.picofile.com/file/8455694342/AredFlower.jpg"
            ),
            Book(
                "The enchanted wanderer",
                "Nikolai Leskov",
                "Russian",
                "1873",
                3.9f,
                "https://s24.picofile.com/file/8455694284/Wanderer.jpg"
            ),
            Book(
                "Lord of the Flies",
                "William Golding",
                "English",
                "1954",
                3.7f,
                "https://s25.picofile.com/file/8455694326/LOTF.jpg"
            ),
            Book(
                "Anna Karenina",
                "Leo Tolstoy",
                "Russian",
                "1878",
                4.1f,
                "https://s24.picofile.com/file/8455694426/AnnaKarenina.jpg"
            ),
            Book(
                "Satan's Diary",
                "Leonid Andreyev",
                "Russian",
                "1919",
                3.7f,
                "https://s24.picofile.com/file/8455694468/SatanDiary.jpg"
            ),
            Book(
                "The Metamorphosis",
                "Franz Kafka",
                "German",
                "1915",
                3.8f,
                "https://s24.picofile.com/file/8455694626/metamorphosis.jpg"
            ),
            Book(
                "The Idiot",
                "Fyodor Dostoevsky",
                "Russian",
                "1869",
                4.2f,
                "https://s25.picofile.com/file/8455696000/idiot.jpg"
            ),
        )

        myAdapter = MyAdapter(bookList, this)
        binding.recyclerMain.adapter = myAdapter
        binding.recyclerMain.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        addNew()

        binding.edtSearchBox.addTextChangedListener { editTextInput ->

            if (editTextInput!!.isNotEmpty()) {

                val cloneList = bookList.clone() as ArrayList<Book>
                val filteredList = cloneList.filter { bookItem ->
                    bookItem.txtSubject.contains(editTextInput)
                }

                myAdapter.setData(ArrayList(filteredList))
             }
            else {
                myAdapter.setData(bookList.clone() as ArrayList<Book>)
                
            }

        }

    }


    override fun onClickBook() {

    }
//******************************************************************************
    override fun onClickLongBook(book: Book, pos: Int) {


            val dialog = AlertDialog.Builder(this).create()
            val dialogRemoveBinding = RemoveDialogBinding.inflate(layoutInflater)
            dialog.setView(dialogRemoveBinding.root)
            dialog.setCancelable(true)
            dialog.show()

            dialogRemoveBinding.btnRemoveDialogCancel.setOnClickListener{
                dialog.dismiss()
            }
            dialogRemoveBinding.btnRemoveDialogAccept.setOnClickListener{
                dialog.dismiss()
                myAdapter.removeBook(book , pos)

            }




    }
//*****************************************************************************
    private fun addNew () {
        binding.btnAddNewItem.setOnClickListener {
            val dialog = AlertDialog.Builder(this).create()
            val dialogBinding = AddDialogBinding.inflate(layoutInflater)
            dialog.setView(dialogBinding.root)
            dialog.setCancelable(true)
            dialog.show()
            dialogBinding.dialogBtnDone.setOnClickListener{

                if (
                    dialogBinding.dialogBook.length() > 0 &&
                    dialogBinding.dialogWriter.length() > 0 &&
                    dialogBinding.dialogYear.length() > 0 &&
                    dialogBinding.dialogNationality.length() > 0
                ) {
                    val txtbook = dialogBinding.dialogBook.text.toString()
                    val txtwriter = dialogBinding.dialogWriter.text.toString()
                    val txtyear = dialogBinding.dialogYear.text.toString()
                    val txtnationality = dialogBinding.dialogNationality.text.toString()
                    val score:Float = (1..5).random().toFloat()

                    val newBook = Book(txtbook, txtwriter, txtnationality,txtyear, score, " ")
                    myAdapter.addBook(newBook)
                    binding.recyclerMain.scrollToPosition(0)
                    dialog.dismiss()
                } else {
                    Toast.makeText(this, "please fill all the  gaps",Toast.LENGTH_SHORT).show()
                }


            }

        }
    }


}
