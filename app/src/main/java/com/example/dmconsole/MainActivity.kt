package com.example.dmconsole

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dmconsole.databinding.ActivityMainBinding
import java.io.InputStream
import java.util.*

val femaleFirstNames = mutableListOf<String>()
val maleFirstNames = mutableListOf<String>()
val lastNames = mutableListOf<String>()
val npcNames = mutableListOf<String>()
private lateinit var adapter : MainActivity.MyAdapter

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        readNames()
        generateNames()

        val layoutManager = LinearLayoutManager(this)
        binding.nameDisplay.setLayoutManager(layoutManager)

        binding.nameDisplay.setHasFixedSize(true)

        val divider = DividerItemDecoration(
            applicationContext, layoutManager.orientation
        )
        binding.nameDisplay.addItemDecoration(divider)

        adapter = MyAdapter()
        binding.newListButton.setOnClickListener(this)
        binding.nameDisplay.setAdapter(adapter)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.instructions){
            val builder = AlertDialog.Builder(binding.root.context)
            builder.setTitle("How to Use")
            builder.setPositiveButton("Sounds Good", null)
            builder.setMessage("DMConsole is used for generating Non Playable Characters (NPCs) " +
                    "for the Dungeon Master (DM) to use in a table top role playing game (ttrpg). \n\n" +
                    "Starting the app or pressing the New List button generates a randomized scrollable list of 100 npc names." +
                    " Clicking on a name will generate randomized details for that npc name. \n")
            builder.show()

        } else if(item.itemId == R.id.about) {
            val builder = AlertDialog.Builder(binding.root.context)
            builder.setTitle("About")
            builder.setPositiveButton("Okay", null)
            builder.setMessage("DMConsole is developed by Robert Crutchfield.")
            builder.show()

        }
        return super.onOptionsItemSelected(item)
    }

    inner class MyViewHolder(val itemView : View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {


        init {
            itemView.findViewById<View>(R.id.item_textview).setOnClickListener(this)
        }

        fun setText(text: String) {
            itemView.findViewById<TextView>(R.id.item_textview).setText(text)
        }

        override fun onClick(view: View?) {
            if (view != null) {
                val intent = Intent(view.context, DetailedNPC::class.java)
                val name = npcNames[adapterPosition]
                intent.putExtra("Name", name)

                startActivity(intent)
            }
        }
    }

    inner class MyAdapter() : RecyclerView.Adapter<MyViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.setText(npcNames[position])
        }

        override fun getItemCount(): Int {
            return npcNames.size
        }
    }

    private fun generateNames(){

        for(i in 1..100) {
            var firstName = ""
            //determines gender and sets first name
            if(randomNumber(2) == 1){
                firstName = femaleFirstNames[randomNumber(femaleFirstNames.size - 1)]
            } else {
                firstName = maleFirstNames[randomNumber(maleFirstNames.size - 1)]
            }

            var lastName = lastNames[randomNumber(lastNames.size - 1)]
            val name = firstName + " " + lastName
            npcNames.add(name)
        }
    }

    private fun randomNumber(max : Int) : Int{
        val result = (0..max).random()
        return result
    }

    private fun readNames(){
        val femaleinput : InputStream =  getAssets().open("femalefirstNames.txt")
        val maleinput : InputStream =  getAssets().open("malefirstNames.txt")
        val lastinput : InputStream = getAssets().open("lastNames.txt")

        val femalescanner = Scanner(femaleinput)
        val malescanner = Scanner(maleinput)
        val lastscanner = Scanner(lastinput)

        while(femalescanner.hasNextLine()){
            val firstName = femalescanner.nextLine()
            femaleFirstNames.add(firstName)
        }
        while(malescanner.hasNextLine()){
            val firstName = malescanner.nextLine()
            maleFirstNames.add(firstName)
        }
        while(lastscanner.hasNextLine()){
            val lastName = lastscanner.nextLine()
            lastNames.add(lastName)
        }
    }

    override fun onClick(v: View?) {
        npcNames.clear()
        generateNames()
        adapter.notifyDataSetChanged()
    }
}